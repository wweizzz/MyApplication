package com.example.william.my.module.compose.activity.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.path.RouterPath

@Route(path = RouterPath.Compose.NavHost)
class NavHostActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NavigationExample()
        }
    }

    @Composable
    fun NavigationExample() {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "home") {
            composable("home") {
                HomeScreenExample(navController)
            }
            composable("second") {
                SecondScreenExample(navController)
            }
        }
    }

    @Composable
    fun HomeScreenExample(navController: NavController) {
        Column {
            Text(text = "This is the home screen")
            Button(onClick = {
                navController.navigate("second")
            }) {
                Text("Go to second screen")
            }
        }
    }

    @Composable
    fun SecondScreenExample(navController: NavController) {
        Column {
            Text(text = "This is the second screen")
            Button(onClick = {
                navController.popBackStack()
            }) {
                Text("Go back")
            }
        }
    }
}