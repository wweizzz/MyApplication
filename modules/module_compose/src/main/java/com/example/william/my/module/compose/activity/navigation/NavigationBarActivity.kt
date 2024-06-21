package com.example.william.my.module.compose.activity.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.path.RouterPath

@Route(path = RouterPath.Compose.NavigationBar)
class NavigationBarActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyMain()
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun MyMain() {
        val navController = rememberNavController()
        Scaffold(
            bottomBar = { MyNavigationBar(navController) }
        ) { innerPadding ->
            println(innerPadding)
            MyNavHost(navController)
        }
    }

    @Composable
    fun MyNavHost(navController: NavHostController) {
        NavHost(navController = navController, startDestination = "home") {
            composable(Screen.Home.route) { HomeFragment() }
            composable(Screen.Profile.route) { ProfileFragment() }
        }
    }

    @Composable
    fun MyNavigationBar(navController: NavHostController) {

        val items = listOf(
            Screen.Home, Screen.Profile
        )

        val navBackStackEntry by navController.currentBackStackEntryAsState()

        NavigationBar {
            items.forEach { screen ->
                NavigationBarItem(
                    selected = navBackStackEntry?.destination?.hierarchy?.any { it.route == screen.route } == true,
                    onClick = {
                        navController.navigate(screen.route) {
                            // 弹出到导航图开始目的地，以避免在返回栈中积累大量目的地
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // 当重新选择相同的项时，避免创建同一目的地的多个副本
                            launchSingleTop = true
                            // 当重新选择之前选择过的项时，恢复状态
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = screen.icon,
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(
                            text = (screen.resourceId)
                        )
                    },
                )
            }
        }
    }

    @Composable
    fun HomeFragment() {
        // 内容定义
        Text(text = "ProfileFragment")
    }

    @Composable
    fun ProfileFragment() {
        // 内容定义
        Text(text = "ProfileFragment")
    }
}