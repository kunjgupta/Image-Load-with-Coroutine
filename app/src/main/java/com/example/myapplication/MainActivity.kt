package com.example.myapplication

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.net.URL

class MainActivity : AppCompatActivity() {
    private val IMAGE_URL = "http://ichef.bbci.co.uk/onesport/cps/480/cpsprodpb/11136/production/_95324996_defoe_rex.jpg"

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        coroutineScope.launch {
            val deferred = coroutineScope.async(Dispatchers.IO) {
                getDownloadImage()
            }

            val originalBitmap = deferred.await()

            loadImage(originalBitmap)
        }
    }

    private fun getDownloadImage() =
        URL(IMAGE_URL).openStream().use {
            BitmapFactory.decodeStream(it)
        }

    private fun loadImage(bmp : Bitmap) {
        findViewById<ProgressBar>(R.id.progress_bar).visibility = View.GONE
        findViewById<ImageView>(R.id.image_view).setImageBitmap(bmp)
        findViewById<ImageView>(R.id.image_view).visibility = View.VISIBLE
    }
}