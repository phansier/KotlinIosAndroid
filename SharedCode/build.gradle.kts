import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
}

kotlin {
    //target for both Android & Backend
    jvm()

    //select iOS target platform depending on the Xcode environment variables
    val iOSTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget =
            if (System.getenv("SDK_NAME")?.startsWith("iphoneos") == true)
                ::iosArm64
            else
                ::iosX64

    iOSTarget("ios") {
        binaries {
            framework {
                baseName = "SharedCode"
            }
        }
    }
    targets.getByName<KotlinNativeTarget>("ios").compilations["main"].kotlinOptions.freeCompilerArgs +=
            listOf("-Xobjc-generics", "-Xg0")

    js()

    /*sourceSets {
        all {
            languageSettings.useExperimentalAnnotation("kotlin.Experimental")
        }*/

    sourceSets["commonMain"].dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:${rootProject.extra["coroutines_version"]}")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:${rootProject.extra["serialization_version"]}")

        implementation("io.ktor:ktor-client-core:${rootProject.extra["ktor_version"]}")
        implementation("io.ktor:ktor-client-json:${rootProject.extra["ktor_version"]}")

        implementation("com.soywiz.korlibs.klock:klock:${rootProject.extra["klockVersion"]}")
    }

    sourceSets["commonTest"].dependencies {
        implementation("org.jetbrains.kotlin:kotlin-test-common:${rootProject.extra["kotlin_version"]}")
        implementation("org.jetbrains.kotlin:kotlin-test-annotations-common:${rootProject.extra["kotlin_version"]}")
    }


    sourceSets["jvmMain"].dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib:${rootProject.extra["kotlin_version"]}")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${rootProject.extra["coroutines_version"]}")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${rootProject.extra["coroutines_version"]}")

        implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:${rootProject.extra["serialization_version"]}")

        implementation("io.ktor:ktor-client-core-jvm:${rootProject.extra["ktor_version"]}")
        implementation("io.ktor:ktor-client-android:${rootProject.extra["ktor_version"]}")
        implementation("io.ktor:ktor-client-json-jvm:${rootProject.extra["ktor_version"]}")
    }

    sourceSets["jvmTest"].dependencies {
        implementation("org.jetbrains.kotlin:kotlin-test:${rootProject.extra["kotlin_version"]}")
        implementation("org.jetbrains.kotlin:kotlin-test-junit:${rootProject.extra["kotlin_version"]}")
    }

    sourceSets["iosMain"].dependencies {
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-native:${rootProject.extra["coroutines_version"]}")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-native:${rootProject.extra["serialization_version"]}")

        implementation("io.ktor:ktor-client-ios:${rootProject.extra["ktor_version"]}")
        implementation("io.ktor:ktor-client-core-native:${rootProject.extra["ktor_version"]}")
        implementation("io.ktor:ktor-client-json-native:${rootProject.extra["ktor_version"]}")
    }

    sourceSets["jsMain"].dependencies {
        //implementation kotlin ('stdlib-js')

        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:${rootProject.extra["coroutines_version"]}")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-js:${rootProject.extra["serialization_version"]}")

        implementation("io.ktor:ktor-client-js:${rootProject.extra["ktor_version"]}")
        implementation("io.ktor:ktor-client-core-js:${rootProject.extra["ktor_version"]}")
        implementation("io.ktor:ktor-client-json-js:${rootProject.extra["ktor_version"]}")

        implementation("org.jetbrains.kotlinx:kotlinx-html-js:0.6.12")
    }
    //workaround for https://github.com/ktorio/ktor/issues/961
    /*jsTest {
        dependencies {
            api(npm("text-encoding", text_encoding_version))
        }
    }*/


}


val packForXcode by tasks.creating(Sync::class) {
    val frameworkDir = File(buildDir, "xcode-frameworks")
/// selecting the right configuration for the iOS
/// framework depending on the environment
/// variables set by Xcode build
    val mode = System.getenv("XCODE_CONFIGURATION")?.toUpperCase() ?: "DEBUG"
    val framework = kotlin.targets
            .getByName<KotlinNativeTarget>("ios")
            .binaries.getFramework(mode)

    inputs.property("mode", mode)
    dependsOn(framework.linkTask)

    from({ framework.outputDirectory })
    into(frameworkDir)

/// generate a helpful ./gradlew wrapper with embedded Java path
    doLast {
        val gradlew = File(frameworkDir, "gradlew")
        gradlew.writeText("#!/bin/bash\n"
                + "export 'JAVA_HOME=${System.getProperty("java.home")}'\n"
                + "cd '${rootProject.rootDir}'\n"
                + "./gradlew \$@\n")
        gradlew.setExecutable(true)
    }
}

tasks.getByName("build").dependsOn(packForXcode)


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
