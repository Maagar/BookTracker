package com.example.booktracker.presentation.screen.Discover

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booktracker.data.model.Series
import com.example.booktracker.data.model.UserProfile
import com.example.booktracker.data.repository.SeriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.realtime.HasOldRecord
import io.github.jan.supabase.realtime.HasRecord
import io.github.jan.supabase.realtime.PostgresAction
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.realtime.RealtimeChannel
import io.github.jan.supabase.realtime.channel
import io.github.jan.supabase.realtime.postgresChangeFlow
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val seriesRepository: SeriesRepository,
    private val auth: Auth,
    private val realtime: Realtime
) : ViewModel() {

    private val _series = MutableStateFlow<List<Series>>(emptyList())
    val series: StateFlow<List<Series>> = _series

    private val _recommendations = MutableStateFlow<List<Series>>(emptyList())
    val recommendations: StateFlow<List<Series>> = _recommendations

    private var currentPage = 0
    private val pageSize = 20
    private var isLoading = false
    private var isLastPage = false

    private var isSearching = false

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    init {
        fetchSeries()
        getRecommendations()
        observeChanges()
    }


    fun observeChanges() {
        val channel = realtime.channel("profiles:recommendations")

        channel.postgresChangeFlow<PostgresAction>(schema = "public").onEach {
            when(it) {
                is HasRecord -> getRecommendations()
                is HasOldRecord -> Log.d("hasOldRecord", "${it.oldRecord}")
                else -> null
            }
        }
            .launchIn(viewModelScope)

        viewModelScope.launch {
            channel.subscribe()
        }
    }




    fun getRecommendedSeries() {
        _recommendations.value = emptyList()
        viewModelScope.launch {
            val client = HttpClient(CIO) {
                install(ContentNegotiation) {
                    json(Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    })
                }
                install(HttpTimeout) {
                    requestTimeoutMillis = 60000
                    connectTimeoutMillis = 5000
                    socketTimeoutMillis = 5000

                }
            }
            runCatching {
                val payload = listOf(auth.currentUserOrNull()
                    ?.let { UserProfile(user_profile_id = it.id) })

                client.post("https://magar.app.n8n.cloud/webhook/9749aeac-6954-494e-b6fc-cd5a84bcb207") {
                    contentType(ContentType.Application.Json)
                    setBody(payload)
                }
            }.onSuccess {
                client.close()
            }.onFailure {
                Log.e("DiscoverViewModel", "Error fetching recommendations", it)
                client.close()
            }
        }
    }

    fun setIsRefreshing() {
        _series.value = emptyList()
        isLastPage = false
        currentPage = 0
        _isRefreshing.value = true
    }

    fun onQueryChange(query: String) {
        _query.value = query
        debounceSearch()
    }

    fun resetQuery() {
        _query.value = ""
    }

    fun getRecommendations() {
        viewModelScope.launch {
            runCatching {
                seriesRepository.getRecommendedSeries()
            }.onFailure {
                Log.e("DiscoverViewModel", "Error fetching recommendations", it)
            }.onSuccess {
                _recommendations.value = it
            }
        }
    }

    fun fetchSeries() {
        if (isLoading || isLastPage) return

        viewModelScope.launch {
            isLoading = true
            try {
                val newSeries = seriesRepository.getSeries(
                    page = currentPage,
                    pageSize = pageSize,
                    searchQuery = _query.value
                )
                if (newSeries.size < pageSize) {
                    isLastPage = true
                }
                if (isSearching) {
                    _series.value = newSeries
                    isSearching = false
                } else {
                    _series.value += newSeries
                }
                currentPage++
            } catch (e: Exception) {
                Log.e("DiscoverViewModel", "Error fetching series", e)
            } finally {
                isLoading = false
                _isRefreshing.value = false
            }
        }
    }

    fun searchSeries() {
        currentPage = 0
        isLastPage = false
        isSearching = true
        fetchSeries()
    }

    fun debounceSearch() {
        viewModelScope.launch {
            val latestQuery = query.value
            delay(500)
            if (latestQuery == query.value) {
                searchSeries()
            }
        }
        _isRefreshing.value = false
    }

    fun refreshRecommendations(seriesId: Int) {
        val updatedRecommendations = _recommendations.value.mapIndexed { _, series ->
            if (series.id == seriesId) {
                series.copy(is_following = !series.is_following)
            } else {
                series
            }
        }
    }

    fun refreshSeries(seriesId: Int) {
        val updatedSeriesList = _series.value.mapIndexed { index, series ->
            if (series.id == seriesId)
                series.copy(is_following = !series.is_following)
            else series
        }
        _series.value = updatedSeriesList
    }


}