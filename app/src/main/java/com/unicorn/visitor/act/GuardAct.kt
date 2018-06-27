package com.unicorn.visitor.act

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.mikepenz.fontawesome_typeface_library.FontAwesome
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.IIcon
import com.mikepenz.ionicons_typeface_library.Ionicons
import com.unicorn.visitor.R
import kotlinx.android.synthetic.main.act_guard.*
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem
import me.majiajie.pagerbottomtabstrip.item.NormalItemView2

class GuardAct : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_guard)
        initViewPager()
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
}
