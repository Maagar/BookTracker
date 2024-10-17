package com.example.booktracker.data.network

import android.util.Log
import com.example.booktracker.data.model.ProfileData
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProfileDao @Inject constructor(private val supabaseClient: SupabaseClient) {
    suspend fun getProfileData(): ProfileData =
        withContext(Dispatchers.IO) {
            val profileId = supabaseClient.auth.currentSessionOrNull()?.user?.id
            val result = supabaseClient.from("profiles")
                .select(columns = Columns.list("id, username, avatar_url"))
                {filter {
                    if (profileId != null) {
                        eq("id", profileId)
                    }
                }}
                .decodeSingle<ProfileData>()
            Log.d("test", "$result")
            result
        }
}