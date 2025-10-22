package com.ravi.jot.presentation.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.FormatListBulleted
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Attachment
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ravi.jot.Route
import com.ravi.jot.data.JotDatabase
import com.ravi.jot.data.NavItem
import java.time.LocalDate

val navItemList = listOf(
    NavItem(
        route = Route.Top.J,
        title = "JOURNAL",
        i = Icons.AutoMirrored.Filled.FormatListBulleted
    ),
    NavItem(
        route = Route.Top.C,
        title = "CALENDAR",
        i = Icons.Filled.CalendarMonth
    ),
    NavItem(
        route = Route.Top.A,
        title = "ATTACHMENTS",
        i = Icons.Default.Attachment
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Top(
    jotDB: JotDatabase,
    navController: NavController
) {
    val iNavController = rememberNavController()
    val navBackStackEntry by iNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val title = when {
        currentDestination?.hasRoute<Route.Top.J>() == true -> "JOURNAL"
        currentDestination?.hasRoute<Route.Top.C>() == true -> "CALENDAR"
        currentDestination?.hasRoute<Route.Top.A>() == true -> "ATTACHMENTS"
        else -> "JOT"
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = title) }
            )
        },
        bottomBar = {
            NavigationBar {
                navItemList.forEach { it ->
                    NavigationBarItem(
                        onClick = {
                            iNavController.navigate(it.route) {
                                popUpTo(Route.Top.J) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(imageVector = it.i, contentDescription = it.title) },
                        selected = currentDestination?.hasRoute(it.route::class) == true
                    )
                }
            }
        },
        floatingActionButton = {
            if (currentDestination?.hasRoute<Route.Top.J>() == true) {
                FloatingActionButton(
                    onClick = {
                        navController.navigate(
                            Route.Root.NewJ(
                                e = LocalDate.now().year.toString(),
                                month = LocalDate.now().month.toString(),
                                date = LocalDate.now().dayOfMonth
                            )
                        )
                    }
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "")
                }
            }
        }
    ) { innerP ->
        NavHost(
            navController = iNavController,
            startDestination = Route.Top.J,
            modifier = Modifier.padding(innerP)
        ) {
            composable<Route.Top.J> {
                Jots(
                    jotDB = jotDB,
                    goToJot = { jotEntryId ->
                        navController.navigate(
                            Route.Root.NewJ(
                                jotEntryId = jotEntryId
                            )
                        )
                    }
                )
            }
            composable<Route.Top.C> {
                C(
                    jotDB = jotDB,
                    goToJot = { jotEntryId, e, month, date ->
                        navController.navigate(
                            Route.Root.NewJ(
                                jotEntryId = jotEntryId,
                                e = e,
                                month = month,
                                date = date
                            )
                        )
                    }
                )
            }
            composable<Route.Top.A> {
                Attachments(jotDB = jotDB)
            }
        }
    }
}