// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    val kotlin_version by extra("1.7.10")
    val coroutines_version by extra("1.6.4")
    val ktor_version by extra("2.0.3")
    val serialization_version by extra("1.4.0-RC")
    val klockVersion by extra("3.0.0")

    //val text_encoding_version by extra("0.7.0")

    val logback_version by extra("1.2.3")

    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
        classpath("org.jetbrains.kotlin:kotlin-serialization:$kotlin_version")
        classpath("com.android.tools.build:gradle:7.2.1")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
