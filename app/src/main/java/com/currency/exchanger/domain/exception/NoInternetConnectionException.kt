package com.currency.exchanger.domain.exception

class NoInternetConnectionException(
    override val cause: Throwable?
) : Exception("No Internet connection", cause)