package com.feature.dayjson.network

import com.feature.dayjson.model.DayNewModel
import com.feature.dayjson.model.HttpResult
import io.reactivex.Single

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

    private val dayBackupApi: DayBackupApi by lazy {
        DayBackupClient.get()
    }

    fun getDayNewList(page: Int, ver: String, appVer: String): Single<List<DayNewModel>> {
        return dayApi.getDayNewList(page, ver, appVer)
    }

    fun getBackupDayNewList(pageFileName: String): Single<HttpResult<List<DayNewModel>>> {
        return dayBackupApi.getBackupDayNewList(pageFileName)
    }
}