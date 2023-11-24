package dev.amal.booksapp.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import dev.amal.booksapp.components.BookItemList
import dev.amal.booksapp.components.TextInputField
import dev.amal.booksapp.model.BookItem
import dev.amal.booksapp.navigation.MainActions
import dev.amal.booksapp.utils.ViewState
import dev.amal.booksapp.viewmodel.MainViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@ExperimentalCoilApi
@ExperimentalComposeUiApi
@Composable
fun BookListScreen(viewModel: MainViewModel, actions: MainActions) {

    when (val result = viewModel.books.value) {
        is ViewState.Loading -> {
            Text(text = "Loading")
        }
        is ViewState.Error -> Text(text = "Error found: ${result.exception}")
        is ViewState.Success -> {
            BookList(result.data, actions)
        }
        ViewState.Empty -> Text("No results found!")
    }
}

@ExperimentalComposeUiApi
@Composable
fun TopBar(
    actions: MainActions
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Explore thousands of \nbooks in app",
                style = MaterialTheme.typography.h5,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colors.primaryVariant,
                maxLines = 2,
                modifier = Modifier.padding(start = 30.dp, end = 30.dp, bottom = 24.dp)
            )
        }
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "Profile",
            tint = MaterialTheme.colors.primaryVariant,
            modifier = Modifier
                .size(50.dp)
                .clickable(onClick = actions.gotoProfile)
                .padding(end = 20.dp)
        )
    }
}

@ExperimentalCoilApi
@ExperimentalComposeUiApi
@Composable
fun BookList(bookList: List<BookItem>, actions: MainActions) {

    val search = remember { mutableStateOf("") }

    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(top = 24.dp, bottom = 24.dp),
        modifier = Modifier.background(MaterialTheme.colors.background)
    ) {
        item {
            TopBar(actions)
        }
        item {
            TextInputField(
                value = search.value,
                onValueChanged = { search.value = it }
            )
        }
        item {
            Text(
                text = "Popular books",
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.primaryVariant,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(start = 25.dp, top = 25.dp)
            )
        }
        items(bookList.filter { it.title.contains(search.value, ignoreCase = true) }) { book ->
            BookItemList(
                book.title,
                book.authors.toString(),
                book.thumbnailUrl,
                book.categories,
                onItemClick = { actions.gotoBookDetails.invoke(book.isbn) })
        }
    }
}