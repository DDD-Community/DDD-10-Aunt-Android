package com.aunt.opeace.di

import com.aunt.opeace.data.OPeaceRepository
import com.aunt.opeace.data.OPeaceRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindsOPeaceRepository(impl: OPeaceRepositoryImpl): OPeaceRepository
}