package com.example

import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.client.annotation.Client
import io.micronaut.tracing.annotation.NewSpan
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import mu.KotlinLogging
import javax.inject.Inject
import kotlin.math.log

open class SomeService @Inject constructor(private val client: PostmanClient) {

    companion object {
        private val logger = KotlinLogging.logger { }
    }

    @NewSpan
    open fun someWork(): String? {
        logger.info("On thread")
        return client.getFromApi()
    }

    @NewSpan
    open suspend fun someAsyncWork(): String? {

        return withContext(Dispatchers.Default){
            delay(2000)
            logger.info("In coroutine")
            someRoutineWork()
            client.getFromApi()
        }

    }

    @NewSpan
    open suspend fun someRoutineWork(): String {
        return withContext(Dispatchers.Default) {
            delay(100)
            "Result from routine"
        }
    }

}

@Client("https://postman-echo.com/")
interface PostmanClient {

    @NewSpan
    @Get("/get")
    fun getFromApi(): String?

}