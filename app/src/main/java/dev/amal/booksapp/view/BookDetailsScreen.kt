package dev.amal.booksapp.view

import android.annotation.SuppressLint
import android.content.Intent
import android.speech.tts.TextToSpeech
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import dev.amal.booksapp.R
import dev.amal.booksapp.components.TopBar
import dev.amal.booksapp.navigation.MainActions
import dev.amal.booksapp.ui.theme.typography
import dev.amal.booksapp.utils.DetailViewState
import dev.amal.booksapp.viewmodel.MainViewModel
import dev.amal.booksapp.components.BookDetailsCard
import dev.amal.booksapp.model.BookItem
import java.util.Locale

@ExperimentalCoilApi
@Composable
fun BookDetailsScreen(viewModel: MainViewModel, actions: MainActions) {
    Scaffold(topBar = {
        TopBar(
            title = stringResource(id = R.string.text_bookDetails),
            action = actions, onShareClick = HandleShare()
        )
    }) {
        BookDetails(viewModel = viewModel)
    }

}

var bookData: BookItem? = null

@Composable
fun HandleShare(): () -> Unit {
    val context = LocalContext.current

    return {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Sharing Book")
        val shareMessage = "Check out this book: ${bookData?.title} of author: ${bookData?.authors}"
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)

        val chooserIntent = Intent.createChooser(shareIntent, "Share Book")
        context.startActivity(chooserIntent)
    }
}

@Composable
fun SpeakerButton(text: String) {
    var isSpeaking by remember { mutableStateOf(false) }
    var textToSpeech: TextToSpeech? = null
    val context = LocalContext.current

    IconButton(
        onClick = {
            if (isSpeaking) {
                textToSpeech?.stop()
                isSpeaking = false
            } else {
                textToSpeech = TextToSpeech(context) { status ->
                    if (status == TextToSpeech.SUCCESS) {
                        textToSpeech?.let { txtToSpeech ->
                            txtToSpeech.language = Locale.US
                            txtToSpeech.setSpeechRate(0.5f)
                            txtToSpeech.speak(text, TextToSpeech.QUEUE_ADD, null, null)
                            isSpeaking = true
                        }
                    }
                }
            }
        }
    ) {
        Icon(
            imageVector = if (isSpeaking) Icons.Default.Close else Icons.Default.PlayArrow,
            contentDescription = stringResource(R.string.text_speaker_button),
            modifier = Modifier.size(40.dp)
        )
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@ExperimentalCoilApi
@Composable
fun BookDetails(viewModel: MainViewModel) {
    when (val result = viewModel.bookDetails.value) {
        DetailViewState.Loading -> Text(text = "Loading")
        is DetailViewState.Error -> Text(text = "Error found: ${result.exception}")
        is DetailViewState.Success -> {
            val book = result.data
            bookData = book

            LazyColumn {
                // Book Details Card
                item {
                    BookDetailsCard(
                        book.title,
                        book.authors,
                        book.thumbnailUrl,
                        book.categories
                    )
                }

                // Description
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    Row {
                        Text(
                            text = stringResource(id = R.string.text_content),
                            style = typography.h6,
                            color = MaterialTheme.colors.primaryVariant,
                            modifier = Modifier.padding(start = 18.dp, end = 18.dp)
                        )

                        Spacer(modifier = Modifier.width(110.dp))

                        SpeakerButton("You are using the reading function. Now I will read books + ${book.title} of author ${book.authors}" +
                                "with the following about the book ${book.longDescription} Thank you for listening. This is the end of the reading part. ")
                    }

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = book.longDescription.replace("\\s+".toRegex(), " ").trim(),
                        style = typography.body1,
                        textAlign = TextAlign.Justify,
                        color = MaterialTheme.colors.primaryVariant.copy(0.7F),
                        modifier = Modifier.padding(start = 18.dp, end = 18.dp)
                    )
                }
            }

        }

        DetailViewState.Empty -> Text("No results found!")
    }
}
