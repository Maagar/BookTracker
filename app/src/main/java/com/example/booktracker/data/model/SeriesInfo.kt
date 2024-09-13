package com.example.booktracker.data.model

import kotlinx.serialization.Serializable

@Serializable
data class SeriesInfo(
    private val series_authors: List<SeriesAuthors> = emptyList(),
    private val series_publishers: List<SeriesPublishers> = emptyList(),
    private val series_tags: List<SeriesTags> = emptyList()
) {
    val authorList: List<String> get() = series_authors.map { it.authors.full_name }
    val publisherList: List<String> get() = series_publishers.map { it.publishers.name }
    val tagList: List<String> get() = series_tags.map { it.tags.name }
}

@Serializable
data class SeriesAuthors(
    val id: Int,
    val authors: Author
)

@Serializable
data class Author(
    val full_name: String
)

@Serializable
data class SeriesPublishers(
    val id: Int,
    val publishers: Publishers
)

@Serializable
data class Publishers(
    val name: String
)

@Serializable
data class SeriesTags(
    val id: Int,
    val tags: Tags
)

@Serializable
data class Tags(
    val name: String
)