package ru.beryukhov.mpp

import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.html.respondHtml
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.Parameters
import io.ktor.http.content.MultiPartData
import io.ktor.http.content.TextContent
import io.ktor.http.content.resource
import io.ktor.http.content.static
import io.ktor.request.receive
import io.ktor.request.receiveMultipart
import io.ktor.request.receiveParameters
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.serialization.serialization
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.head
import kotlinx.html.id
import kotlinx.html.script
import kotlinx.html.title

data class PostSnippet(val snippet: Text) {
    data class Text(val text: String)
}

fun main() {
    embeddedServer(Netty, port = 8080, host = "127.0.0.1") {
        install(ContentNegotiation) {
            serialization()
        }
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
            get("/api") {
                call.respond(TextContent("{}", ContentType.Application.Json))
            }
            post("/fixStart"){
                val post = call.receive<PostSnippet>()
                println(post)
                call.respond(HttpStatusCode.OK)
            }
            post("/fixEnd"){
                call.respond(HttpStatusCode.OK)
            }

            static("/static") {
                resource("KotlinIosAndroid-SharedCode.js")
            }
        }
    }.start(wait = true)

    //todo check lib
}