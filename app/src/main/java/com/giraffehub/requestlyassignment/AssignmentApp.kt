package com.giraffehub.requestlyassignment

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.requestly.android.core.Requestly

@HiltAndroidApp
class AssignmentApp : Application() {
    override fun onCreate() {
        super.onCreate()

        Requestly.Builder(this, "aRIF941eGBzxE66OCKut")
            .build()
    }
}