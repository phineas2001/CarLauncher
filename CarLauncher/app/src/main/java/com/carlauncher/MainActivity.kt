package com.carlauncher

import android.app.ActivityOptions
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper())
    private lateinit var tvTime: TextView
    private lateinit var tvDate: TextView

    private val clockRunnable = object : Runnable {
        override fun run() {
            updateClock()
            handler.postDelayed(this, 1000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Keep screen on while launcher is active
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        // Full screen immersive
        window.decorView.systemUiVisibility = (
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        )

        setContentView(R.layout.activity_main)

        tvTime = findViewById(R.id.tv_time)
        tvDate = findViewById(R.id.tv_date)

        setupAppButtons()
        handler.post(clockRunnable)
    }

    private fun updateClock() {
        val now = Date()
        tvTime.text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(now)
        tvDate.text = SimpleDateFormat("EEE, dd MMM", Locale("pt", "PT")).format(now)
    }

    private fun setupAppButtons() {
        findViewById<View>(R.id.btn_waze).setOnClickListener { launchApp("com.waze") }
        findViewById<View>(R.id.btn_maps).setOnClickListener { launchApp("com.google.android.apps.maps") }
        findViewById<View>(R.id.btn_spotify).setOnClickListener { launchApp("com.spotify.music") }
        findViewById<View>(R.id.btn_youtube).setOnClickListener { launchApp("com.google.android.youtube") }
        findViewById<View>(R.id.btn_phone).setOnClickListener { launchDialer() }
        findViewById<View>(R.id.btn_contacts).setOnClickListener { launchContacts() }
        findViewById<View>(R.id.btn_triprank).setOnClickListener { launchTripRank() }
    }

    private fun launchApp(packageName: String) {
        val pm = packageManager
        val intent = pm.getLaunchIntentForPackage(packageName)
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val options = ActivityOptions.makeCustomAnimation(
                this,
                android.R.anim.fade_in,
                android.R.anim.fade_out
            )
            startActivity(intent, options.toBundle())
        } else {
            // App not installed - open Play Store
            try {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
            } catch (e: Exception) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packageName")))
            }
        }
    }

    private fun launchDialer() {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun launchContacts() {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            type = "vnd.android.cursor.dir/contact"
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intent)
    }

    private fun launchTripRank() {
        // Try TripRank package name variants
        val packages = listOf("com.triprank", "com.triprank.app", "triprank.app")
        for (pkg in packages) {
            val intent = packageManager.getLaunchIntentForPackage(pkg)
            if (intent != null) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                return
            }
        }
        // Fallback: search on Play Store
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=TripRank")))
        } catch (e: Exception) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/search?q=TripRank")))
        }
    }

    override fun onResume() {
        super.onResume()
        // Re-hide system UI on resume
        window.decorView.systemUiVisibility = (
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(clockRunnable)
    }
}
