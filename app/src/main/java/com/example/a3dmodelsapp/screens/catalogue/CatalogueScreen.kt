package com.example.a3dmodelsapp.screens.catalogue


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.a3dmodelsapp.R
import com.example.a3dmodelsapp.database.models.Model
import com.example.a3dmodelsapp.ui.theme.CustomTextStyles
import com.example.a3dmodelsapp.ui.theme.borderColor
import com.example.a3dmodelsapp.ui.theme.fontFamily
import com.example.a3dmodelsapp.ui.theme.primary
import com.example.a3dmodelsapp.ui.theme.secondary
import com.example.a3dmodelsapp.ui.theme.textColor
import com.example.a3dmodelsapp.ui.theme.textFieldTip
import com.example.a3dmodelsapp.viewModels.MainViewModel

@Composable
fun ModelCard(model: Model, onOpenInfo: () -> Unit, viewModel: MainViewModel) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = secondary
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .clickable(onClick = {
                    onOpenInfo()
                    viewModel.current_model = model

                    if (viewModel.searchButtonState) {
                        viewModel.changeButtonState()
                    }
                })
                .padding(10.dp),
        ) {
            AsyncImage(
                model = model.image_url,
                contentDescription = "Model Preview",
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp)),
                contentScale = ContentScale.FillWidth,
                placeholder = painterResource(R.drawable.cube),
                error = painterResource(R.drawable.cube)
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = model.name,
                //modifier = Modifier.padding(10.dp),
                fontFamily = fontFamily,
                style = CustomTextStyles.body1_bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogueScreen(onOpenInfo: () -> Unit, viewModel: MainViewModel, byUser: Boolean) {
    val listState = rememberLazyStaggeredGridState()
    val models by viewModel.models.collectAsState()
    val userModels = models.filter { model ->
        model.user_login == viewModel.login
    }

    val isLoading by viewModel.isLoading.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.loadModels()
    }

    LaunchedEffect(Unit) {
        viewModel.restoreScrollPosition(listState)
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex to listState.firstVisibleItemScrollOffset }
            .collect { (index, offset) ->
                viewModel.saveScrollPosition(index, offset)
            }
    }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else if (viewModel.error) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Ошибка загрузки", color = MaterialTheme.colorScheme.error)
        }
    }
    else {
        if (models.isEmpty() && viewModel.searchButtonState) {
            NoResults()
        }
        else {
            LazyVerticalStaggeredGrid(
                state = listState,
                modifier = Modifier.padding(if (byUser) 0.dp else 20.dp),
                columns = StaggeredGridCells.Fixed(2),
                verticalItemSpacing = 20.dp,
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                content = {
                    if (byUser) {
                        items(userModels) { model ->
                            ModelCard(model, { onOpenInfo() }, viewModel)
                        }
                    }
                    else {
                        items(models) { model ->
                            ModelCard(model, { onOpenInfo() }, viewModel)
                        }
                    }

                }
            )
        }
    }
}

@Composable
fun NoResults() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp, 50.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = secondary
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)

    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.search_24px),
                contentDescription = "Search",
                tint = primary,
                modifier = Modifier.size(30.dp)
            )
            Text(
                "По вашему запросу ничего не найдено",
                color = textFieldTip,
                style = CustomTextStyles.body2_regular,
                textAlign = TextAlign.Center,
                fontFamily = fontFamily
            )
        }
    }

}