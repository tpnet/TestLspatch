package com.tpnet.xposedmoduel

import android.content.res.XModuleResources
import android.os.Bundle
import android.util.Log
import de.robv.android.xposed.*
import de.robv.android.xposed.callbacks.XC_InitPackageResources
import de.robv.android.xposed.callbacks.XC_LoadPackage

class EntryHook : IXposedHookLoadPackage, IXposedHookZygoteInit, IXposedHookInitPackageResources {

    companion object {
        var TAG = "EntryHook"
        var MODULE_PATH = ""
        var ID_REMOVE_WHISPER = 0
        val PACKAGE_NAME = "com.tpnet.testlspatch"
    }

    override fun handleLoadPackage(p0: XC_LoadPackage.LoadPackageParam?) {
        if (p0?.packageName == PACKAGE_NAME) {
            XposedHelpers.findAndHookMethod(XposedHelpers.findClass(
                "com.tpnet.testlspatch.MainActivity",
                p0.classLoader
            ),
                "onCreate",
                Bundle::class.java,
                object : XC_MethodHook() {
                    override fun afterHookedMethod(param: MethodHookParam?) {
                        super.afterHookedMethod(param)
                        Log.d(TAG, "设置图片：${ID_REMOVE_WHISPER}")
                        XposedHelpers.callMethod(param?.thisObject, "setImage", ID_REMOVE_WHISPER)
                    }
                })

        }
    }

    override fun initZygote(startupParam: IXposedHookZygoteInit.StartupParam?) {
        MODULE_PATH = startupParam?.modulePath ?: "" //获取模块实际路径
        Log.d(TAG, "执行initZygote:${MODULE_PATH}")

    }

    override fun handleInitPackageResources(resparam: XC_InitPackageResources.InitPackageResourcesParam?) {
        Log.d(TAG, "执行handleInitPackageResources")
        if (resparam?.res == null || resparam.packageName != PACKAGE_NAME || MODULE_PATH.isEmpty()) return

        //这里将自带的图标资源插入到目标中，并获取到一个资源id
        val modRes = XModuleResources.createInstance(MODULE_PATH, resparam.res) //创建一个插入资源的实例
        ID_REMOVE_WHISPER = resparam.res.addResource(modRes, R.mipmap.helper_remove_whisper)
    }
}