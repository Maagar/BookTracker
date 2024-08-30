package com.example.booktracker.data.network

import com.example.booktracker.data.model.Series
import com.example.booktracker.data.model.Volume
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SeriesDao @Inject constructor(private val supabaseClient: SupabaseClient) {

    suspend fun getSeriesPaginated(offset: Int, limit: Int, searchQuery: String?): List<Series> =
        withContext(Dispatchers.IO) {
            val response =
                supabaseClient.from("series").select {
                    if (!searchQuery.isNullOrEmpty()) filter { ilike("title", "%$searchQuery%") }
                    range(offset.toLong()..<offset + limit)
                }
                    .decodeList<Series>()
            response
        }


    suspend fun getAllUserVolumes(seriesId: Int): List<Volume> = withContext(Dispatchers.IO) {
        val columns = Columns.raw(
            """
            id,
            title,
            cover_url,
            volume_number,
            user_volumes (
                times_read
            )
        """.trimIndent()
        )
        val response = supabaseClient.from("volumes")
            .select(columns = columns) {
                order(column = "volume_number", order = Order.ASCENDING)
                filter { eq("series_id", seriesId) }
            }
            .decodeList<Volume>()
        response
    }
}