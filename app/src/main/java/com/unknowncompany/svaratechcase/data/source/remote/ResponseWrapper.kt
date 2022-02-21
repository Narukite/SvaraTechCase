package com.unknowncompany.svaratechcase.data.source.remote

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

sealed class ResponseWrapper<T> {

    data class Success<T>(val data: T) : ResponseWrapper<T>()

    data class Loading<T>(val data: T? = null) : ResponseWrapper<T>()

    data class Error<T>(val message: String? = null) : ResponseWrapper<T>()

    data class HttpError<T>(val code: Int? = null, val message: String? = null) :
        ResponseWrapper<T>()

    data class NetworkError<T>(val message: String? = null) : ResponseWrapper<T>()
}

suspend fun <T> Flow<ResponseWrapper<T>>.runFlow(
    onSuccessFetch: (T) -> Unit,
    onLoadingFetch: (() -> Unit)? = null,
    onFailFetch: ((ResponseWrapper<*>) -> Unit)? = null,
) {
    collect { wrapper -> checkResponse(wrapper, onSuccessFetch, onLoadingFetch, onFailFetch) }
}

private fun <T> checkResponse(
    wrapper: ResponseWrapper<T>,
    onSuccessFetch: (T) -> Unit,
    onLoadingFetch: (() -> Unit)?,
    onFailFetch: ((ResponseWrapper<*>) -> Unit)?,
) {
    when (wrapper) {
        is ResponseWrapper.Success -> onSuccessFetch.invoke(wrapper.data)
        is ResponseWrapper.Loading -> onLoadingFetch?.invoke()
        else -> onFailFetch?.invoke(wrapper)
    }
}