package com.github.midros.tmdb.ui.activities.base

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.github.midros.tmdb.di.components.ActivityComponent
import io.reactivex.disposables.Disposable

/**
 * Created by luis rafael on 16/02/19.
 */
abstract class BaseFragment : Fragment(), InterfaceView {

    private var activity: BaseActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity) {
            this.activity = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        activity = null
    }

    private fun isNotNullActivity() : Boolean = activity!=null

    fun getWindowFlagStable(){
        if (isNotNullActivity()) activity!!.getWindowFlagStable()
    }

    fun getActivityComponent(): ActivityComponent? {
        return if (isNotNullActivity()) {
            activity!!.getActivityComponent()
        } else null
    }

    override fun addDisposable(disposable: Disposable) {
        if (isNotNullActivity()) activity!!.addDisposable(disposable)
    }

    override fun clearDisposable() {
        if (isNotNullActivity()) activity!!.clearDisposable()
    }

    override fun showMessage(message: String) {
        if (isNotNullActivity()) activity!!.showMessage(message)
    }

    override fun showDialog(title: Int, message: String, btnMsgOk: Int, btnOk: (DialogInterface) -> Unit): AlertDialog {
        return activity!!.showDialog(title,message,btnMsgOk,btnOk)
    }


    override fun hideKeyboard() {
        if (isNotNullActivity()) activity!!.hideKeyboard()
    }

    fun getBaseActivity(): BaseActivity = activity!!

    interface Callback {
        fun setMinimizeDraggable()
        fun isMaximized() : Boolean
        fun setMaximizeDraggable(id: Int, name: String, type: String, image: String)
        fun setCueVideo(key: String)
        fun setSelectedNavigationMovie(itemId:Int)
        fun setSelectedNavigationTv(itemId: Int)
        fun moveActivityToBack()
    }


}
