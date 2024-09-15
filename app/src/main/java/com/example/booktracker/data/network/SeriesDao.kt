package com.example.booktracker.data.network

import android.util.Log
import com.example.booktracker.data.model.FollowedSeries
import com.example.booktracker.data.model.Series
import com.example.booktracker.data.model.SeriesInfo
import com.example.booktracker.data.model.UserSeriesIds
import com.example.booktracker.data.model.Volume
import com.example.booktracker.data.model.VolumeToUpsert
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

    suspend fun getFollowedSeries(offset: Int, limit: Int): List<FollowedSeries> =
        withContext(Dispatchers.IO) {
            val columns = Columns.raw(
                """
                    id,
                    volumes_read_count,
                    series(
                        id,
                        created_at,
                        title,
                        main_cover_url,
                        is_single_volume,
                        release_date,
                        synopsis,
                        total_volumes_released
                    )
                """.trimIndent()
            )
            val response =
                supabaseClient.from("user_series").select(columns = columns) {
                    range(offset.toLong()..<offset + limit)
                }.decodeList<FollowedSeries>()

            response
        }

    suspend fun getSeriesInfo(seriesId: Int): SeriesInfo = withContext(Dispatchers.IO) {
        val columns = Columns.raw(
            """
                id,
                series_authors(
                    id,
                    authors(
                        full_name
                    )
                ),
                series_publishers(
                    id,
                    publishers(
                        name
                    )
                ),
                series_tags(
                    id,
                    tags(
                        name
                    )
                )
            """.trimIndent()
        )
        val response = supabaseClient.from("series")
            .select(columns) {
                filter { eq("id", seriesId) }
            }.decodeSingle<SeriesInfo>()
        Log.d("test", "Response: $response")
        response
    }


    suspend fun getAllUserVolumes(seriesId: Int): List<Volume> = withContext(Dispatchers.IO) {
        val columns = Columns.raw(
            """
            id,
            title,
            cover_url,
            volume_number,
            user_volumes(
                id,
                times_read,
                owned
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

    suspend fun deleteUserSeries(seriesId: Int): Boolean = withContext(Dispatchers.IO) {
        try {
            supabaseClient.from("user_series").delete {
                filter { eq("series_id", seriesId) }
            }
            true
        } catch (e: Exception) {
            Log.e("DeleteError", "Error deleting user series", e)
            false
        }
    }

    suspend fun upsertUserVolume(volumeToUpsert: VolumeToUpsert): Boolean = withContext(Dispatchers.IO) {
        try {
            supabaseClient.from("user_volumes").update(volumeToUpsert)
            true
        } catch (e: Exception) {
            e.message?.let { Log.e("UpsertError", it) }
            false
        }
    }

}