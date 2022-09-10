package earsline.teea

import android.view.ViewGroup
import android.webkit.WebView
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.euzhene.webviewdemo.getUrlFromDataStore
import kotlinx.coroutines.launch

@Composable
fun WinWebView() {
    var webView: WebView? = null
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    AndroidView(factory = {
        webView = WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            settings.javaScriptEnabled = true
        }
        webView!!
    }, update = { webView = it })
    LaunchedEffect(key1 = webView) {
        if (webView == null) return@LaunchedEffect

        coroutineScope.launch {
            webView!!.loadUrl(getUrlFromDataStore(context)!!)
        }
    }

    BackHandler {}
}


