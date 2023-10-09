package com.znh.gradle80.plugin.module1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.znh.aop.annotation.HuiRouterPath
import com.znh.aop.api.HuiRouterApi
import com.znh.gradle80.plugin.common.HuiConstants
import com.znh.gradle80.plugin.common.theme.Gradle80plugindemoTheme

@HuiRouterPath(HuiConstants.ROUTER_PATH_MODULE1)
class Module1Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Gradle80plugindemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Module1Activity")
                }
            }
        }
    }

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Hello $name!",
            modifier = modifier.clickable {
                HuiRouterApi.routerPath(this, HuiConstants.ROUTER_PATH_DEMO)
            }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}

