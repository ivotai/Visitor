package com.unicorn.visitor.act

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.unicorn.visitor.base.BlacklistFra
import com.unicorn.visitor.base.VisitorRecordFra

class GuardPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int) = when (position) {
        0 -> VisitorRecordFra()
        else -> BlacklistFra()
    }

    override fun getCount() = 2

}