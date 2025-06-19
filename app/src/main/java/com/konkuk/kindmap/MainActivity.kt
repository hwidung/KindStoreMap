package com.konkuk.kindmap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.konkuk.kindmap.magazine.MagazineScreen
import com.konkuk.kindmap.magazine.MagazineViewModel
import com.konkuk.kindmap.magazine.MagazineViewModelFactory
import com.konkuk.kindmap.main.MainScreen
import com.konkuk.kindmap.main.MainViewModel
import com.konkuk.kindmap.main.MainViewModelFactory
import com.konkuk.kindmap.rank.RankScreen
import com.konkuk.kindmap.repository.MagazineRepository
import com.konkuk.kindmap.repository.StoreRepository
import com.konkuk.kindmap.splash.SplashScreen
import com.konkuk.kindmap.ui.theme.KindMapTheme
import kotlinx.coroutines.delay
import kotlin.jvm.java

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        val storeRepository = StoreRepository()
        val mainViewModelFactory = MainViewModelFactory(storeRepository)
        val mainViewModel = ViewModelProvider(this, mainViewModelFactory).get(MainViewModel::class.java)

        val magazineRepository = MagazineRepository()
        val magazineViewModelFactory = MagazineViewModelFactory(magazineRepository)
        val magazineViewModel = ViewModelProvider(this, magazineViewModelFactory).get(MagazineViewModel::class.java)

        setContent {
            KindMapTheme {
                val navController = rememberNavController()
                val rememberedViewModel = remember { mainViewModel }
                var showSplash by remember { mutableStateOf(true) }

                if (showSplash) {
                    SplashScreen()
                    LaunchedEffect(Unit) {
                        delay(2000)
                        showSplash = false
                    }
                } else {
                    NavHost(navController = navController, startDestination = "main") {
                        composable("main") {
                            MainScreen(
                                viewModel = rememberedViewModel,
                                onRankingClick = {
                                    navController.navigate("rank")
                                },
                                onMagazineClick = {
                                    navController.navigate("magazine")
                                },
                            )
                        }

                        composable("rank") {
                            RankScreen(
                                onBackPress = {
                                    navController.navigateUp()
                                },
                            )
                        }

                        composable("magazine") {
                            MagazineScreen(
                                viewModel = magazineViewModel,
                            )
                        }
                    }
                }
            }
        }
    }
}
