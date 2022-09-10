package com.euzhene.webviewdemo

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private const val URL_PREFERENCE_KEY = "url"
private const val DATASTORE_NAME = "settings"
val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_NAME)

suspend fun getUrlFromDataStore(context: Context): String? {
    return context.datastore.data.map {
        it[stringPreferencesKey(URL_PREFERENCE_KEY)]
    }.first()
}

suspend fun saveUrl(context: Context, url: String) {
    context.datastore.edit {
        it[stringPreferencesKey(URL_PREFERENCE_KEY)] = url
    }
}