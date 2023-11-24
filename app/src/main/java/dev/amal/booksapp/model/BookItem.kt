package dev.amal.booksapp.model

import kotlinx.serialization.Serializable

@Serializable
data class BookItem(
    val authors: List<String> = emptyList(),
    val categories: List<String> = emptyList(),
    val isbn: String = "",
    val longDescription: String = "",
    val thumbnailUrl: String = "",
    val title: String = ""
)