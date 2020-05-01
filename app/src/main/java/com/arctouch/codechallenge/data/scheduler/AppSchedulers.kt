package com.arctouch.codechallenge.data.scheduler

import io.reactivex.Scheduler

interface AppSchedulers {
    fun network() : Scheduler
    fun database() : Scheduler
    fun main() : Scheduler
}