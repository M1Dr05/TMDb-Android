package com.github.midros.tmdb.ui.activities.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import com.github.midros.tmdb.R
import com.github.midros.tmdb.ui.activities.base.BaseActivity
import com.github.midros.tmdb.ui.fragments.details.FragmentDetailsBottom
import com.github.midros.tmdb.ui.fragments.details.FragmentDetailsTop
import com.github.midros.tmdb.utils.ConstStrings
import com.github.midros.tmdb.utils.ConstStrings.Companion.ID
import com.github.midros.tmdb.utils.ConstStrings.Companion.IMAGE
import com.github.midros.tmdb.utils.ConstStrings.Companion.TITLE
import com.github.midros.tmdb.utils.ConstStrings.Companion.TYPE
import com.github.pedrovgs.DraggableListener
import com.miguelcatalan.materialsearchview.MaterialSearchView
import com.pawegio.kandroid.show
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import com.github.midros.tmdb.utils.KeyboardUtils
import com.github.midros.tmdb.utils.getVersion
import com.pawegio.kandroid.hide
import com.pawegio.kandroid.runDelayedOnUiThread


/**
 * Created by luis rafael on 16/02/19.
 */
class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener, MaterialSearchView.SearchViewListener, MaterialSearchView.OnQueryTextListener, DraggableListener, InterfaceMainView, KeyboardUtils.SoftKeyboardToggleListener {

    private var scroll:Int =0

    @Inject
    lateinit var interactor: InterfaceMainInteractor<InterfaceMainView>

