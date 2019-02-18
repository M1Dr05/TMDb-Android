package com.github.midros.tmdb.ui.activities.base

import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import io.reactivex.disposables.Disposable

/**
 * Created by luis rafael on 16/02/19.
 */
interface InterfaceView  {

    fun hideKeyboard()

    fun showMessage(message: String)

    fun showDialog(title:Int,message:String,btnMsgOk:Int,btnOk:(DialogInterface)->Unit) : AlertDialog

    fun addDisposable(disposable: Disposable)

    fun clearDisposable()

}