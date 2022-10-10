package com.forasoft.androidutils.logpecker.utils

import android.content.Context

internal val Context.fileProviderAuthority: String
    get() = "${this.packageName}.FileProvider"
