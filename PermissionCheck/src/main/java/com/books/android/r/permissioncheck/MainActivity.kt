package com.books.android.r.permissioncheck

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

@RequiresApi(Build.VERSION_CODES.Q)
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestPermission()

        btn_check.setOnClickListener {
            requestPermission()
        }
    }

    private fun requestPermission() {
        requestPermissions(
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.GET_ACCOUNTS
            ), PERM_REQUEST
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERM_REQUEST -> {
                var sb: StringBuffer = StringBuffer()
                for ((i, result) in permissions.withIndex()) {
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        if (!isPermissionGrarnted(result)) {
                            sb.append(result.split('.')[2]).append(",")
                        }
                    }
                }
                Log.d(TAG, "## not permitted: ${sb.toString().dropLastWhile { it == ',' }}")
            }
        }
    }

    private fun isPermissionGrarnted(permission: String): Boolean {
        return PackageManager.PERMISSION_GRANTED == checkSelfPermission(permission)
    }
}

private const val TAG = "MainActivity"
private const val PERM_REQUEST = 1000
