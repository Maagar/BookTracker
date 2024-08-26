package com.example.booktracker.data.network

import android.util.Log
import com.example.booktracker.data.model.Series
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SeriesDao @Inject constructor(private val supabaseClient: SupabaseClient) {

    suspend fun getAllSeries(): List<Series> = withContext(Dispatchers.IO) {
        val response = supabaseClient.from("series").select().decodeList<Series>()
        Log.d("SeriesDao", "Raw response: $response")
        response
    }
}