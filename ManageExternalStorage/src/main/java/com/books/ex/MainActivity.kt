package com.books.ex

import android.app.AppOpsManager
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileOutputStream


@RequiresApi(Build.VERSION_CODES.Q)
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        open_file.setOnClickListener {
            readWriteFileTest()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_STORAGE) {
            Log.d(TAG, "permissions: ${permissions[0]}, grantResults: ${grantResults[0]}")
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun writeImage(bm: Bitmap) {
        FileOutputStream(File(IMAGE_PATH)).apply {
            bm.compress(Bitmap.CompressFormat.PNG, 100, this)
            flush()
            close()
        }
    }

    private fun readImage() = File(IMAGE_PATH).apply {
        Log.d(TAG, "## File path: $absolutePath")

        call_to_action.text = absolutePath
        open_file_icon.setImageBitmap(BitmapFactory.decodeFile(absolutePath))
    }

    private fun readWriteFileTest() {
        if (hasAllFilesPermission()) {
            // save a file to storage
            writeImage(BitmapFactory.decodeResource(resources, R.drawable.useravatar))

            // read a image file from storage
            readImage()
        } else {
            requestAllFilePermission()
        }
    }

    private fun requestAllFilePermission() {
        if (Build.VERSION.SDK_INT >= 29 && Build.VERSION.PREVIEW_SDK_INT > 0) {
            if (hasAllFilesPermission()) {
                Toast.makeText(this, R.string.already_permission, Toast.LENGTH_LONG).show()
                return
            }

            val uri = Uri.parse("package:${packageName}")

            startActivity(
                Intent(
                    Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
                    uri
                )
            )
        } else {
            Toast.makeText(this, R.string.sorry, Toast.LENGTH_LONG).show()
        }
    }

    private fun hasAllFilesPermission(): Boolean {
        val appOpsManager: AppOpsManager = getSystemService(AppOpsManager::class.java)!!

        val permission = try {
            appOpsManager.unsafeCheckOpNoThrow(
                "android:manage_external_storage",
                android.os.Process.myUid(),
                packageName
            ) == AppOpsManager.MODE_ALLOWED
        } catch (e: PackageManager.NameNotFoundException) {
            return false
        }

        Log.d(TAG, "## hasAllFilesPermission: $permission")

        return permission
    }

}

private const val TAG = "MainActivity"
private const val PERMISSION_STORAGE = 1000
private const val IMAGE_PATH = "/sdcard/ONEstore/Comic/saved.png"