package com.example.booktracker.data.network

import android.util.Log
import com.example.booktracker.data.model.FollowedSeries
import com.example.booktracker.data.model.Series
import com.example.booktracker.data.model.SeriesInfo
import com.example.booktracker.data.model.UpcomingVolume
import com.example.booktracker.data.model.Volume
import com.example.booktracker.data.model.VolumeResponse
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

    suspend fun getSeriesPaginated(offset: Int, limit: Int, searchQuery: String): List<Series> =
        withContext(Dispatchers.IO) {
            supabaseClient.postgrest.rpc(
                "get_series_paginated", seriesParams(offset, limit, searchQuery)
            ).decodeList<Series>()
        }

    suspend fun getSeries(seriesId: Int): Series =
        withContext(Dispatchers.IO) {
            supabaseClient.postgrest.rpc(
                "get_series_by_id", mapOf("p_series_id" to seriesId)
            ).decodeSingle<Series>()
        }

    suspend fun getFollowedSeries(
        offset: Int,
        limit: Int,
        sortByDate: Boolean,
        showFinished: Boolean
    ): List<FollowedSeries> =
        withContext(Dispatchers.IO) {
            supabaseClient.postgrest.rpc(
                "get_followed_series",
                getFollowedSeriesParams(offset, limit, sortByDate, showFinished)
            ).decodeList<FollowedSeries>()
        }

    suspend fun getUpcomingVolumes(offset: Int, limit: Int): List<UpcomingVolume> =
        withContext(Dispatchers.IO) {
            supabaseClient.postgrest.rpc(
                "get_upcoming_volumes", mapOf(
                    "p_offset" to offset, "p_limit" to limit
                )
            ).decodeList<UpcomingVolume>()
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
        val response = supabaseClient.postgrest.rpc(
            "get_user_volumes_by_series",
            mapOf("p_series_id" to seriesId)
        ).decodeList<Volume>()
        response
    }

    suspend fun getVolumeById(volumeId: Int): Volume = withContext(Dispatchers.IO) {
        supabaseClient.postgrest.rpc(
            "get_volume_by_id", mapOf("p_volume_id" to volumeId)
        ).decodeSingle<Volume>()
    }

    suspend fun insertUserSeries(seriesId: Int): Boolean = withContext(Dispatchers.IO) {
        runCatching {
            val data = mapOf("series_id" to seriesId)
            supabaseClient.from("user_series").upsert(data) {
                onConflict = "profile_id, series_id"
            }
            true
        }.getOrElse { e ->
            Log.e("insertError", "Error inserting user series", e)
            false
        }
    }

    suspend fun deleteUserSeries(seriesId: Int): Boolean = withContext(Dispatchers.IO) {
        runCatching {
            supabaseClient.from("user_series").delete {
                filter { eq("series_id", seriesId) }
            }
            true
        }.getOrElse { e ->
            Log.e("DeleteError", "Error deleting user series", e)
            false
        }
    }

    suspend fun insertUserVolume(volumeToInsert: VolumeToInsert): Int? =
        withContext(Dispatchers.IO) {
            runCatching {
                val result = supabaseClient.from("user_volumes")
                    .upsert(volumeToInsert) {
                        onConflict = "profile_id, volume_id"
                        select(columns = Columns.list("id"))
                    }.decodeList<VolumeResponse>()
                result.firstOrNull()?.id
            }.onFailure { e ->
                e.message?.let { Log.e("InsertError", it) }
            }.getOrNull()
        }

    suspend fun updateUserVolume(volumeToUpdate: VolumeToUpdate): Boolean =
        withContext(Dispatchers.IO) {
            runCatching {
                supabaseClient.from("user_volumes").update(volumeToUpdate) {
                    filter { eq("id", volumeToUpdate.id) }
                }
                true
            }.onFailure { e ->
                e.message?.let { Log.e("InsertError", it) }
            }.getOrDefault(false)
        }

    suspend fun deleteUserVolume(userVolumeId: Int): Boolean =
        withContext(Dispatchers.IO) {
            runCatching {
                supabaseClient.from("user_volumes").delete() {
                    filter { eq("id", userVolumeId) }
                }
                true
            }.onFailure { e ->
                e.message?.let { Log.e("InsertError", it) }
            }.getOrDefault(false)
        }
}