package dev.amal.booksapp.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import dev.amal.booksapp.R
import dev.amal.booksapp.components.ProfileCard
import dev.amal.booksapp.components.TopBarProfile
import dev.amal.booksapp.navigation.MainActions
import dev.amal.booksapp.utils.ProfileViewState
import dev.amal.booksapp.viewmodel.MainViewModel

@Composable
fun BookProfileScreen(
    viewModel: MainViewModel,
    actions: MainActions
) {
    Scaffold(topBar = {
        TopBarProfile(title = stringResource(id = R.string.text_bookProfile), action = actions)
    }) {
        Profile(viewModel = viewModel)
    }
}

@SuppressLint("StateFlowValueCalledInComposition", "UnrememberedMutableState")
@Composable
fun Profile(viewModel: MainViewModel) {
    when (val result = viewModel.profile.value) {
        ProfileViewState.Loading -> Text(text = "Loading")
        is ProfileViewState.Error -> Text(text = "Error found: ${result.exception}")
        is ProfileViewState.Success -> {
            val profile = result.data

            Column(modifier = Modifier.fillMaxSize()) {
                ProfileCard(
                    profile.first().fullName,
                    profile.first().nicName,
                    profile.first().email,
                    profile.first().photo
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.background)
                ) {
                    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.profile))
                    val logoAnimationState =
                        animateLottieCompositionAsState(
                            composition = composition,
                            iterations = LottieConstants.IterateForever
                        )
                    LottieAnimation(
                        composition = composition,
                        progress = { logoAnimationState.progress },
                    )
                }
            }

        }

        ProfileViewState.Empty -> Text("No results found!")
    }
}