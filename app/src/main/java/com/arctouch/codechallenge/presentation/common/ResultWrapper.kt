package com.arctouch.codechallenge.presentation.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers

object ResultWrapper {
    fun <T> wrap(flowable : Flowable<T>,
                 mediator : MediatorLiveData<Result<T>>? = null
    ) : LiveData<@JvmSuppressWildcards Result<T>> {
        val result = mediator ?: MediatorLiveData()
        val source = LiveDataReactiveStreams.fromPublisher(
                Flowable.fromCallable {
                    result.postValue(Result.waiting())
                }.flatMap {
                    flowable
                }
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    if(it == null || (it is Collection<*> && it.isEmpty())){
                        Result.empty()
                    }
                    else {
                        Result.withData(it)
                    }
                }.onErrorReturn {
                    it.printStackTrace()
                    Result.error(it)
                }
        )

        result.addSource(source) {
            result.postValue(it)
        }

        return result
    }

    fun <T> wrapFirst(flowable : Flowable<List<T>>,
                      mediator : MediatorLiveData<Result<T>>? = null
    ) : LiveData<Result<T>> {
        val result = mediator ?: MediatorLiveData()
        val source = LiveDataReactiveStreams.fromPublisher(flowable
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    if (it.isNotEmpty()) Result.withData(it.first()) else Result.empty()
                }.onErrorReturn {
                    it.printStackTrace()
                    Result.error(it)
                }
        )

        result.addSource(source) {
            result.postValue(it)
        }

        return result
    }

    fun <T> wrap(maybe : Maybe<T>,
                 mediator : MediatorLiveData<Result<T>>? = null
    ) : LiveData<Result<T>> {
        val result = mediator ?: MediatorLiveData()
        val source = LiveDataReactiveStreams.fromPublisher(
            Flowable.fromCallable {
                result.postValue(Result.waiting())
            }.flatMap {
                maybe.toFlowable()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                if(it == null || (it is Collection<*> && it.isEmpty())){
                    Result.empty()
                }
                else {
                    Result.withData(it)
                }
            }.onErrorReturn {
                it.printStackTrace()
                Result.error(it)
            }
        )

        result.addSource(source){
            result.removeSource(source)
            result.postValue(it)
        }

        return result
    }

    fun <T, Q> wrapLoading(
        maybe : Maybe<T>,
        mediator : MediatorLiveData<Result<Q>>
    ) {
        val source = LiveDataReactiveStreams.fromPublisher(
            Flowable.fromCallable {
                mediator.postValue(Result.waiting())
            }.flatMap {
                maybe.toFlowable()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                Result.empty<T>()
            }.onErrorReturn {
                it.printStackTrace()
                Result.error(it)
            }
        )

        mediator.addSource(source){
            mediator.removeSource(source)
            if(it.failed){
                mediator.postValue(Result.error(it.error!!))
            }
        }
    }

//    fun <T> wrapEach(maybe : Maybe<List<T>>,
//                     mediator : MediatorLiveData<br.com.softbuilder.appplus2.presentation.common.Result<T>>? = null
//    ) : LiveData<br.com.softbuilder.appplus2.presentation.common.Result<T>> {
//        val source = LiveDataReactiveStreams.fromPublisher(maybe.toFlowable()
//            .observeOn(AndroidSchedulers.mainThread())
//            .map {
//                if (it.isEmpty()) br.com.softbuilder.appplus2.presentation.common.Result.empty() else br.com.softbuilder.appplus2.presentation.common.Result.withData(it)
//            }.onErrorReturn {
//                it.printStackTrace()
//                br.com.softbuilder.appplus2.presentation.common.Result.error(it)
//            }
//        )
//
//        val result = mediator ?: MediatorLiveData()
//
//        result.addSource(source){ ret ->
//            result.removeSource(source)
//
//            if(ret.returnedData){
//                ret.data?.forEach {
//                    result.value = br.com.softbuilder.appplus2.presentation.common.Result.withData(it)
//                }
//            }
//            else if(ret.empty){
//                result.value = br.com.softbuilder.appplus2.presentation.common.Result.empty()
//            }
//            else if(ret.failed){
//                result.value = br.com.softbuilder.appplus2.presentation.common.Result.error(ret.error!!)
//            }
//        }
//
//        result.postValue(br.com.softbuilder.appplus2.presentation.common.Result.waiting())
//
//        return result
//    }
}
