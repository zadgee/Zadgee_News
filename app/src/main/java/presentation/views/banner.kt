package presentation.views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView


@Composable
fun AdBanner(modifier:Modifier = Modifier,id:String){
     AndroidView(factory = {
         context->
         AdView(context).apply {
             setAdSize(AdSize.BANNER)
             adUnitId = id
             val adRequest = AdRequest.Builder().build()
             loadAd(adRequest)
         }
     })
    modifier.fillMaxWidth()
}