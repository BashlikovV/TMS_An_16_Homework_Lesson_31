package by.bashlikovvv.tms_an_16_homework_lesson_31

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import by.bashlikovvv.tms_an_16_homework_lesson_31.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bannerButton.setOnClickListener {
            if (binding.bannerContainer.isVisible) {
                binding.bannerContainer.hideBanner()
            } else {
                binding.bannerContainer.showBanner()
            }
        }
        binding.bannerContainer.setClickListeners(object : CustomBannerLayout.OnCLikListeners {
            override fun notifyFirstButtonClicked() {
                Toast
                    .makeText(this@MainActivity, "First button clicked!", Toast.LENGTH_LONG)
                    .show()
            }
            override fun notifySecondButtonClicked() {
                Toast
                    .makeText(this@MainActivity, "Second button clicked!", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}