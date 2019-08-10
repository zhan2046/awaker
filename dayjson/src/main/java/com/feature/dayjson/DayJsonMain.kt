package com.feature.dayjson

import com.feature.dayjson.model.DayNewModel
import com.feature.dayjson.model.HttpResult
import com.feature.dayjson.network.DayRepository
import com.feature.dayjson.utils.JsonFileIOUtils
import com.google.gson.Gson
import java.io.File

object DayJsonMain {

    private const val USER_DIR = "user.dir"
    private const val JSON = "json"
    private const val API = "api"
    private const val DAY = "day"
    private const val FILE_TYPE = ".json"
    private const val CODE_SUCCESS = 200

    private val mainGSon = Gson()
    private var dayListFile = File("")

    @JvmStatic
    fun main(args: Array<String>) {
        println("=== main init ===")
        initCreateJsonFile()
        handleDayNewList()
        println("=== main end ===")
    }

    private fun handleDayNewList() {
        println("=== handleDayNewList call... ===")
        val disposable = DayRepository.get()
                .getDayNewList(1, "android", "36")
                .doOnError { e ->
                    println("=== doOnError called... ===$e")
                }
                .doOnSuccess { list ->
                    println("=== doOnSuccess called... ===" + "list:" + list.size)
                    dayNewModelListJsonFile(list, dayListFile.absolutePath, mainGSon)
                }
                .subscribe({ }, { })
    }

    private fun initCreateJsonFile() {
        println("initCreateJsonFile call...")
        val userDirFile = File(System.getProperty(USER_DIR)).parentFile
        println("userDirFile:${userDirFile.absolutePath}")

        val createJsonFile = File(File(userDirFile, JSON), API)
        println("createJsonFile:${createJsonFile.absolutePath}")

        dayListFile = File(createJsonFile, DAY)
        println("movieListFile:${dayListFile.absolutePath}")
        println("initCreateJsonFile end ... ")
    }

    private fun dayNewModelListJsonFile(dayNewModelList: List<DayNewModel>,
                                    rootPath: String, mainGSon: Gson) {
        val filePath = File(File(rootPath),
                "1".plus(FILE_TYPE)).absolutePath
        val httpResult = HttpResult(CODE_SUCCESS,
                "", dayNewModelList)
        val fileJson = mainGSon.toJson(httpResult)
        val isKeyPageSuccess = JsonFileIOUtils.writeFileFromString(filePath, fileJson)
        println("isKeyPageSuccess:$isKeyPageSuccess, filePath:$filePath")
        println("filePath: " + File(filePath).exists())
    }
}
