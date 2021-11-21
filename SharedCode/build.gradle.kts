import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
}

kotlin {
    //target for both Android & Backend
    jvm()

    ios {
        binaries {
            framework {
                baseName = "SharedCode"
            }
        }
    }
    js(IR) {
        browser()
    }


    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-stdlib")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${rootProject.extra["coroutines_version"]}") {
                    version {
                        strictly("1.5.2-native-mt")
                    }
                }

                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${rootProject.extra["serialization_version"]}")

                implementation("io.ktor:ktor-client-core:${rootProject.extra["ktor_version"]}")
                implementation("io.ktor:ktor-client-json:${rootProject.extra["ktor_version"]}")

                implementation("com.soywiz.korlibs.klock:klock:${rootProject.extra["klockVersion"]}")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-test-common:${rootProject.extra["kotlin_version"]}")
                implementation("org.jetbrains.kotlin:kotlin-test-annotations-common:${rootProject.extra["kotlin_version"]}")
            }
        }


        val jvmMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-stdlib:${rootProject.extra["kotlin_version"]}")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${rootProject.extra["coroutines_version"]}")


                implementation("io.ktor:ktor-client-core-jvm:${rootProject.extra["ktor_version"]}")
                implementation("io.ktor:ktor-client-android:${rootProject.extra["ktor_version"]}")
                implementation("io.ktor:ktor-client-json-jvm:${rootProject.extra["ktor_version"]}")
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-test:${rootProject.extra["kotlin_version"]}")
                implementation("org.jetbrains.kotlin:kotlin-test-junit:${rootProject.extra["kotlin_version"]}")
            }
        }

        val iosMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-ios:${rootProject.extra["ktor_version"]}")
            }
        }

        val jsMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-js:${rootProject.extra["ktor_version"]}")
                implementation("io.ktor:ktor-client-core-js:${rootProject.extra["ktor_version"]}")
                implementation("io.ktor:ktor-client-json-js:${rootProject.extra["ktor_version"]}")

                implementation("org.jetbrains.kotlinx:kotlinx-html-js:0.7.3")
            }
        }
    }
}


/*jvmJar {
    if (pBuildJs.toBoolean()) {
        dependsOn(jsBrowserWebpack)
        doLast {
            copy {
                from(new File (jsBrowserWebpack.entry.name, jsBrowserWebpack.outputPath))
                into "../backend/resources"
            }
        }
    }
}*/
