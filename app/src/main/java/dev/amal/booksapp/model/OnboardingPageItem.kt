package dev.amal.booksapp.model

import androidx.annotation.DrawableRes
import dev.amal.booksapp.R

sealed class OnBoardingPageItem(
    val title: String,
    val description: String,
    @DrawableRes
    val image: Int
) {
    object First : OnBoardingPageItem(
        title = "Only Books Can Help You",
        description = "Books are an intellectual friend that can help you improve your skills and knowledge in humanity and accompany you on every path to success.",
        image = R.drawable.img_1
    )

    object Second : OnBoardingPageItem(
        title = "Learn Smartly",
        description = "It’s 2023 and it’s time to learn every quickly and smartly. All books are storage in cloud and you can access all of them from your phone or laptop or PC. ",
        image = R.drawable.img_2
    )

    object Third : OnBoardingPageItem(
        title = "Book Has Power To Chnage\n" +
                "Everything",
        description = "We have true friend in our life and the books is that. Book has power to chnage yourself and make you more valueable.",
        image = R.drawable.img_3
    )
}