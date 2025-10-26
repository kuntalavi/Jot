package com.ravi.jot.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.YearMonth

@Composable
fun C(
    goToJot: (jotEntryId: Int, e: String, month: String, date: Int) -> Unit
) {
    val months = remember {
        val t = YearMonth.now()
        (-6..6).map { offset ->
            t.plusMonths(offset.toLong())
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {

        stickyHeader {
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ) {
                listOf("SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT").forEach { day ->
                    Text(
                        text = day,
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        items(months) { month ->
            MonthV(
                month = month,
                goToJot = goToJot
            )
        }
    }
}

@Composable
fun MonthV(
    month: YearMonth,
    goToJot: (jotEntryId: Int, e: String, month: String, date: Int) -> Unit
) {
    val daysInMonth = month.lengthOfMonth()
    val firstDayOfWeek = month.atDay(1).dayOfWeek.value % 7

    val weeks = (daysInMonth + firstDayOfWeek + 6) / 7

    Text(
        text = "${month.month.name} ${month.year}",
        fontSize = 16.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp)
    )

    Column {
        repeat(weeks) { week ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                repeat(7) { dayOfWeek ->
                    val dayN = week * 7 + dayOfWeek - firstDayOfWeek + 1

                    Box(
                        modifier = Modifier
                            .background(Color(0xFFE2E2E2))
                            .weight(1f)
                            .aspectRatio(1f)
                            .padding(1.dp)
                            .clickable(
                                onClick = {
                                    if (dayN >= 1 && dayN <= daysInMonth) {
                                        goToJot(
                                            -1,
                                            month.atDay(dayN).year.toString(),
                                            month.atDay(dayN).month.toString(),
                                            dayN
                                        )
                                    }
                                }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (dayN >= 1 && dayN <= daysInMonth) {
                            Text(text = dayN.toString())
                        }
                    }
                }
            }
        }
    }
}