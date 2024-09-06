package com.example.booktracker.data.network

import android.util.Log
import com.example.booktracker.data.model.Series
import com.example.booktracker.data.model.UserSeries
import com.example.booktracker.data.model.UserSeriesIds
import com.example.booktracker.data.model.Volume
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order
import io.github.jan.supabase.postgrest.query.filter.FilterOperator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SeriesDao @Inject constructor(private val supabaseClient: SupabaseClient) {

    suspend fun getSeriesPaginated(offset: Int, limit: Int, searchQuery: String?): List<Series> =
        withContext(Dispatchers.IO) {
            val seriesList =
                supabaseClient.from("series").select {
                    if (!searchQuery.isNullOrEmpty()) filter { ilike("title", "%$searchQuery%") }
                    range(offset.toLong()..<offset + limit)
                }
                    .decodeList<Series>()

            val followedSeriesIds =
                supabaseClient.from("user_series").select(columns = Columns.list("series_id"))
                    .decodeList<UserSeriesIds>()
                    .map { it.series_id }

            seriesList.forEach { series ->
                series.isFollowing = followedSeriesIds.contains(series.id)
            }

            seriesList
        }

    suspend fun getFollowedSeries(offset: Int, limit: Int): List<UserSeries> =
        withContext(Dispatchers.IO) {
            val columns = Columns.raw(
                """
                    id,
                    title,
                    main_cover_url,
                    is_single_volume,
                    release_date,
                    synopsis
                """.trimIndent()
            )
            val followedSeriesIds =
                supabaseClient.from("user_series").select(Columns.list("series_id"))
                    .decodeList<UserSeriesIds>()
            Log.d("SeriesDao", "Followed Series IDs: ${followedSeriesIds.map { it.series_id }}")

            if (followedSeriesIds.isEmpty()) {
                return@withContext emptyList<UserSeries>()
            }

            val seriesIds = followedSeriesIds.joinToString { it.series_id.toString() }

            val response =
                supabaseClient.from("series").select(columns = columns) {
                    filter {
                        filter(column = "id", operator = FilterOperator.IN, value = "($seriesIds)")
                    }
                    range(offset.toLong()..<offset + limit)
                }.decodeList<UserSeries>()
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

    suspend fun insertUserSeries(seriesId: Int): UserSeriesIds = withContext(Dispatchers.IO) {
        supabaseClient.from("user_series")
            .insert(UserSeriesIds(seriesId)) { select(columns = Columns.list("series_id")) }
            .decodeSingle()
    }
}