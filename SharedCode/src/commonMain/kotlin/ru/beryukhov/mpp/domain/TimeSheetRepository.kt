package ru.beryukhov.mpp.domain

import com.soywiz.klock.DateTime
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.http.Url

interface TimeSheetRepository {
    suspend fun getRecordsFromServer(): List<DateTimeRecord>
}

open class BaseTimeSheetRepositoryImpl : TimeSheetRepository {

    override suspend fun getRecordsFromServer(): List<DateTimeRecord> {
        val client = HttpClient()
        val address = Url("https://beryukhov.ru/")

        val call = client.get(url = address)
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
