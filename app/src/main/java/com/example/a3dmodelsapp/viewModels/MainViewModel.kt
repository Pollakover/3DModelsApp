package com.example.a3dmodelsapp.viewModels

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a3dmodelsapp.database.ApiClient
import com.example.a3dmodelsapp.database.categories.AddCategoryRequest
import com.example.a3dmodelsapp.database.categories.Category
import com.example.a3dmodelsapp.database.categories.FetchCategoriesRequest
import com.example.a3dmodelsapp.database.models.DeleteModelRequest
import com.example.a3dmodelsapp.database.models.Model
import com.example.a3dmodelsapp.database.models.UpdateModelRequest
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val sharedPreferences: SharedPreferences,
    private val userLogin: String
) : ViewModel() {

    var login by mutableStateOf(userLogin)
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    var error by mutableStateOf(false)

    private val _scrollPosition = MutableStateFlow<Pair<Int, Int>?>(null)
    val scrollPosition: StateFlow<Pair<Int, Int>?> = _scrollPosition.asStateFlow()

    fun saveScrollPosition(firstVisibleIndex: Int, firstVisibleScrollOffset: Int) {
        _scrollPosition.value = firstVisibleIndex to firstVisibleScrollOffset
    }

    suspend fun restoreScrollPosition(state: LazyStaggeredGridState) {
        scrollPosition.value?.let { (index, offset) ->
            state.scrollToItem(index, offset)
        }
    }

    //MODELS
    val _models = MutableStateFlow<List<Model>>(emptyList())

    var current_model by mutableStateOf<Model?>(null)

    fun loadModels() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = ApiClient.modelApi.fetchModels()
                _models.value = response
                error = false
            } catch (_: Exception) {
                error = true
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteModel(onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = ApiClient.modelApi.deleteModel(
                    DeleteModelRequest(current_model?.id ?: 0)
                )
                if (response.isSuccessful) {
                    Log.d("DELETE", "Model deleted successfully")
                    current_model = null
                    loadModels()
                    onSuccess()
                }
                error = false
            } catch (e: Exception) {
                Log.e("DELETE", "Error deleting model: ${e.message}")
                error = true
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateModel(
        name: String,
        description: String,
        categories: List<String>,
        onSuccess: () -> Unit = {}
    ) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response =
                    ApiClient.modelApi.updateModel(
                        UpdateModelRequest(
                            id = current_model!!.id,
                            name = name,
                            description = description,
                            categories = categories
                        )
                    )
                if (response.isSuccessful) {
                    current_model = response.body()?.model
                    onSuccess()
                }
                error = false
            } catch (e: Exception) {
                Log.e("UPDATE", "Error updating model: ${e.message}")
                error = true
            } finally {
                _isLoading.value = false
            }
        }
    }

    //Categories
/////////////////////////////////////////////////////////////////////////////////////////////////

    val _categories = MutableStateFlow<List<Category>>(emptyList())

    fun loadCategories() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = ApiClient.categoryApi.fetchCategories(
                    FetchCategoriesRequest(
                        current_model?.id ?: 0
                    )
                )
                _categories.value = response
                error = false
            } catch (_: Exception) {
                error = true
            } finally {
                _isLoading.value = false
            }
        }
    }

    private val _addCategorySuccess = MutableStateFlow(false)

    fun addCategory(
        model_id: Int,
        name: String,
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                ApiClient.categoryApi.addCategory(
                    AddCategoryRequest(
                        model_id = model_id,
                        name = name
                    )
                )
                _addCategorySuccess.value = true
                error = false
            } catch (e: Exception) {
                error = true
            } finally {
                _isLoading.value = false
            }
        }
    }

    //Состояние кнопки поиска
    var searchButtonState by mutableStateOf(false)
        private set

    // изменения состояния кнопки поиска
    fun changeButtonState() {
        _searchText.value = TextFieldValue("")
        searchButtonState = !searchButtonState
    }

    //Запрос фокуса
    var focusRequester by mutableStateOf(FocusRequester())

    fun requestSearchFocus() {
        focusRequester.requestFocus()
    }

    //Для задержки при поиске
    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    var isFocused by mutableStateOf(false)

    //Текст внутри поля
    private val _searchText = MutableStateFlow(TextFieldValue(""))
    val searchText = _searchText.asStateFlow()

    fun onSearchTextChange(text: TextFieldValue) {
        _searchText.value = text

    }

    fun clearSearch() {
        _searchText.value = TextFieldValue("")
    }

    @OptIn(FlowPreview::class)
    val models = searchText
        .onEach { query ->
            _isSearching.update { true }
            //saveSearchQuery(query)
        }
        .combine(_models) { query, models ->
            if (query.text.isBlank()) {
                models
            } else {
                models.filter {
                    it.doesMatchSearchQuery(query.text)
                }
            }
        }
        .onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _models.value
        )
}