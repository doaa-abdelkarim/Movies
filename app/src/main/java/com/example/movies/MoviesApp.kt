package com.example.movies

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import androidx.work.*
import com.example.movies.data.di.ApplicationScope
import com.example.movies.worker.RefreshDataWorker
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class MoviesApp : Application() {

    @Inject
    @ApplicationScope
    lateinit var coroutineScope: CoroutineScope

    @Inject
    @ApplicationContext
    lateinit var context: Context

    var isLargeScreen = false

    override fun onCreate() {
        super.onCreate()

        initTimber()
        checkScreenSize()
        coroutineScope.launch {
            setupRecurringWork()
        }

    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }

    private fun checkScreenSize() {
        if ((resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE
        )
            isLargeScreen = true
    }

    private suspend fun setupRecurringWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(true)
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setRequiresDeviceIdle(true)
                }
            }.build()

        val repeatingRequest =
            PeriodicWorkRequestBuilder<RefreshDataWorker>(17, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            RefreshDataWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )

        //For test
        withContext(Dispatchers.Main) {
            WorkManager.getInstance(context)
                .getWorkInfoByIdLiveData(repeatingRequest.id)
                .observeForever {
                    // val textViewWorkStatus = text_view_work_status
                    it?.apply {
                        if (state.isFinished)
                            Timber.d("Finished")
                    }
                    // text_view_work_status.append("State: ${it.state.name}\n")
                    Timber.d("State: ${it.state.name}\n")
                }
        }

    }
}