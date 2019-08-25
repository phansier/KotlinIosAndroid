package ru.beryukhov.mpp

import io.ktor.application.call
import io.ktor.html.respondHtml
import io.ktor.http.content.resource
import io.ktor.http.content.static
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.head
import kotlinx.html.id
import kotlinx.html.script
import kotlinx.html.title

fun main() {
    embeddedServer(Netty, port = 8080, host = "127.0.0.1") {
        routing {
            get("/") {
                call.respondHtml {
                    head {
                        title("Hello from Ktor!")
                    }
                    body {
                        +"Test"
                        div {
                            id = "js-response"
                            +"Loading..."
                        }
                        script(src = "/static/KotlinIosAndroid-SharedCode.js") {}
                    }
                }
            }
            static("/static") {
                resource("KotlinIosAndroid-SharedCode.js")
            }
        }
    }.start(wait = true)

    //todo check lib
}