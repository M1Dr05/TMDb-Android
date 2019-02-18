package com.github.midros.tmdb.utils.schedulers

import io.reactivex.Scheduler

/**
 * Created by luis rafael on 16/02/19.
 */
interface BaseSchedulerProvider {

    fun io(): Scheduler

    fun ui(): Scheduler
}