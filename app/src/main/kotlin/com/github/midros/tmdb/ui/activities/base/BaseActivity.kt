package com.github.midros.tmdb.ui.activities.base

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.github.midros.tmdb.app.TmdbApp
import com.github.midros.tmdb.di.components.ActivityComponent
import com.github.midros.tmdb.di.components.DaggerActivityComponent
import com.github.midros.tmdb.di.modules.ActivityModule
import com.github.midros.tmdb.utils.KeyboardUtils
import com.pawegio.kandroid.longToast
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import com.pawegio.kandroid.alert


/**
 * Created by luis rafael on 16/02/19.
 */
abstract class BaseActivity : AppCompatActivity(), InterfaceView, BaseFragment.Callback {

    companion object {
        @JvmStatic var component: ActivityComponent? = null

        @JvmStatic var compositeDisposable: CompositeDisposable?=null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component = DaggerActivityComponent.builder().activityModule(ActivityModule(this))
                .appsComponent(TmdbApp.component).build()
        compositeDisposable = CompositeDisposable()
    }

    fun getWindowFlagStable(){
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
    }

    fun getActivityComponent(): ActivityComponent? {
        return if (component != null) component else null
    }

    override fun hideKeyboard() {
        KeyboardUtils.hiddenKeyboard(this)
    }

    override fun showMessage(message: String) {
        longToast(message)
    }

    override fun showDialog(title: Int, message: String, btnMsgOk: Int, btnOk: (DialogInterface) -> Unit) : AlertDialog =
            alert(message,getString(title)){
                positiveButton(btnMsgOk){ btnOk(this) }
            }.builder.create()

    override fun addDisposable(disposable: Disposable){
        compositeDisposable!!.add(disposable)
    }

    override fun clearDisposable() {
        compositeDisposable!!.clear()
    }

    override fun setMinimizeDraggable() {}
    override fun setMaximizeDraggable(id: Int, name: String, type: String, image: String) {}
    override fun setCueVideo(key: String) {}
    override fun isMaximized(): Boolean = true
    override fun setSelectedNavigationMovie(itemId: Int) {}
    override fun setSelectedNavigationTv(itemId: Int) {}

}