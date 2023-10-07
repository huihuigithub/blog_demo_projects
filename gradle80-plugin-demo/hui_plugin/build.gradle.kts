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
}