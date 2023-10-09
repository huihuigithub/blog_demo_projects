plugins {
    id("java-gradle-plugin") //会自动引入java-library、gradleApi()
    id("org.jetbrains.kotlin.jvm") //支持kotlin编写插件
    id("maven-publish") //maven发布插件
}

gradlePlugin {
    plugins {
        create("pagePlugin") {
            group = "com.znh.plugin"
            version = "1.0.0"
            id = "com.znh.plugin.page"
            implementationClass = "com.znh.plugin.page.PageAnalysisPlugin"
        }
        create("routerPlugin") {
            group = "com.znh.plugin"
            version = "1.0.1"
            id = "com.znh.plugin.router"
            implementationClass = "com.znh.plugin.router.HuiRouterPlugin"
        }
    }
}

publishing {
    repositories {
        maven {
            url = uri("../custom_plugin_repo")
        }
    }
}

dependencies {
    implementation("com.android.tools.build:gradle:8.1.2")
    implementation("org.ow2.asm:asm:9.2")
    implementation("org.ow2.asm:asm-commons:9.2")
}