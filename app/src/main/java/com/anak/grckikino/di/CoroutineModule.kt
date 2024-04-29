package com.anak.grckikino.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

internal object CoroutinesModule {
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
}