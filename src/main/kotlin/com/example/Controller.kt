package com.example

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import io.micronaut.tracing.annotation.NewSpan
import javax.inject.Inject

@Controller("/foo")
open class Controller @Inject constructor(private val someService: SomeService){


    @NewSpan
    @Get("/bar")
    @Produces(MediaType.TEXT_PLAIN)
    open fun bar(): HttpResponse<Any> {

        return HttpResponse.ok(someService.someWork())
    }

    @NewSpan
    @Get("/asyncBar")
    @Produces(MediaType.TEXT_PLAIN)
    open suspend fun asyncBar(): HttpResponse<Any> {

        return HttpResponse.ok(someService.someAsyncWork())
    }

}