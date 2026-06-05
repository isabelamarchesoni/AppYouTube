package br.edu.fatecpg.appfirebase.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.fatecpg.appfirebase.databinding.ActivityTrailerBinding

class TrailerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTrailerBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrailerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val youtubeId = intent.getStringExtra("youtube_id") ?: return
        val titulo = intent.getStringExtra("titulo") ?: "Trailer"

        supportActionBar?.title = titulo
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.webView.settings.apply {
            javaScriptEnabled = true
            mediaPlaybackRequiresUserGesture = false
        }

        val embedUrl = "https://www.youtube.com/embed/$youtubeId?autoplay=1&rel=0"

        val html = """
            <!DOCTYPE html>
            <html>
            <head>
              <meta name="viewport" content="width=device-width, initial-scale=1.0">
              <style>
                * { margin: 0; padding: 0; box-sizing: border-box; background: #000; }
                body { display: flex; justify-content: center; align-items: center; height: 100vh; }
                iframe { width: 100%; height: 100%; position: absolute; top: 0; left: 0; }
              </style>
            </head>
            <body>
              <iframe src="${"$"}embedUrl"
                frameborder="0"
                allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                allowfullscreen>
              </iframe>
            </body>
            </html>
        """.trimIndent()

        binding.webView.loadData(html, "text/html", "utf-8")
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onPause() {
        super.onPause()
        binding.webView.onPause()
    }

    override fun onResume() {
        super.onResume()
        binding.webView.onResume()
    }

    override fun onDestroy() {
        binding.webView.destroy()
        super.onDestroy()
    }
}