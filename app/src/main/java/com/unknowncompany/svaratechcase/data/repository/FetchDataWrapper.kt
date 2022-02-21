package com.unknowncompany.svaratechcase.data.repository

import com.unknowncompany.svaratechcase.data.source.remote.ResponseWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import retrofit2.HttpException
import java.io.IOException

abstract class FetchDataWrapper<Response, Model> {

    protected abstract suspend fun fetchData(): Response

    protected abstract suspend fun mapData(response: Response): Model

    suspend fun getData(): Flow<ResponseWrapper<Model>> {
        return flow<ResponseWrapper<Model>> {
            val model = mapData(fetchData())
            emit(ResponseWrapper.Success(model))
        }.onStart {
            emit(ResponseWrapper.Loading())
        }.catch { exception ->
            emit(when {
                isNetworkError(exception) -> ResponseWrapper.NetworkError()
                isHttpError(exception) -> {
                    val httpException = exception as HttpException
                    ResponseWrapper.HttpError(httpException.code(), httpException.message())
                }
                else -> ResponseWrapper.Error(exception.message)
            }
            )
        }
    }

    private fun isNetworkError(throwable: Throwable) = throwable is IOException

    private fun isHttpError(throwable: Throwable) = throwable is HttpException
}