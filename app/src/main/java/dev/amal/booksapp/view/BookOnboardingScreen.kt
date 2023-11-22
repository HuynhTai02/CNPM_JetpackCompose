package dev.amal.booksapp.view

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import dev.amal.booksapp.model.OnBoardingPageItem
import dev.amal.booksapp.navigation.MainActions

@ExperimentalCoilApi
@ExperimentalComposeUiApi
@Composable
fun BookOnboardingScreen(
    actions: MainActions,
    onboardingPages: List<OnBoardingPageItem>
) {
    var currentPage by remember { mutableStateOf(0) }

    val totalPageCount = onboardingPages.size

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Hiển thị nội dung của trang hiện tại
        val currentPageItem = onboardingPages.getOrNull(currentPage)
        currentPageItem?.let { pageItem ->

            Column(modifier = Modifier) {
                // Hiển thị hình ảnh của trang giới thiệu
                Image(
                    painter = painterResource(id = pageItem.image),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.6f),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(24.dp))

                // Hiển thị tiêu đề, mô tả và hình ảnh của trang giới thiệu
                Text(
                    text = pageItem.title,
                    modifier = Modifier.padding(horizontal = 21.dp),
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.primaryVariant
                )

                Text(
                    text = pageItem.description,
                    modifier = Modifier.padding(horizontal = 21.dp),
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.primaryVariant.copy(0.5f)
                )

            }

            Spacer(modifier = Modifier.weight(1.0f))

            // Hiển thị chấm tròn chỉ trang hiện tại
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth(),

                ) {
                repeat(totalPageCount) { pageIndex ->
                    val isSelected = pageIndex == currentPage
                    IndicatorDot(isSelected = isSelected)
                    Spacer(modifier = Modifier.width(5.dp)) // Đổi giá trị ở đây
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Các nút điều hướng
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 21.dp)
                    .navigationBarsPadding(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp) // Khoảng cách giữa các phần tử là 4dp
            ) {
                // Nút Back (chỉ hiển thị từ trang thứ hai trở đi)
                if (currentPage > 0) {
                    Spacer(modifier = Modifier.weight(1f)) // Tạo một khoảng để giữ vị trí của nút 'Back'
                    Button(
                        onClick = { currentPage-- },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray),
                    ) {
                        Text(text = "Back", color = MaterialTheme.colors.primaryVariant)
                    }
                } else {
                    Spacer(modifier = Modifier.weight(1f)) // Tạo một khoảng để giữ vị trí của nút 'Back'
                }
                Button(
                    onClick = {
                        when {
                            currentPage == totalPageCount - 1 -> {
                                actions.gotoBookList()
                            }
                            currentPage < totalPageCount - 1 -> currentPage++
                        }
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primaryVariant),
                    modifier = Modifier.width(120.dp)
                ) {
                    Text(text = if (currentPage == totalPageCount - 1) "Get Started" else "Next")
                }
            }

            Spacer(modifier = Modifier.height(30.dp)) // Khoảng cách bên dưới màn hình
        }
    }
}

@Composable
fun IndicatorDot(isSelected: Boolean) {
    val color =
        if (isSelected) MaterialTheme.colors.primaryVariant else MaterialTheme.colors.primaryVariant.copy(
            0.3f
        )
    Box(
        modifier = Modifier
            .size(10.dp)
            .background(color = color, shape = CircleShape)
            .padding(5.dp)
    )
}