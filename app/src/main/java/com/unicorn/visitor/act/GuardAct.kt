package com.unicorn.visitor.act

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.baidu.ocr.sdk.OCR
import com.baidu.ocr.sdk.OnResultListener
import com.baidu.ocr.sdk.exception.OCRError
import com.baidu.ocr.sdk.model.AccessToken
import com.baidu.ocr.sdk.model.IDCardParams
import com.baidu.ocr.sdk.model.IDCardResult
import com.baidu.ocr.ui.camera.CameraActivity
import com.baidu.ocr.ui.camera.CameraNativeHelper
import com.baidu.ocr.ui.camera.CameraView
import com.blankj.utilcode.util.ToastUtils
import com.mikepenz.fontawesome_typeface_library.FontAwesome
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.IIcon
import com.unicorn.visitor.R
import com.unicorn.visitor.orc.FileUtil
import kotlinx.android.synthetic.main.act_guard.*
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem
import me.majiajie.pagerbottomtabstrip.item.NormalItemView2
import java.io.File

class GuardAct : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_guard)
        initViewPager()
        initAccessTokenWithAkSk()
    }

    private fun initViewPager() {
        val navigationController = tab.custom()
                .addItem(newItem(FontAwesome.Icon.faw_address_card, FontAwesome.Icon.faw_address_card2, "来访记录"))
                .addItem(newItem(FontAwesome.Icon.faw_address_book, FontAwesome.Icon.faw_address_book2, "黑名单"))
                .build()
        navigationController.setupWithViewPager(viewPager)
        viewPager.offscreenPageLimit = 1
        viewPager.adapter = GuardPagerAdapter(supportFragmentManager)
    }

    private val defaultColor = Color.parseColor("#aeaeae")
    private val checkedColor = Color.parseColor("#457ADB")

    private fun newItem(default: IIcon, checked: IIcon, text: String): BaseTabItem {
        val normalItemView = NormalItemView2(this)
        normalItemView.initialize(
                IconicsDrawable(this)
                        .icon(default)
                        .color(defaultColor)
                        .sizeDp(24),
                IconicsDrawable(this)
                        .icon(checked)
                        .color(checkedColor)
                        .sizeDp(24),
                text)
        normalItemView.setTextDefaultColor(defaultColor)
        normalItemView.setTextCheckedColor(checkedColor)
        return normalItemView
    }

    //

    private fun initAccessTokenWithAkSk() {
        OCR.getInstance(this).initAccessTokenWithAkSk(object : OnResultListener<AccessToken> {
            override fun onResult(result: AccessToken) {
                val token = result.accessToken
                ToastUtils.showShort("初始化成功")
//                hasGotToken = true
                init()
            }

            override fun onError(error: OCRError) {
//                error.printStackTrace()
                ToastUtils.showShort("AK，SK方式获取token失败", error.message)
            }
        }, applicationContext, "bBMwLS4hlgI7p0drv10ZWFgn", "FhHcEjyVhoOQoGeFUDuMz79bPtyBkGiA")
    }

    override fun onBackPressed() {
        val intent = Intent(this, CameraActivity::class.java)
        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                FileUtil.getSaveFile(application).absolutePath)
        intent.putExtra(CameraActivity.KEY_NATIVE_ENABLE,
                true)
        // KEY_NATIVE_MANUAL设置了之后CameraActivity中不再自动初始化和释放模型
        // 请手动使用CameraNativeHelper初始化和释放模型
        // 推荐这样做，可以避免一些activity切换导致的不必要的异常
        intent.putExtra(CameraActivity.KEY_NATIVE_MANUAL,
                true)
        intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT)
        startActivityForResult(intent, 233)
    }

    private fun init() {

        //  初始化本地质量控制模型,释放代码在onDestory中
        //  调用身份证扫描必须加上 intent.putExtra(CameraActivity.KEY_NATIVE_MANUAL, true); 关闭自动初始化和释放本地模型
        CameraNativeHelper.init(this, OCR.getInstance(this).license
        ) { errorCode, e ->
            val msg: String
            when (errorCode) {
                CameraView.NATIVE_SOLOAD_FAIL -> msg = "加载so失败，请确保apk中存在ui部分的so"
                CameraView.NATIVE_AUTH_FAIL -> msg = "授权本地质量控制token获取失败"
                CameraView.NATIVE_INIT_FAIL -> msg = "本地质量控制"
                else -> msg = errorCode.toString()
            }
            ToastUtils.showShort("本地质量控制初始化错误，错误原因： $msg")
        }
    }

    private fun recIDCard(filePath: String) {
        val param = IDCardParams()
        param.imageFile = File(filePath)
        // 设置身份证正反面
        param.idCardSide = IDCardParams.ID_CARD_SIDE_FRONT
        // 设置方向检测
        param.isDetectDirection = true
        // 设置图像参数压缩质量0-100, 越大图像质量越好但是请求时间越长。 不设置则默认值为20
        param.imageQuality = 20

        OCR.getInstance(this).recognizeIDCard(param, object : OnResultListener<IDCardResult> {
            override fun onResult(result: IDCardResult?) {
                if (result != null) {
                    ToastUtils.showShort("result")
                }
            }

            override fun onError(error: OCRError) {
                ToastUtils.showShort(error.message)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 233 && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                val contentType = data.getStringExtra(CameraActivity.KEY_CONTENT_TYPE)
                val filePath = FileUtil.getSaveFile(applicationContext).absolutePath
                if (!TextUtils.isEmpty(contentType)) {
                    if (CameraActivity.CONTENT_TYPE_ID_CARD_FRONT == contentType) {
                        recIDCard( filePath)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        // 释放本地质量控制模型
        CameraNativeHelper.release()
        super.onDestroy()
    }

}
