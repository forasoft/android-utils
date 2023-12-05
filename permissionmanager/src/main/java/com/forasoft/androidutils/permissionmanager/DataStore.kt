package com.forasoft.androidutils.permissionmanager

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

private const val PREFERENCES_DATA_STORE_NAME = "androidutils.permissionmanager.preferences"

internal val Context.preferencesDataStore by preferencesDataStore(PREFERENCES_DATA_STORE_NAME)
