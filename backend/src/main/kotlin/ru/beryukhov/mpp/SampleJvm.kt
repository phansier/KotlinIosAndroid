package ru.beryukhov.mpp

import com.soywiz.klock.DateTime
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.GsonConverter
import io.ktor.html.respondHtml
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.TextContent
import io.ktor.http.content.resource
import io.ktor.http.content.static
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing

import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlinx.html.*
import ru.beryukhov.mpp.domain.DateTimeRecord
import com.google.gson.GsonBuilder



private val sampleList = listOf(
        DateTimeRecord(DateTime.createAdjusted(2019, 9, 22, 9), true),
        DateTimeRecord(DateTime.createAdjusted(2019, 9, 22, 17, 30), false)
)

fun main() {
    embeddedServer(Netty, port = 8080, host = "127.0.0.1") {

        install(ContentNegotiation) {
            //register(ContentType.Application.Json, SerializationConverter())
            register(ContentType.Application.Json, GsonConverter())
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
            post("/"){
                val map = call.receive<Map<*, *>>()
                val text = map.entries.joinToString { "${it.key}=${it.value}" }
                call.respond(text)
            }
            post("/fixStart"){
                println("call=$call")
                /*val post = call.receive<PostSnippet>()
                println(post)
                call.respond(post)*/
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