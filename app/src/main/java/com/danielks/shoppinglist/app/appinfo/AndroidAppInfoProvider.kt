package com.danielks.shoppinglist.app.appinfo

import android.content.Context
import android.content.pm.PackageManager
import com.danielks.shoppinglist.R
import com.danielks.shoppinglist.core.appinfo.AppInfo
import com.danielks.shoppinglist.core.appinfo.AppInfoProvider

class AndroidAppInfoProvider(
    private val context: Context
) : AppInfoProvider {

    override fun get(): AppInfo {
        val appName = context.getString(R.string.app_name)
        val versionName = getVersionName() ?: "0.0"
        val credits = "By: Daniel Krieger"

        return AppInfo(
            appName = appName,
            versionName = versionName,
            credits = credits
        )
    }

    private fun getVersionName(): String? {
        val pm = context.packageManager
        val pkg = context.packageName

        return try {
            pm.getPackageInfo(pkg, PackageManager.PackageInfoFlags.of(0)).versionName
        } catch (e: Exception) {
            null
        }
    }
}