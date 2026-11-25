package com.youarelaunched.challenge.network.api

import android.content.Context
import com.google.gson.Gson
import com.youarelaunched.challenge.network.models.NetworkVendor
import com.youarelaunched.challenge.network.models.NetworkVendorsData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class NetworkDataProvider(
    private val workDispatcher: CoroutineDispatcher,
    private val appContext: Context
) : ApiVendors {

    override suspend fun getVendors(): Result<List<NetworkVendor>> = withContext(workDispatcher) {
        return@withContext try {
            val json = appContext.assets
                .open("vendors.json")
                .bufferedReader()
                .use {
                    it.readText()
                }

            val result = Gson()
                .fromJson(json, NetworkVendorsData::class.java)
                .vendors

            Result.success(result)
        } catch (e: Exception) {
            Result.failure(Exception(e.message ?: "Unknown Error"))
        }
    }
}