    @Inject lateinit var draggableTopFragment: FragmentDetailsTop
    @Inject lateinit var draggableBottomFragment: FragmentDetailsBottom


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getActivityComponent()!!.inject(this)
        interactor.onAttach(this)
        setSupportActionBar(toolbar)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        interactor.setFragmentHome()
        initializeDrawerLayout()
        initializeDraggable()
        initializeSearchView()
        initializeKeyboardListener()
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        if (interactor.getFragmentHome().isAdded) interactor.getFragmentHome().onUserInteraction()
        else interactor.getFragmentHome().onStopTimer()
    }

    private fun initializeKeyboardListener() {
        KeyboardUtils.addKeyboardToggleListener(this, this)
    }

    override fun onToggleSoftKeyboard(isVisible: Boolean) {
        runDelayedOnUiThread(200) {
            if (interactor.getFragmentSearch().isAdded && !draggable_main.isMaximized) {
                if (!draggable_main.isClosedAtLeft && !draggable_main.isClosedAtRight)
                    setMinimizeDraggable()
            }
        }
    }

    override fun onDestroy() {
        interactor.onDetach()
        clearDisposable()
        super.onDestroy()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val item = menu!!.findItem(R.id.nav_search)
        search_toolbar.setMenuItem(item)
        return true
    }

    private fun initializeSearchView() {
        search_toolbar.clearFocus()
        search_toolbar.setOnSearchViewListener(this)
        search_toolbar.setOnQueryTextListener(this)
    }

    private fun initializeDraggable() {

        draggable_main.apply {
            setFragmentManager(supportFragmentManager)
            setTopFragment(draggableTopFragment)
            setBottomFragment(draggableBottomFragment)

            var typedValue = TypedValue()
            resources.getValue(R.dimen.x_scale_factor, typedValue, true)
            val xScaleFactor = typedValue.float
            typedValue = TypedValue()
            resources.getValue(R.dimen.y_scale_factor, typedValue, true)
            val yScaleFactor = typedValue.float

            setXScaleFactor(xScaleFactor)
            setYScaleFactor(yScaleFactor)
            setTopViewHeight(resources.getDimensionPixelSize(R.dimen.top_fragment_height))
            setTopFragmentMarginRight(resources.getDimensionPixelSize(R.dimen.top_fragment_margin))
            setTopFragmentMarginBottom(resources.getDimensionPixelSize(R.dimen.top_fragment_margin))

            isClickToMaximizeEnabled = true
            hide()
        }
        draggable_main.setDraggableListener(this)
        draggable_main.initializeView()
    }

    private fun initializeDrawerLayout() {
        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun setCheckedNavigationItem(id: Int) {
        nav_view.menu.findItem(id).isChecked = true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        closeNavigationDrawer()
        when (item.itemId) {
            R.id.nav_home -> if (!item.isChecked) interactor.setFragmentHome()
            R.id.nav_movies -> if (!item.isChecked) setSelectedNavigationMovie(R.id.nav_popular_movies)
            R.id.nav_tv_shows -> if (!item.isChecked) setSelectedNavigationTv(R.id.nav_popular_tv)
            R.id.nav_people -> if (!item.isChecked) interactor.setFragmentPeople()
            R.id.nav_about -> showDialog(R.string.title_dialog,getVersion(),android.R.string.ok){ it.dismiss() }.show()
        }
        return true
    }

    override fun setTitleToolbar(title: String) {
        supportActionBar!!.title = title
    }

    override fun closeNavigationDrawer() {
        if (drawer_layout != null) {
            drawer_layout.closeDrawer(GravityCompat.START)
        }
    }

    override fun onBackPressed() {
        when {
            draggable_main.isMaximized -> setMinimizeDraggable()
            search_toolbar.isSearchOpen -> search_toolbar.closeSearch()
            drawer_layout.isDrawerOpen(GravityCompat.START) -> closeNavigationDrawer()
            else -> super.onBackPressed()
        }
    }

    override fun isMaximized(): Boolean = draggable_main.isMaximized

    override fun setMinimizeDraggable() {
        draggable_main.minimize()
    }

    override fun setMaximizeDraggable(id: Int, name: String, type: String, image: String) {
        if (draggableTopFragment.getIdDetails() != id) {
            draggableTopFragment.setDetailsTop(id, name, type, image)
            draggableBottomFragment.setDetailsBottom(id, type)
        }
        draggable_main.show()
        draggable_main.maximize()
    }

    override fun setCueVideo(key: String) {
        draggableTopFragment.setCueVideo(key)
    }

    override fun setDrawerLock() {
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    override fun setDrawerUnLock() {
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }

    override fun onMaximized() {
        setDrawerLock()
        hideKeyboard()
        if (draggableTopFragment.isAdded)
            draggableTopFragment.showMenuDetailsTop()
    }

    override fun onMinimized() {
        if (!interactor.getFragmentSearch().isAdded) setDrawerUnLock()
        if (draggableTopFragment.isAdded)
            draggableTopFragment.hiddenMenuDetailsTop()
    }

    override fun onClosedToLeft() {
        if (draggableTopFragment.isAdded)
            draggableTopFragment.setPausePlayer()
    }

    override fun onClosedToRight() {
        if (draggableTopFragment.isAdded)
            draggableTopFragment.setPausePlayer()
    }

    override fun onSearchViewClosed() {
        hideKeyboard()
        runDelayedOnUiThread(500) {
            interactor.closeFragmentSearch(ConstStrings.SEARCH_TAG)
        }
    }

    override fun onSearchViewShown() {
        interactor.setFragmentSearch()
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        hideKeyboard()
        return true
    }

    override fun onQueryTextChange(query: String): Boolean {
        if (interactor.getFragmentSearch().isAdded)
            interactor.getFragmentSearch().setSearch(query)
        return true
    }

    override fun setSelectedNavigationMovie(itemId: Int) {
        interactor.setFragmentMovies()
        if (interactor.getFragmentMovies().isAdded) {
            interactor.getFragmentMovies().setItemSelectedNavigation(itemId)
            if (itemId == R.id.nav_popular_movies) interactor.getFragmentMovies().onSelectedCategoryPopular()
        }
    }

    override fun setSelectedNavigationTv(itemId: Int) {
        interactor.setFragmentSeries()
        if (interactor.getFragmentTvShow().isAdded){
            interactor.getFragmentTvShow().setItemSelectedNavigation(itemId)
            if (itemId == R.id.nav_popular_tv) interactor.getFragmentTvShow().onSelectedCategoryPopular()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (data!=null){
                val id = data.getIntExtra(ID,0)
                val title = data.getStringExtra(TITLE)
                val type = data.getStringExtra(TYPE)
                val image = data.getStringExtra(IMAGE)
                setMaximizeDraggable(id,title,type,image)
            }
        }
    }

}
