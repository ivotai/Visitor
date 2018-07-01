package com.unicorn.visitor.main

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.unicorn.visitor.app.base.BlacklistFra
import com.unicorn.visitor.main.visitRecord.VisitorRecordFra

class MainPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int) = if (position == 0) VisitorRecordFra() else BlacklistFra()

    override fun getCount() = 2

}