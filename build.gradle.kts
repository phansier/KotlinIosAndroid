// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    val kotlin_version by extra("1.5.30")
    val coroutines_version by extra("1.5.2-native-mt")
    val ktor_version by extra("1.6.4")
    val serialization_version by extra("1.3.0")
    val klockVersion by extra("2.4.8")

    //val text_encoding_version by extra("0.7.0")

    val logback_version by extra("1.2.3")

    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
        classpath("org.jetbrains.kotlin:kotlin-serialization:$kotlin_version")
        classpath("com.android.tools.build:gradle:7.0.3")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
