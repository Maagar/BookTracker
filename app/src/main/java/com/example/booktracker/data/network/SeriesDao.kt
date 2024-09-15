package com.example.booktracker.data.network

import android.util.Log
import com.example.booktracker.data.model.FollowedSeries
import com.example.booktracker.data.model.Series
import com.example.booktracker.data.model.SeriesInfo
import com.example.booktracker.data.model.UserSeriesIds
import com.example.booktracker.data.model.Volume
import com.example.booktracker.data.model.VolumeToInsert
import com.example.booktracker.data.model.VolumeToUpdate
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.rpc
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
            supabaseClient.postgrest.rpc(
                "get_followed_series", mapOf(
                    "p_offset" to offset, "p_limit" to limit
                )
            ).decodeList()
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
        response
    }


    suspend fun getAllUserVolumes(seriesId: Int): List<Volume> = withContext(Dispatchers.IO) {
        val response = supabaseClient.postgrest.rpc("get_user_volumes_by_series", mapOf("p_series_id" to seriesId)).decodeList<Volume>()
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

    suspend fun insertUserVolume(volumeToInsert: VolumeToInsert): Boolean =
        withContext(Dispatchers.IO) {
            try {
                supabaseClient.from("user_volumes").insert(volumeToInsert)
                true
            } catch (e: Exception) {
                e.message?.let { Log.e("InsertError", it) }
                false
            }
        }

    suspend fun updateUserVolume(volumeToUpdate: VolumeToUpdate): Boolean =
        withContext(Dispatchers.IO) {
            try {
                supabaseClient.from("user_volumes").update(volumeToUpdate)
                true
            } catch (e: Exception) {
                e.message?.let { Log.e("InsertError", it) }
                false
            }
        }

    suspend fun deleteUserVolume(userVolumeId: Int): Boolean =
        withContext(Dispatchers.IO) {
            try {
                supabaseClient.from("user_volumes").delete() {
                    filter { eq("id", userVolumeId) }
                }

                true
            } catch (e: Exception) {
                e.message?.let { Log.e("InsertError", it) }
                false
            }
        }

}