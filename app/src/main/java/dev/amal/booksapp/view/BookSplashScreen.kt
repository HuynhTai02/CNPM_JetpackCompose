package dev.amal.booksapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import dev.amal.booksapp.R
import dev.amal.booksapp.components.BookItemList
import dev.amal.booksapp.components.TextInputField
import dev.amal.booksapp.model.BookItem
import dev.amal.booksapp.navigation.MainActions
import dev.amal.booksapp.ui.theme.text
import dev.amal.booksapp.utils.ViewState
import dev.amal.booksapp.viewmodel.MainViewModel
import kotlinx.coroutines.delay

@ExperimentalCoilApi
@ExperimentalComposeUiApi
@Composable
fun BookSplashScreen( actions: MainActions) {
    LaunchedEffect(key1 = true){
        delay(3000L)

        actions.gotoBookOnboarding.invoke()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.book))
        val logoAnimationState =
            animateLottieCompositionAsState(composition = composition)
        LottieAnimation(
            composition = composition,
            progress = { logoAnimationState.progress }
        )
    }
}