package com.books.android.r.phonestate

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.telephony.TelephonyManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

@RequiresApi(Build.VERSION_CODES.Q)
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestPermission()
    }

    private fun requestPermission() {
        val phonePermission = when (BuildConfig.FLAVOR?.equals("phonestate")) {
            true -> Manifest.permission.READ_PHONE_STATE
            else -> Manifest.permission.READ_PHONE_NUMBERS
        }

        requestPermissions(
            arrayOf(
                phonePermission
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
                        if (!isPermissionGranted(result)) {
                            sb.append(result.split('.')[2]).append("\n")
                        }
                    }
                }

                if (sb.isNotEmpty()) {
                    sb.append("----------------------------------------------\n")
                    sb.append("Not permitted.")

                    tv_phone_number.text = "Not permitted"
                } else {
                    tv_phone_number.text = getPhoneNumber()
                }

            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getPhoneNumber(): String {
        val number: String
        try {
            val tm = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            number = tm.line1Number
        } catch (ex: Exception) {
            return ex.message!!
        }
        return number
    }

    private fun isPermissionGranted(permission: String): Boolean {
        return PackageManager.PERMISSION_GRANTED == checkSelfPermission(permission)
    }

}

private const val TAG = "MainActivity"
private const val PERM_REQUEST = 1000
