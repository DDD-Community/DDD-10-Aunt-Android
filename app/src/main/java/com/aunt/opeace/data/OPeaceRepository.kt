package com.aunt.opeace.data

import com.aunt.opeace.api.ApiService
import javax.inject.Inject
import javax.inject.Singleton

interface OPeaceRepository {

}

@Singleton
class OPeaceRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : OPeaceRepository {

}