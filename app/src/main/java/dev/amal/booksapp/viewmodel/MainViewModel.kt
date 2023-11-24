package dev.amal.booksapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.amal.booksapp.model.BookItem
import dev.amal.booksapp.model.Profile
import dev.amal.booksapp.utils.DetailViewState
import dev.amal.booksapp.utils.ProfileViewState
import dev.amal.booksapp.utils.ViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class MainViewModel : ViewModel() {

    private val _viewState = MutableStateFlow<ViewState>(ViewState.Loading)
    private val _detailViewState = MutableStateFlow<DetailViewState>(DetailViewState.Loading)
    private val _profileViewState = MutableStateFlow<ProfileViewState>(ProfileViewState.Loading)

    val books = _viewState.asStateFlow()
    val bookDetails = _detailViewState.asStateFlow()
    val profile = _profileViewState.asStateFlow()


    private val format = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
        isLenient = true
    }

    fun getAllBooks(context: Context) = viewModelScope.launch {
        try {
            val myJson = context.assets.open("books.json").bufferedReader().use {
                it.readText()
            }

            val bookList = format.decodeFromString<List<BookItem>>(myJson)
            _viewState.value = ViewState.Success(bookList)

        } catch (e: Exception) {
            _viewState.value = ViewState.Error(e)
        }
    }

    fun getBookByID(context: Context, isbnNO: String) = viewModelScope.launch {
        try {
            val myJson = context.assets.open("books.json").bufferedReader().use {
                it.readText()
            }

            val bookList = format.decodeFromString<List<BookItem>>(myJson)
                .first { it.isbn.contentEquals(isbnNO) }
            _detailViewState.value = DetailViewState.Success(bookList)

        } catch (e: Exception) {
            _detailViewState.value = DetailViewState.Error(e)
        }
    }

    fun getProfile(context: Context) = viewModelScope.launch {
        try {
            val myJson = context.assets.open("profile.json").bufferedReader().use {
                it.readText()
            }
            val profile = format.decodeFromString<List<Profile>>(myJson)
            _profileViewState.value = ProfileViewState.Success(profile)
        } catch (e: Exception) {
            _profileViewState.value = ProfileViewState.Error(e)
        }
    }
}