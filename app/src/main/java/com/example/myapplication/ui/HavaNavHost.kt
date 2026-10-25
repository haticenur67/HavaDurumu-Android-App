package com.example.myapplication.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.ui.city.CityScreen
import com.example.myapplication.ui.forecast.ForecastScreen

@Composable
fun HavaNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "city") {
        composable("city") {
            CityScreen(
                onCityClick = { city ->
                    navController.navigate("forecast/${city.latitude}/${city.longitude}/${city.name}")
                }
            )
        }
        composable(
            route = "forecast/{lat}/{lon}/{name}",
            arguments = listOf(
                navArgument("lat") { type = NavType.FloatType },
                navArgument("lon") { type = NavType.FloatType },
                navArgument("name") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val lat = backStackEntry.arguments?.getFloat("lat")?.toDouble() ?: 0.0
            val lon = backStackEntry.arguments?.getFloat("lon")?.toDouble() ?: 0.0
            val name = backStackEntry.arguments?.getString("name") ?: ""

            ForecastScreen(lat = lat, lon = lon, cityName = name,onBackClick = { navController.popBackStack() })
        }
    }
}