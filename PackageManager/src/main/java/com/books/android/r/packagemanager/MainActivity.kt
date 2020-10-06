package com.books.android.r.packagemanager

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

@RequiresApi(Build.VERSION_CODES.R)
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pkgName = "com.twitter.android"
//        val pkgName = packageName
//        val installerPackageName = packageManager.getInstallerPackageName(pkgName)
        if (Build.VERSION.SDK_INT > 29) {
            val installSourceInfo = packageManager.getInstallSourceInfo(pkgName)

            tv_1.text = "packageName: $packageName\n"
                .plus("initiatingPackageName: ${installSourceInfo.initiatingPackageName}\n")
                .plus("installingPackageName: ${installSourceInfo.installingPackageName}\n")
                .plus("originatingPackageName: ${installSourceInfo.originatingPackageName}\n")
//            .plus("installerPackageName: $installerPackageName")
        }
    }
}
