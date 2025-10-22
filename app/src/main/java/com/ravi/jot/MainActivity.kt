package com.ravi.jot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.ravi.jot.data.JotDatabase
import com.ravi.jot.presentation.screens.NewJot
import com.ravi.jot.presentation.screens.Top
import com.ravi.jot.presentation.theme.JotTheme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val jotDB = JotDatabase.getJotDB(applicationContext)

        enableEdgeToEdge()
        setContent {
            JotTheme {
                JotApp(jotDB)
            }
        }
    }
}

@Serializable
sealed interface Route {

    sealed interface Root : Route {
        @Serializable
        data object T : Root

        @Serializable
        data class NewJ(
            val jotEntryId: Int = -1,
            val e: String = "",
            val month: String = "",
            val date: Int = 0
        ) : Root
    }

    sealed interface Top : Route {
        @Serializable
        data object J : Top

        @Serializable
        data object C : Top

        @Serializable
        data object A : Top
    }

}

@Composable
fun JotApp(jotDB: JotDatabase) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.Root.T
    ) {
        composable<Route.Root.T> {
            Top(
                jotDB = jotDB,
                navController = navController
            )
        }
        composable<Route.Root.NewJ> { backStackEntry ->
            val args = backStackEntry.toRoute<Route.Root.NewJ>()
            NewJot(
                jotDB = jotDB,
                e = args.e,
                month = args.month,
                date = args.date,
                jotEntryId = args.jotEntryId,
                goBack = { navController.navigateUp() }
            )
        }
    }
}