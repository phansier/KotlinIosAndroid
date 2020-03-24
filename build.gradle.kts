// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    val kotlin_version by extra("1.3.70")
    val coroutines_version by extra("1.3.4")
    val ktor_version by extra("1.3.2")
    val serialization_version by extra("0.20.0")
    val klockVersion by extra("1.9.1")

    val text_encoding_version by extra("0.7.0")

    val logback_version by extra("1.2.3")

    repositories {
        maven(url = "https://kotlin.bintray.com/kotlinx")
        maven(url = "https://dl.bintray.com/kotlin/kotlin-eap/")
        maven(url = "https://dl.bintray.com/jetbrains/kotlin-native-dependencies")
        maven(url = "https://dl.bintray.com/korlibs/korlibs")
        maven(url = "https://dl.bintray.com/soywiz/soywiz")

        google()
        jcenter()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
        classpath("org.jetbrains.kotlin:kotlin-serialization:$kotlin_version")
        classpath("com.android.tools.build:gradle:4.0.0-beta01")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        maven(url = "https://kotlin.bintray.com/kotlinx")
        maven(url = "https://dl.bintray.com/kotlin/kotlin-eap/")
        maven(url = "https://dl.bintray.com/kotlin/ktor")
        maven(url = "https://dl.bintray.com/korlibs/korlibs")
        maven(url = "https://dl.bintray.com/soywiz/soywiz")


        google()
        jcenter()
    }
}
