package com.znh.gradle80.plugin.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.znh.aop.annotation.HuiRouterPath
import com.znh.gradle80.plugin.common.HuiConstants
import com.znh.gradle80.plugin.common.theme.Gradle80plugindemoTheme

@HuiRouterPath(HuiConstants.ROUTER_PATH_DEMO)
class DemoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Gradle80plugindemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("DemoActivity")
                }
            }
        }
    }

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Hello $name!",
            modifier = modifier
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

