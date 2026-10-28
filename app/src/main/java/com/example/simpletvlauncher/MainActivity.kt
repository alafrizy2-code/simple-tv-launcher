package com.example.simpletvlauncher

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simpletvlauncher.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val apps = loadInstalledApps()
        val favoriteApps = if (apps.size >= 5) apps.subList(0, 5) else apps

        val actions = listOf(
            ActionButtonItem("Settings", "settings"),
            ActionButtonItem("Apps", "apps")
        )

        binding.recyclerActions.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerActions.adapter = ActionButtonAdapter(actions) { action ->
            when (action.action) {
                "settings" -> {
                    startActivity(Intent(android.provider.Settings.ACTION_SETTINGS))
                }
                "apps" -> {
                    val intent = Intent(Intent.ACTION_MAIN).apply {
                        addCategory(Intent.CATEGORY_APP_MARKET)
                    }
                    try {
                        startActivity(intent)
                    } catch (_: Exception) {
                        val fallback = Intent(android.provider.Settings.ACTION_SETTINGS)
                        startActivity(fallback)
                    }
                }
            }
        }

        binding.recyclerFavorites.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerFavorites.adapter = FavoriteAdapter(favoriteApps) { app ->
            launchApp(app.packageName)
        }

        binding.recyclerApps.layoutManager = GridLayoutManager(this, 5)
        binding.recyclerApps.adapter = AppAdapter(apps) { app ->
            launchApp(app.packageName)
        }

        updateClock()
    }

    private fun updateClock() {
        val runnable = object : Runnable {
            override fun run() {
                val time = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
                val date = SimpleDateFormat("EEE, dd MMM", Locale.getDefault()).format(Date())

                binding.tvTime.text = time
                binding.tvDate.text = date
                handler.postDelayed(this, 1000)
            }
        }
        handler.post(runnable)
    }

    private fun loadInstalledApps(): List<AppItem> {
        val pm = packageManager
        val mainIntent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }

        val resolveInfos = pm.queryIntentActivities(mainIntent, PackageManager.MATCH_ALL)
            .filter { it.activityInfo != null }
            .sortedBy { it.loadLabel(pm).toString().lowercase() }

        val result = mutableListOf<AppItem>()

        for (info in resolveInfos) {
            val label = info.loadLabel(pm).toString()
            val packageName = info.activityInfo.packageName
            val icon = info.loadIcon(pm)

            if (packageName != this.packageName) {
                result.add(AppItem(label, packageName, icon))
            }
        }

        return result
    }

    private fun launchApp(packageName: String) {
        val launchIntent = packageManager.getLaunchIntentForPackage(packageName)
        if (launchIntent != null) {
            startActivity(launchIntent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}
