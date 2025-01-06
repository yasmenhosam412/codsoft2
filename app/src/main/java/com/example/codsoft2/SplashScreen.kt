package com.example.codsoft2

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.animation.RotateAnimation
import androidx.appcompat.app.AppCompatActivity
import com.example.codsoft2.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)





        val rotateAnimation = RotateAnimation(
            0f, 360f,
            RotateAnimation.RELATIVE_TO_SELF, 0.5f,
            RotateAnimation.RELATIVE_TO_SELF, 0.5f
        ).apply {
            duration = 1500
            repeatCount = 0
            fillAfter = true
        }
        binding.textView4.startAnimation(rotateAnimation)

        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000) // 3 seconds delay

        displaySavedImageAndColor()

    }

    private fun displaySavedImageAndColor() {
        // Retrieve the saved image and color
        val sharedPreferences = getSharedPreferences("userPreferences", MODE_PRIVATE)
        val savedImage = sharedPreferences.getInt("selectedImage", R.drawable.ba10)  // Default image if none is saved
        val savedColor = sharedPreferences.getString("selectedColor", "#FAF4E6")  // Default color if none is saved

        // Apply the saved image and color
        binding.main.setBackgroundColor(Color.parseColor(savedColor))
        binding.imageView5.setImageResource(savedImage)
    }

}
