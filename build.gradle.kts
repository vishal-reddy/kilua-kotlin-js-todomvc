import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kilua.rpc)
    alias(libs.plugins.kilua)
}

extra["mainClassName"] = "io.ktor.server.netty.EngineMain"

@OptIn(ExperimentalWasmDsl::class)
kotlin {
    jvmToolchain(21)
    jvm {
        compilerOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
        }
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        mainRun {
            mainClass.set(project.extra["mainClassName"]!!.toString())
        }
    }
    js(IR) {
        useEsModules()
        browser {
            commonWebpackConfig {
                outputFileName = "main.bundle.js"
                sourceMaps = false
            }
            testTask {
                useKarma {
                    useChromeHeadless()
                }
            }
        }
        binaries.executable()
        compilerOptions {
            target.set("es2015")
        }
    }
    wasmJs {
        useEsModules()
        browser {
            commonWebpackConfig {
                outputFileName = "main.bundle.js"
                sourceMaps = false
            }
            testTask {
                useKarma {
                    useChromeHeadless()
                }
            }
        }
        binaries.executable()
        compilerOptions {
            target.set("es2015")
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(libs.kilua.ssr.server)
                implementation(libs.ktor.server.netty)
                implementation(libs.ktor.server.compression)
                implementation(libs.logback.classic)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
            }
        }
        val webMain by creating {
            dependsOn(commonMain)
            dependencies {
                implementation(libs.kilua)
                implementation(libs.kilua.tailwindcss)
                implementation(libs.kilua.fontawesome)
                implementation(libs.kilua.routing)
                implementation(libs.kilua.tempus.dominus)
                implementation(libs.kilua.trix)
                implementation(libs.kilua.tom.select)
                implementation(libs.kilua.imask)
                implementation(libs.kilua.toastify)
                implementation(libs.kilua.splitjs)
                implementation(libs.kilua.rsup.progress)
                implementation(libs.kilua.tabulator)
                implementation(libs.kilua.leaflet)
                implementation(libs.kilua.jetpack)
                implementation(libs.kilua.lazy.layouts)
                implementation(libs.kilua.animation)
                implementation(libs.kilua.marked)
                implementation(libs.kilua.sanitize.html)
                implementation(libs.kilua.svg)
                implementation(libs.kilua.rest)
                implementation(libs.kilua.ssr)
            }
        }
        val jsMain by getting {
            dependsOn(webMain)
        }
        val wasmJsMain by getting {
            dependsOn(webMain)
        }
        val webTest by creating {
            dependsOn(commonTest)
            dependencies {
                implementation(libs.kilua.testutils)
            }
        }
        val jsTest by getting {
            dependsOn(webTest)
        }
        val wasmJsTest by getting {
            dependsOn(webTest)
        }
    }
}

composeCompiler {
    targetKotlinPlatforms.set(
        KotlinPlatformType.values()
            .filterNot { it == KotlinPlatformType.jvm }
            .asIterable()
    )
}

