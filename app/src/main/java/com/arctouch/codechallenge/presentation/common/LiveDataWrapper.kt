package com.arctouch.codechallenge.presentation.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers

object LiveDataWrapper {
    fun <T> wrap(flowable : Flowable<T>,
                 mediator : MediatorLiveData<T>? = null
    ) : LiveData<@JvmSuppressWildcards T> {
        val result = mediator ?: MediatorLiveData()
        val source = LiveDataReactiveStreams.fromPublisher(
                flowable
                .observeOn(AndroidSchedulers.mainThread())
        )

        result.addSource(source) {
            result.postValue(it)
        }

        return result
    }

    fun <T> wrapFirst(flowable : Flowable<List<T>>,
                      mediator : MediatorLiveData<T>? = null
    ) : LiveData<T> {
        val result = mediator ?: MediatorLiveData()
        val source = LiveDataReactiveStreams.fromPublisher(flowable
                .observeOn(AndroidSchedulers.mainThread())
        )

        result.addSource(source) {
            if(it.isNotEmpty()){
                result.postValue(it.first())
            }
        }

        return result
    }

    fun <T> wrap(maybe : Maybe<T>,
                 mediator : MediatorLiveData<T>? = null
    ) : LiveData<T> {
        val result = mediator ?: MediatorLiveData()
        val source = LiveDataReactiveStreams.fromPublisher(
                maybe.toFlowable()
                        .observeOn(AndroidSchedulers.mainThread())
        )

        result.addSource(source){
            result.removeSource(source)
            result.postValue(it)
        }

        return result
    }

    fun <T, Q> wrapLoading(
        maybe : Maybe<T>,
        mediator : MediatorLiveData<Q>
    ) {
        val source = LiveDataReactiveStreams.fromPublisher(
                maybe.toFlowable()
                    .observeOn(AndroidSchedulers.mainThread())
        )

        mediator.addSource(source){
            mediator.removeSource(source)
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
