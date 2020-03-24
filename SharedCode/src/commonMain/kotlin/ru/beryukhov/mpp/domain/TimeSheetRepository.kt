package ru.beryukhov.mpp.domain

import com.soywiz.klock.DateTime
import io.ktor.client.HttpClient
import io.ktor.client.call.call
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.response
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.http.Url

interface TimeSheetRepository {
    suspend fun getRecordsFromServer(): List<DateTimeRecord>
}

open class BaseTimeSheetRepositoryImpl : TimeSheetRepository {

    override suspend fun getRecordsFromServer(): List<DateTimeRecord> {
        val client = HttpClient()
        val address = Url("https://beryukhov.ru/")

        val call = client.request<HttpResponse>(address) {
            method = HttpMethod.Get
        }
        client.close()
        if (call.status == OK) {
            return listOf(
                    DateTimeRecord(DateTime.createAdjusted(2019, 9, 22, 9), true),
                    DateTimeRecord(DateTime.createAdjusted(2019, 9, 22, 17, 30), false)
            )
        }
        return emptyList()

    }
}
