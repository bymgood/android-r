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
import androidx.core.os.BuildCompat
import kotlinx.android.synthetic.main.activity_main.*

@RequiresApi(Build.VERSION_CODES.Q)
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestPermission()
    }

    private fun requestPermission() {
        val phonePermission = when ("STATE" == getString(R.string.app_name)) {
            true -> arrayOf(Manifest.permission.READ_PHONE_STATE)
            else -> arrayOf(Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_PHONE_NUMBERS)
        }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
        requestPermissions(phonePermission, PERM_REQUEST)
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
                    tv_phone_number.text = getPhoneNumber().plus("\n")
                                                .plus(getNetworkType())
                }

            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getPhoneNumber(): String {
        return try {
            val tm = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            "PhoneNumber: ".plus(tm.line1Number)
        } catch (ex: Exception) {
            ex.printStackTrace()
            "Exception: ".plus(ex.message)
        }
    }

    @SuppressLint("MissingPermission")
    private fun getNetworkType(): String {
        return try {
            val tm = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            "NetworkType: ".plus(tm.networkType)
        } catch (ex: Exception) {
            ex.printStackTrace()
            "Exception: ".plus(ex.message)
        }
    }


    private fun isPermissionGranted(permission: String): Boolean {
        return PackageManager.PERMISSION_GRANTED == checkSelfPermission(permission)
    }

}

private const val TAG = "MainActivity"
private const val PERM_REQUEST = 1000
