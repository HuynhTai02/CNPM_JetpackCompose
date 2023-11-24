package dev.amal.booksapp.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import dev.amal.booksapp.ui.theme.typography

@Composable
fun ProfileCard(
    fullName: String,
    nicName: String,
    email: String,
    thumbnailUrl: String
) {
    Box(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 20.dp, end = 16.dp, top = 40.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(color = Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(100.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colors.onSurface),
        )
        ProfileContentView(fullName, nicName, email, thumbnailUrl)
    }
}

@SuppressLint("SimpleDateFormat")
@Composable
fun ProfileContentView(
    fullName: String,
    nicName: String,
    email: String,
    thumbnailUrl: String
) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = rememberImagePainter(
                data = thumbnailUrl
            ),
            contentDescription = fullName,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(128.dp)
                .clip(CircleShape)
                .border(2.dp, MaterialTheme.colors.primaryVariant.copy(0.7F), CircleShape)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.onSurface)
                .padding(bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = fullName,
                style = typography.h6,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.primaryVariant
            )
            Text(
                text = nicName,
                style = typography.caption,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.primaryVariant.copy(0.7F)
            )
            Text(
                text = email,
                style = typography.caption,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.primaryVariant.copy(0.7F)
            )
        }
    }
}
