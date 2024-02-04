package com.aunt.opeace.di

import android.content.Context
import com.aunt.opeace.preference.OPeacePreference
import com.aunt.opeace.preference.OPeacePreferenceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface PreferenceModule {
    @Binds
    fun bindsOPeacePreference(impl: OPeacePreferenceImpl): OPeacePreference
}