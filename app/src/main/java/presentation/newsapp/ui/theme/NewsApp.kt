package presentation.newsapp.ui.theme

import android.app.Application
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NewsApp:Application(){
    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this)
    }
}