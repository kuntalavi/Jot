package com.ravi.jot.data

import androidx.compose.ui.graphics.vector.ImageVector
import com.ravi.jot.Route

data class NavItem(
    val route: Route.Top,
    val title: String,
    val i: ImageVector,
)
