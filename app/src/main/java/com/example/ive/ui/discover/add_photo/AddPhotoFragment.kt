package com.example.ive.ui.discover.add_photo

import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import com.example.ive.databinding.FragmentAddPhotoBinding
import com.example.ive.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPhotoFragment : BaseFragment<FragmentAddPhotoBinding>() {

    private val galleryImage = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) {
        binding.ivImage.setImageURI(it)
    }

    override fun initListeners() {
        binding.btAddPhoto.setOnClickListener {
            galleryImage.launch(IMAGE_MIME_TYPE)
        }
    }

    override fun getDataBinding() = FragmentAddPhotoBinding.inflate(layoutInflater)

    companion object {
        const val IMAGE_MIME_TYPE = "image/*"
    }
}