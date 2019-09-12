package com.github.midros.tmdb.ui.activities.main

import com.github.midros.tmdb.ui.activities.base.InterfaceView

/**
 * Created by luis rafael on 16/02/19.
 */
interface InterfaceMainView : InterfaceView {

    fun setCheckedNavigationItem(id: Int)
    fun closeNavigationDrawer()
    fun setTitleToolbar(title: String)
    fun setDrawerUnLock()
    fun setDrawerLock()
}