package com.vlukereddy.todo.todomvc

import dev.kilua.Application
import dev.kilua.CoreModule
import dev.kilua.TailwindcssModule
import dev.kilua.FontAwesomeModule
import dev.kilua.TempusDominusModule
import dev.kilua.TrixModule
import dev.kilua.TomSelectModule
import dev.kilua.ImaskModule
import dev.kilua.ToastifyModule
import dev.kilua.SplitjsModule
import dev.kilua.TabulatorModule
import dev.kilua.LeafletModule
import dev.kilua.JetpackModule
import dev.kilua.AnimationModule
import dev.kilua.Hot
import dev.kilua.html.div
import dev.kilua.compose.root
import dev.kilua.ssr.SimpleSsrRouter
import dev.kilua.startApplication

class App : Application() {
    override fun start() {
        root("root") {
            SimpleSsrRouter {
                div {
                    route("/") {
                        div {
                            +"Hello world"
                        }
                    }

                    route("/hello") {
                        div {
                            +"Hello vishal"
                        }
                    }
                }
            }
        }
    }
}

fun main() {
    startApplication(
        ::App,
        webpackHot(),
        TailwindcssModule,
        FontAwesomeModule,
        TempusDominusModule,
        TrixModule,
        TomSelectModule,
        ImaskModule,
        ToastifyModule,
        SplitjsModule,
        TabulatorModule,
        LeafletModule,
        JetpackModule,
        AnimationModule,
        CoreModule
    )
}

expect fun webpackHot(): Hot?
