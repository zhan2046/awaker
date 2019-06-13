package com.ruzhan.day.source

import com.ruzhan.day.DayApi
import com.ruzhan.day.DayClient
import com.ruzhan.day.model.DayNewModel
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class DayRepository(private val dayApi: DayApi) {

    companion object {

        private var INSTANCE: DayRepository? = null

        @JvmStatic
        fun get(): DayRepository {
            if (INSTANCE == null) {
                synchronized(DayRepository::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = DayRepository(DayClient.get())
                    }
                }
            }
            return INSTANCE!!
        }
    }

    fun getDayNewList(page: Int, ver: String, appVer: String): Single<List<DayNewModel>> {
        return dayApi.getDayNewList(page, ver, appVer)
                .subscribeOn(Schedulers.io())
    }
}