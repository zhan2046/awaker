package com.feature.dayjson.network

import com.feature.dayjson.model.DayNewModel
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class DayRepository {

    companion object {

        private var INSTANCE: DayRepository? = null

        @JvmStatic
        fun get(): DayRepository = INSTANCE ?: synchronized(DayRepository::class) {
            INSTANCE ?: DayRepository().also {
                INSTANCE = it
            }
        }
    }

    private val dayApi: DayApi by lazy {
        DayClient.get()
    }

    fun getDayNewList(page: Int, ver: String, appVer: String): Single<List<DayNewModel>> {
        return dayApi.getDayNewList(page, ver, appVer)
    }
}