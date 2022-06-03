package com.example.cameraapp


import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.example.cameraapp.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {

    lateinit var imageuri:Uri
    private lateinit var binding: ActivityMainBinding

    private val contract = registerForActivityResult(ActivityResultContracts.GetContent()){
        binding.finalImage.setImageURI(it)
    }

    private val cameraContract = registerForActivityResult(ActivityResultContracts.TakePicture()){
        binding.finalImage.setImageURI(null)
        binding.finalImage.setImageURI(imageuri)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        imageuri = createimageUri()!!
        binding.btnCapture.setOnClickListener{
            cameraContract.launch(imageuri)
        }

        binding.btnUpload.setOnClickListener{
            contract.launch("image/*")
        }

    }

    private fun createimageUri(): Uri? {
        val image = File(applicationContext.filesDir,"camera_photos.png")
        return FileProvider.getUriForFile(applicationContext,"com.example.cameraapp.fileprovider",image)
    }
}