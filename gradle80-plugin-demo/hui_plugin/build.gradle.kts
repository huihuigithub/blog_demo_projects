plugins {
    id("java-gradle-plugin") //会自动引入java-library、gradleApi()
    id("org.jetbrains.kotlin.jvm") //支持kotlin编写插件
    id("maven-publish") //maven发布插件
}

gradlePlugin {
    plugins {
        create("pagePlugin") {
            group = "com.znh.page"
            version = "1.0.0"
            id = "com.znh.gradle80.page"
            implementationClass = "com.znh.gradle80.page.PageAnalysisPlugin"
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