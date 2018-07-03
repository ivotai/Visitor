package com.unicorn.visitor.busi.visitRecord

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
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
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.ToastUtils
import com.hwangjr.rxbus.RxBus
import com.unicorn.visitor.R
import com.unicorn.visitor.app.dagger2.component.ComponentsHolder
import com.unicorn.visitor.busi.visitRecord.event.RefreshVisitRecordListEvent
import com.unicorn.visitor.clicks
import com.unicorn.visitor.custom
import com.unicorn.visitor.model.Leader
import com.unicorn.visitor.model.VisitRecord
import com.unicorn.visitor.model.Visitor
import com.unicorn.visitor.util.FileUtil
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.act_add_visit_record.*
import org.joda.time.DateTime
import java.io.File
import java.util.*


class AddVisitRecordAct : AppCompatActivity() {

    private lateinit var leaderList: List<Leader>

    private lateinit var reserveTime: DateTime

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_add_visit_record)

        initAccessTokenWithAkSk()

        val api = ComponentsHolder.appComponent.getGeneralApi()
        api.getAllLeader().custom().subscribeBy(
                onNext = {
                    leaderList = it
                    tvLeader.attachDataSource(leaderList)
                },
                onError = {
                    it
                }
        )

        flBack.clicks().subscribe { finish() }
        flScan.clicks().subscribe { scan() }
        tvConfirm.clicks().subscribe { addVisitRecord() }

        reserveTime = DateTime()
        tvReserveTime.text = reserveTime.toString("yyyy-MM-dd")
        tvReserveTime.clicks().subscribe { showDatePickerDialog() }
    }

    private fun showDatePickerDialog() {
        val now = Calendar.getInstance()
        val dpd = DatePickerDialog.newInstance(
                { _, year, monthOfYear, dayOfMonth ->
                    reserveTime = DateTime(year, monthOfYear + 1, dayOfMonth, 0, 0)
                    tvReserveTime.text = reserveTime.toString("yyyy-MM-dd")
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        )
        dpd.show(fragmentManager, "DatePickerDialog")
    }

    private fun initAccessTokenWithAkSk() {
        OCR.getInstance(this).initAccessTokenWithAkSk(object : OnResultListener<AccessToken> {
            override fun onResult(result: AccessToken) {
                initCameraNativeHelper()
            }

            override fun onError(error: OCRError) {
            }
        }, applicationContext, "bBMwLS4hlgI7p0drv10ZWFgn", "FhHcEjyVhoOQoGeFUDuMz79bPtyBkGiA")
    }

    private fun initCameraNativeHelper() {
        //  初始化本地质量控制模型,释放代码在onDestroy中
        //  调用身份证扫描必须加上 intent.putExtra(CameraActivity.KEY_NATIVE_MANUAL, true); 关闭自动初始化和释放本地模型
        CameraNativeHelper.init(this, OCR.getInstance(this).license) { errorCode, e ->
            val msg = when (errorCode) {
                CameraView.NATIVE_SOLOAD_FAIL -> "加载so失败，请确保apk中存在ui部分的so"
                CameraView.NATIVE_AUTH_FAIL -> "授权本地质量控制token获取失败"
                CameraView.NATIVE_INIT_FAIL -> "本地质量控制"
                else -> errorCode.toString()
            }
//            ToastUtils.showShort("本地质量控制初始化错误，错误原因： $msg")
        }
        scan()
    }

    private fun scan() {
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 233 && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                val contentType = data.getStringExtra(CameraActivity.KEY_CONTENT_TYPE)
                val filePath = FileUtil.getSaveFile(applicationContext).absolutePath
                if (!TextUtils.isEmpty(contentType)) {
                    if (CameraActivity.CONTENT_TYPE_ID_CARD_FRONT == contentType) {
                        recIDCard(filePath)
                    }
                }
            }
        }
    }

    private fun recIDCard(filePath: String) {
        IDCardParams().apply {
            imageFile = File(filePath)
            // 设置身份证正反面
            idCardSide = IDCardParams.ID_CARD_SIDE_FRONT
            // 设置方向检测
            isDetectDirection = true
            // 设置图像参数压缩质量0-100, 越大图像质量越好但是请求时间越长。 不设置则默认值为20
            imageQuality = 20
        }.let {
            OCR.getInstance(this).recognizeIDCard(it, object : OnResultListener<IDCardResult> {
                override fun onResult(result: IDCardResult?) {
                    if (result != null) {
                        tvName.text = result.name.words.trim()
                        tvGender.text = result.gender.words.trim()
                        tvIdCard.text = result.idNumber.words.trim()

                        FileUtils.copyFile(File(filePath), File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "1.jpg"))
                    }
                }

                override fun onError(error: OCRError) {
//                    ToastUtils.showShort(error.message)
                }
            })
        }
    }

    private fun addVisitRecord() {
        if (TextUtils.isEmpty(tvName.text)) {
            ToastUtils.showShort("请扫描身份证")
            return
        }
        if (TextUtils.isEmpty(etDescription.text)) {
            ToastUtils.showShort("请填写来访事由")
            return
        }

        val visitor = Visitor(name = tvName.text.toString(), gender = tvGender.text.toString(),
                idCard = tvIdCard.text.toString(), company = etCompany.text.toString())
        val leader = leaderList[tvLeader.selectedIndex]
        val record = VisitRecord(visitor, leader, reserveTime = reserveTime.toDate().time, description = etDescription.text.toString())
        val api = ComponentsHolder.appComponent.getGeneralApi()
        api.addVisitRecord(record).custom().subscribeBy(
                onNext = {
                    if (it.success) {
                        ToastUtils.showShort("访客登记完成")
                        RxBus.get().post(RefreshVisitRecordListEvent())
                        finish()
                    } else {
                        ToastUtils.showShort(it.message)
                    }
                },
                onError = {}
        )
    }

    override fun onDestroy() {
        // 释放本地质量控制模型
        CameraNativeHelper.release()
        super.onDestroy()
    }

}
