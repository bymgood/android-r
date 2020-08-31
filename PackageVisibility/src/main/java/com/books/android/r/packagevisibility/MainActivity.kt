package com.books.android.r.packagevisibility

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.layout_main.*
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_main)

        btn_list_installed.setOnClickListener {
            listInstalled()
        }

//        isIntentAvailable(this, "onestore://common")
    }

    private fun callPlayStore() {
        startActivity(getGooglePlayIntent())
    }

    private fun getGooglePlayIntent(): Intent {
        val GOOGLE_PLAY_APP_URL = "market://details?id=com.skt.skaf.OA00050017"
        val intent = Intent(Intent.ACTION_VIEW)
        with(intent) {
            setData(Uri.parse(GOOGLE_PLAY_APP_URL))
        }

        return intent
    }

    /**
     * 1. install twitter app from google play store
     * 2. see logcat if there's BLOCKED when you try to read package information from twitter
     *  BLOCKED log:
     *      2020-09-01 01:21:17.148 488-3515/? I/AppsFilter:
     *      interaction: PackageSetting{b54bcac com.books.android.r.packagevisibility.r/10153} ->
     *      PackageSetting{97e2684 com.twitter.android/10152} BLOCKED
     *
     *  Added <queries> tag:
     *      2020-09-01 01:23:56.182 13723-13723/com.books.android.r.packagevisibility.r D/MainActivity:
     *      ## Package name: com.twitter.android
     */
    private fun listInstalled() {
        val packages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)

        val sb = StringBuilder()
        for (packageInfo in packages) {
            sb.append("${packageInfo.packageName}").append("\n")
        }

        tv_out.setText(sb.toString())
    }


//    private fun isIntentAvailable(context: Context?, uri: String?) {
//        val packageManager = context?.packageManager
//        val intent = Intent()
//        intent.data = Uri.parse(uri)
//        intent.addFlags(Intent.FLAG_DEBUG_LOG_RESOLUTION)
//        val list = packageManager?.queryIntentActivities(intent, 0)
//
//        if (list != null) {
//            for (resolveInfo in list) {
//                Log.d(TAG, "## resolveInfo: ${resolveInfo}")
//            }
//        }
//    }

    private val TAG = "MainActivity"
}