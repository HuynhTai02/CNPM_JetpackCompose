package dev.amal.booksapp.model

import kotlinx.serialization.Serializable

@Serializable
data class BookItem(
    @Transient val authors: List<String> = emptyList(),
    @Transient val categories: List<String> = emptyList(),
    @Transient val isbn: String = "",
    @Transient val longDescription: String = "",
    val thumbnailUrl: String = "",
    val title: String = ""
)