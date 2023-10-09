package com.znh.gradle80.plugin.demo

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
import androidx.compose.ui.tooling.preview.Preview
import com.znh.aop.annotation.HuiRouterPath
import com.znh.aop.api.HuiRouterApi
import com.znh.gradle80.plugin.common.HuiConstants
import com.znh.gradle80.plugin.common.theme.Gradle80plugindemoTheme

@HuiRouterPath(HuiConstants.ROUTER_PATH_SECOND)
class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Gradle80plugindemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("SecondActivity")
                }
            }
        }
    }

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Hello $name!",
            modifier = modifier.clickable {
                HuiRouterApi.routerPath(this, HuiConstants.ROUTER_PATH_MODULE1)
            }
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        Gradle80plugindemoTheme {
            Greeting("Android")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}

