package com.feature.dayjson.network

import com.feature.dayjson.model.DayNewModel
import com.feature.dayjson.model.HttpResult
import com.feature.dayjson.model.MainModel
import io.reactivex.Single
import okhttp3.ResponseBody

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

    private val fileDownloadApi: FileDownloadApi by lazy {
        FileDownloadClient.get()
    }

    fun getDayNewList(page: Int, ver: String, appVer: String): Single<List<DayNewModel>> {
        return dayApi.getDayNewList(page, ver, appVer)
    }

    fun getBackupDayNewList(pageFileName: String): Single<HttpResult<List<DayNewModel>>> {
        return dayBackupApi.getBackupDayNewList(pageFileName)
    }

    fun getMainModel(pageFileName: String): Single<HttpResult<MainModel>> {
        return dayBackupApi.getMainModel(pageFileName)
    }

    fun requestFileDownload(url: String): Single<ResponseBody> {
        return fileDownloadApi.requestFileDownload(url)
    }
}