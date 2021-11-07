package com.udacity.asteroidradar

import android.app.Application
import android.os.Build
import androidx.work.*
import com.udacity.asteroidradar.worker.RefreshDataWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import timber.log.Timber
import timber.log.Timber.DebugTree


class AsteroidRadarApplication: Application() {

    val applicationScope = CoroutineScope(Dispatchers.Default) //coroutine scope variable

    fun OnCreate(){
        super.onCreate()
        delayedInit()

        //Enable Timber logging
        Timber.plant(DebugTree())


    }

    private fun delayedInit() = applicationScope.launch {
        setupRecurringWork()
    }
    private fun setupRecurringWork() {

        //Conditions for when to run the worker job.
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(true)
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setRequiresDeviceIdle(true)
                }
            }.build()

        //Schedule daily
        val repeatingRequest = PeriodicWorkRequestBuilder<RefreshDataWorker>(1, TimeUnit.DAYS)
            .build()

        WorkManager.getInstance().enqueueUniquePeriodicWork(
            RefreshDataWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest)
    }

}