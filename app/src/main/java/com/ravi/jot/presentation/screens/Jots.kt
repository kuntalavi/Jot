package com.ravi.jot.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ravi.jot.presentation.vm.JotVM

@Composable
fun Jots(
    vm: JotVM,
    goToJot: (jotEntryId: Int) -> Unit
) {

    val entries by vm.entries.collectAsState(
        emptyList()
    )

    LazyColumn(
        modifier = Modifier.padding(horizontal = 10.dp)
    ) {
        item {
            Text(
                text = "E N T R I E S",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )
        }
        items(entries) { entry ->
            Text(
                text = "${entry.date} ${entry.month.uppercase()} ${entry.e.substring(2, 4)}",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable(
                        onClick = { goToJot(entry.id) }
                    ),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = entry.content,
                        modifier = Modifier
                            .padding(16.dp)
                            .weight(1f)
                    )
                }
            }
        }
    }
}