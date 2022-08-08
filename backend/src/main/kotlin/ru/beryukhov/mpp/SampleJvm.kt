package ru.beryukhov.mpp

import com.google.gson.GsonBuilder
import com.soywiz.klock.DateTime
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.TextContent
import io.ktor.serialization.gson.gson
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.html.respondHtml
import io.ktor.server.http.content.resource
import io.ktor.server.http.content.static
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import kotlinx.html.body
import kotlinx.html.head
import kotlinx.html.script
import kotlinx.html.title
import ru.beryukhov.mpp.domain.DateTimeRecord


private val sampleList = listOf(
    DateTimeRecord(DateTime.createAdjusted(2019, 9, 22, 9), true),
    DateTimeRecord(DateTime.createAdjusted(2019, 9, 22, 17, 30), false)
)

fun main() {
    embeddedServer(Netty, port = 8080, host = "127.0.0.1") {

        install(ContentNegotiation) {
            gson {
                setPrettyPrinting()
            }
        }

        val gson = GsonBuilder()
            .setPrettyPrinting()
            .create()

        routing {
            get("/") {
                call.respondHtml {
                    head {
                        title("Hello from Ktor!")
                    }
                    body {
                        script(src = "/static/KotlinIosAndroid-SharedCode.js") {}
                    }
                }
            }
            get("/api") {
                call.respond(TextContent(gson.toJson(sampleList), ContentType.Application.Json))
            }
            post("/") {
                val map = call.receive<Map<*, *>>()
                val text = map.entries.joinToString { "${it.key}=${it.value}" }
                call.respond(text)
            }
            post("/fixStart") {
                println("call=$call")
                /*val post = call.receive<PostSnippet>()
                println(post)
                call.respond(post)*/
                call.respond(HttpStatusCode.OK)
            }
            post("/fixEnd") {
                call.respond(HttpStatusCode.OK)
            }

            static("/static") {
                resource("KotlinIosAndroid-SharedCode.js")
            }
        }
    }.start(wait = true)

    //todo check lib
}