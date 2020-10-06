package com.books.android.r.systemuivisibility

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn_apply).setOnClickListener {
            applyNavigationColors()
        }
    }

    private fun applyNavigationColors() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            if (mNavigationBarColor.isNotBlank()) {
                try {
                    window.navigationBarColor = Color.parseColor("#00B700")

                    if (isDarkNavigationButton()) {
                        lightNavigationBar()
                    } else {
                        lightNavigationBarInv()
                    }
                } catch (ignore: Exception) {
                }
//            }
        }
    }

    private fun lightNavigationBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS, WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS)
        } else {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        }
    }

    private fun lightNavigationBarInv() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.setSystemBarsAppearance(0, WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS)
        } else {
            window.decorView.systemUiVisibility = window.decorView.systemUiVisibility.and(
                View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR.inv()
            )
        }
    }

    var flag: Boolean = true
    private fun isDarkNavigationButton(): Boolean {
        flag = !flag
        return flag
    }
}
