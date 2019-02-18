package com.github.midros.tmdb.utils.schedulers

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by luis rafael on 16/02/19.
 */
object SchedulerProvider : BaseSchedulerProvider {

    override fun io(): Scheduler = Schedulers.io()

    override fun ui(): Scheduler = AndroidSchedulers.mainThread()
}