package earsline.teea

import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

class WinWebViewClient:WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        view?.loadUrl(request?.url.toString())
        return false
    }
}