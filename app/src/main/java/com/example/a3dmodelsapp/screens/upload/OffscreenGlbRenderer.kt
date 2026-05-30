package com.example.a3dmodelsapp.screens.upload

import android.content.Context
import android.graphics.Bitmap
import android.graphics.SurfaceTexture
import android.net.Uri
import android.opengl.Matrix
import android.view.Surface
import com.google.android.filament.*
import com.google.android.filament.gltfio.*
import java.nio.ByteBuffer
import androidx.core.graphics.createBitmap
import com.google.android.filament.utils.KTX1Loader

class OffscreenGlbRenderer(
    private val context: Context
) {

    private val engine = Engine.create()

    private val renderer = engine.createRenderer()
    private val scene = engine.createScene()
    private val view = engine.createView()

    private val cameraEntity = EntityManager.get().create()
    private val camera = engine.createCamera(cameraEntity)

    private lateinit var swapChain: SwapChain

    private val materialProvider = UbershaderProvider(engine)

    private val assetLoader = AssetLoader(
        engine,
        materialProvider,
        EntityManager.get()
    )

    private val resourceLoader = ResourceLoader(engine)

    init {
        view.scene = scene
        view.camera = camera

        view.blendMode = View.BlendMode.TRANSLUCENT

        view.colorGrading = ColorGrading.Builder()
            .toneMapping(ColorGrading.ToneMapping.ACES)
            .build(engine)

        setupCamera()
        setupLight()
    }

    private fun setupCamera() {

        camera.setProjection(
            45.0,
            1.0,
            0.1,
            100.0,
            Camera.Fov.VERTICAL
        )

        camera.lookAt(
            0.0, 0.0, 4.0,
            0.0, 0.0, 0.0,
            0.0, 1.0, 0.0
        )

        camera.setExposure(
            16.0f,
            1.0f / 125.0f,
            100.0f
        )
    }

    private fun setupLight() {

        val sun = EntityManager.get().create()

        LightManager.Builder(LightManager.Type.SUN)
            .color(1.0f, 1.0f, 1.0f)
            .intensity(80_000f)
            .direction(1.0f, 0.0f, 0.0f)
            .castShadows(true)
            .build(engine, sun)

        scene.addEntity(sun)

        val fill = EntityManager.get().create()

        LightManager.Builder(LightManager.Type.DIRECTIONAL)
            .color(0.8f, 0.8f, 1.0f)
            .intensity(20_000f)
            .direction(-1.0f, -0.5f, 0.5f)
            .build(engine, fill)

        scene.addEntity(fill)

        val rim = EntityManager.get().create()

        LightManager.Builder(LightManager.Type.DIRECTIONAL)
            .color(1.0f, 0.95f, 0.9f)
            .intensity(15_000f)
            .direction(1.0f, 0.0f, 1.0f)
            .build(engine, rim)

        scene.addEntity(rim)

        val ibl = KTX1Loader.createIndirectLight(
            engine,
            readAsset("envs/venetian_crossroads_2k/venetian_crossroads_2k_ibl.ktx")
        )

        ibl.indirectLight?.intensity = 30_000f

        scene.indirectLight = ibl.indirectLight
    }

    fun renderGlbToBitmap(
        uri: Uri,
        width: Int = 1024,
        height: Int = 1024,
        rotationY: Float = 0f
    ): Bitmap {

        val textureId = IntArray(1)
        android.opengl.GLES20.glGenTextures(1, textureId, 0)

        val surfaceTexture = SurfaceTexture(textureId[0])
        surfaceTexture.setDefaultBufferSize(width, height)

        val surface = Surface(surfaceTexture)

        swapChain = engine.createSwapChain(surface)

        view.viewport = Viewport(
            0,
            0,
            width,
            height
        )

        val buffer = readUri(uri)

        val asset = assetLoader.createAsset(buffer)
            ?: error("Не удалось загрузить GLB")

        resourceLoader.loadResources(asset)

        scene.addEntities(asset.entities)

        transformModel(asset, rotationY)

        fitCameraToModel(asset)

        renderer.beginFrame(
            swapChain,
            System.nanoTime()
        )

        renderer.render(view)

        val pixelBuffer = ByteBuffer.allocateDirect(width * height * 4)

        renderer.readPixels(
            0,
            0,
            width,
            height,
            Texture.PixelBufferDescriptor(
                pixelBuffer,
                Texture.Format.RGBA,
                Texture.Type.UBYTE
            )
        )

        renderer.endFrame()

        engine.flushAndWait()

        val bitmap = createBitmap(width, height)

        pixelBuffer.rewind()

        bitmap.copyPixelsFromBuffer(pixelBuffer)

        destroyAsset(asset)

        surface.release()
        surfaceTexture.release()

        return bitmap
    }

    fun getModelInfo(uri: Uri): ModelInfo {

        val buffer = readUri(uri)

        val asset = assetLoader.createAsset(buffer)
            ?: error("Не удалось загрузить GLB")

        resourceLoader.loadResources(asset)

        val box = asset.boundingBox

        val width = box.halfExtent[0] * 2f
        val height = box.halfExtent[1] * 2f
        val depth = box.halfExtent[2] * 2f

        val fileSizeBytes =
            context.contentResolver.openAssetFileDescriptor(uri, "r")
                ?.length ?: 0L

        val result = ModelInfo(
            fileSizeMb = fileSizeBytes / 1024f / 1024f,

            width = width,
            height = height,
            depth = depth,
        )

        assetLoader.destroyAsset(asset)

        return result
    }

    private fun readUri(uri: Uri): ByteBuffer {

        val bytes = context.contentResolver
            .openInputStream(uri)
            ?.use { it.readBytes() }
            ?: error("Unable to open file")

        return ByteBuffer.allocateDirect(bytes.size)
            .apply {
                put(bytes)
                flip()
            }
    }

    private fun fitCameraToModel(asset: FilamentAsset) {

        val box = asset.boundingBox

//        val centerX = box.center[0]
//        val centerY = box.center[1]
//        val centerZ = box.center[2]

        val sizeX = box.halfExtent[0] * 2f
        val sizeY = box.halfExtent[1] * 2f
        val sizeZ = box.halfExtent[2] * 2f

        val largestDimension = maxOf(sizeX, sizeY, sizeZ)

        val distance = largestDimension * 1.8f

        camera.lookAt(
            0.0,
            sizeY * 0.15,     // немного выше центра
            distance.toDouble(),

            0.0,
            0.0,
            0.0,

            0.0,
            1.0,
            0.0
        )
    }

    private fun transformModel(
        asset: FilamentAsset,
        rotationY: Float
    ) {

        val tm = engine.transformManager
        val instance = tm.getInstance(asset.root)

        val matrix = FloatArray(16)
        Matrix.setIdentityM(matrix, 0)

        // 1. поворот
        Matrix.rotateM(matrix, 0, rotationY, 0f, 1f, 0f)

        // 2. 🔥 auto-center model
        val box = asset.boundingBox

        val centerX = box.center[0]
        val centerY = box.center[1]
        val centerZ = box.center[2]

        Matrix.translateM(
            matrix,
            0,
            -centerX,
            -centerY,
            -centerZ
        )

        tm.setTransform(instance, matrix)
    }

    private fun readAsset(name: String): ByteBuffer {

        val bytes = context.assets.open(name)
            .use { it.readBytes() }

        return ByteBuffer.allocateDirect(bytes.size)
            .apply {
                put(bytes)
                flip()
            }
    }

    private fun destroyAsset(asset: FilamentAsset) {

        scene.removeEntities(asset.entities)

        assetLoader.destroyAsset(asset)
    }

    fun destroy() {

        materialProvider.destroyMaterials()

        engine.destroyRenderer(renderer)
        engine.destroyView(view)
        engine.destroyScene(scene)
        engine.destroyCameraComponent(cameraEntity)

        engine.destroy()
    }
}