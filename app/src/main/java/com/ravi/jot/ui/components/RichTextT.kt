package com.ravi.jot.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun RichTextT(
//    state: com.mohamedrejeb.richeditor.model.RichTextState,
    getImage: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF9F8FE))
            .imePadding()
    ) {
        IconButton(
            onClick = { getImage() }
        ) {
            Icon(
                imageVector = Icons.Default.Photo,
                contentDescription = ""
            )
        }
//        IconButton(
//            onClick = { state.toggleSpanStyle(SpanStyle(fontWeight = FontWeight.Bold)) },
//            colors = if (state.currentSpanStyle.fontWeight == FontWeight.Bold)
//                IconButtonDefaults.iconButtonColors(
//                    containerColor = B,
//                    contentColor = White
//                ) else IconButtonDefaults.iconButtonColors()
//        ) {
//            Icon(
//                imageVector = Icons.Default.FormatBold,
//                contentDescription = ""
//            )
//        }
    }
}
