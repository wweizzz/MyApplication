package com.example.william.my.module.opensource.activity5

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.activity.BasicImageActivity
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.google.firebase.Firebase
import com.google.firebase.ai.ImagenModel
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import com.google.firebase.ai.type.ImagenAspectRatio
import com.google.firebase.ai.type.ImagenGenerationConfig
import com.google.firebase.ai.type.ImagenImageFormat
import com.google.firebase.ai.type.ImagenPersonFilterLevel
import com.google.firebase.ai.type.ImagenSafetyFilterLevel
import com.google.firebase.ai.type.ImagenSafetySettings
import com.google.firebase.ai.type.PublicPreviewAPI
import kotlinx.coroutines.launch

@Route(path = RouterPath.Opensource.Imagen)
class ImagenActivity : BasicImageActivity() {

    @OptIn(PublicPreviewAPI::class)
    private var model: ImagenModel? = null

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        initImagenModel()
        generateImages()
    }

    @OptIn(PublicPreviewAPI::class)
    private fun initImagenModel() {
        val config = ImagenGenerationConfig(
            numberOfImages = 2,
            aspectRatio = ImagenAspectRatio.LANDSCAPE_16x9,
            imageFormat = ImagenImageFormat.jpeg(compressionQuality = 100),
            addWatermark = false
        )
        // Initialize the Gemini Developer API backend service
        // For Vertex AI use Firebase.ai(backend = GenerativeBackend.vertexAI())
        model = Firebase.ai(backend = GenerativeBackend.googleAI()).imagenModel(
            modelName = "imagen-3.0-generate-002",
            generationConfig = config,
            safetySettings = ImagenSafetySettings(
                safetyFilterLevel = ImagenSafetyFilterLevel.BLOCK_LOW_AND_ABOVE,
                personFilterLevel = ImagenPersonFilterLevel.BLOCK_ALL
            )
        )
    }

    @OptIn(PublicPreviewAPI::class)
    private fun generateImages() {
        lifecycleScope.launch {
            val imageResponse = model?.generateImages(
                prompt = "An astronaut riding a horse",
            )
            val image = imageResponse?.images?.first()
            val bitmapImage = image?.asBitmap()
            mBinding.basicsImage.setImageBitmap(bitmapImage)
        }
    }
}