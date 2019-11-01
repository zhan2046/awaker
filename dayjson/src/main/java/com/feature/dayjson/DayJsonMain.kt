package com.feature.dayjson

import com.feature.dayjson.model.DayNewModel
import com.feature.dayjson.model.HttpResult
import com.feature.dayjson.model.MainModel
import com.feature.dayjson.network.DayRepository
import com.feature.dayjson.utils.JsonFileIOUtils
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable
import okhttp3.ResponseBody
import java.io.*


object DayJsonMain {

    private const val USER_DIR = "user.dir"
    private const val JSON = "json"
    private const val IMAGE = "image"
    private const val API = "api"
    private const val DAY = "day"
    private const val FILE_TYPE = ".json"
    private const val MAIN_FILE_NAME = "main.json"
    private const val CODE_SUCCESS = 200
    private const val MAX_FILE_SIZE = 100

    private val mainGSon = Gson()
    private var dayListFile = File("")
    private var downloadImageFile = File("")

    private val backupList = ArrayList<DayNewModel>()

    private var isHandleBackupDayNewListSuccess = false
    private var isHandleAllDayNewListSuccess = false

    private val compositeDisposable = CompositeDisposable()

    @JvmStatic
    fun main(args: Array<String>) {
        println("=== main init ===")
        initCreateJsonFile()
        handleBackupDayNewList()
        handleDayNewList()
        compositeDisposable.clear()
        println("=== main end ===")
    }

    private fun handleAllDayNewList(list: List<DayNewModel>): ArrayList<DayNewModel> {
        val newList = ArrayList(list)
        for (oldItem in backupList) {
            var isExist = false
            for (item in list) {
                if (oldItem == item) {
                    isExist = true
                    break
                }
            }
            if (!isExist) {
                newList.add(oldItem)
            }
        }
        return newList
    }

    private fun handleBackupDayNewList() {
        compositeDisposable.add(DayRepository.get().getMainModel(MAIN_FILE_NAME)
                .doOnError { e ->
                    println("=== getMainModel doOnError called... ===$e")
                }
                .doOnSuccess { mainModel ->
                    println("=== getMainModel doOnSuccess called... ===" + "mainModel:" + mainModel.pageSize)
                    println("=== handleBackupDayNewList call... ===")
                    backupList.clear()
                    for (count in 1..mainModel.pageSize) {
                        compositeDisposable.add(DayRepository.get()
                                .getBackupDayNewList(count.toString().plus(FILE_TYPE))
                                .map { result -> result.data }
                                .doOnError { e ->
                                    println("=== Backup doOnError called... ===$e")
                                }
                                .doOnSuccess { list ->
                                    println("=== Backup doOnSuccess called... ===" + "list:" + list.size)
                                    backupList.addAll(list)
                                }
                                .subscribe({ }, { }))
                    }
                    if (backupList.isNotEmpty()) {
                        isHandleBackupDayNewListSuccess = true
                    }
                }.subscribe({ }, { }))
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
                    val newList = handleAllDayNewList(list)
                    isHandleAllDayNewListSuccess = true
                    if (isHandleAllDayNewListSuccess && isHandleBackupDayNewListSuccess) {
                        dayNewModelListJsonFile(newList, dayListFile.absolutePath, mainGSon)
                    } else {
                        println("isHandleAllDayNewListSuccess: " + isHandleAllDayNewListSuccess +
                                " ,isHandleBackupDayNewListSuccess:" + isHandleBackupDayNewListSuccess)
                    }
                }
                .subscribe({ }, { })
        compositeDisposable.add(disposable)
    }

    private fun initCreateJsonFile() {
        println("initCreateJsonFile call...")
        val dirFile = File(System.getProperty(USER_DIR))
        val userDirFile = if (dirFile.absolutePath.contains("dayjson"))
            dirFile.parentFile else dirFile
        println("userDirFile:${userDirFile.absolutePath}")

        val createJsonFile = File(File(userDirFile, JSON), API)
        println("createJsonFile:${createJsonFile.absolutePath}")

        downloadImageFile = File(userDirFile, IMAGE)
        println("downloadImageFile:${downloadImageFile.absolutePath}")

        dayListFile = File(createJsonFile, DAY)
        println("movieListFile:${dayListFile.absolutePath}")
        println("initCreateJsonFile end ... ")
    }

    private fun dayNewModelListJsonFile(list: List<DayNewModel>,
                                        rootPath: String, mainGSon: Gson) {
        val dayNewModelList = handleNewModelList(list)
        val totalList = splitList(dayNewModelList, MAX_FILE_SIZE)
        for (i in totalList.indices) {
            val childList = totalList[i]
            val filePath = File(File(rootPath),
                    (i + 1).toString().plus(FILE_TYPE)).absolutePath
            val httpResult = HttpResult(CODE_SUCCESS,
                    "", childList)
            val fileJson = mainGSon.toJson(httpResult)
            val isKeyPageSuccess = JsonFileIOUtils.writeFileFromString(filePath, fileJson)
            println("isKeyPageSuccess:$isKeyPageSuccess, filePath:$filePath")
            println("filePath: " + File(filePath).exists())
        }
        println("======")
        println("======")
        println("=== handler  MainModel===")
        val filePath = File(File(rootPath),
                "main".plus(FILE_TYPE)).absolutePath
        val httpResult = HttpResult(CODE_SUCCESS,
                "", MainModel(totalList.size))
        val fileJson = mainGSon.toJson(httpResult)
        val isKeyPageSuccess = JsonFileIOUtils.writeFileFromString(filePath, fileJson)
        println("MainModel isKeyPageSuccess:$isKeyPageSuccess, filePath:$filePath")
        println("MainModel filePath: " + File(filePath).exists())
        println("=== handler  MainModel finish ===")
        println("======")
        println("======")

        println("=== totalList size... ===" + "totalList: " + totalList.size + " count page")
        println("=== new list size... ===" + "list:" + dayNewModelList.size)

        val imageAllList = ArrayList<DayNewModel>()
        for (i in totalList.indices) {
            val childList = totalList[i]
            imageAllList.addAll(childList)
        }
        handlerImageDownload(imageAllList)
    }

    private fun handlerImageDownload(list: List<DayNewModel>) {
        for (index in list.indices) {
            val item = list[index]
            if (item.tags == null) {
                continue
            }
            val title = item.title
            if (title != null && title.isNotBlank()) {
                val dayDir = File(downloadImageFile, title)
                if (!dayDir.exists()) {
                    dayDir.mkdirs()
                }
                val guid = item.guid
                val coverLandscape = item.cover_landscape
                requestFileDownload(coverLandscape, guid, dayDir, "")
                val coverLandscapeHD = item.cover_landscape_hd
                requestFileDownload(coverLandscapeHD, guid, dayDir, "HD")
                println("handlerImageDownload current: ($index) finish")
            } else {
                println("=== find title text is empty === $item" + " : " + item.title)
            }
        }
        println("========================")
        println("========================")
        println("========================")
        println("handlerImageDownload task finish, handler: (" + list.size + ") pic")
    }

    private fun requestFileDownload(url: String) {
        val disposable = DayRepository.get().requestFileDownload(url)
                .doOnError { e ->
                    println("=== doOnError called... ===$e")
                }
                .doOnSuccess { responseBody ->
                    println("=== requestFileDownload doOnSuccess === $url")
                }
                .subscribe({ }, { })
        compositeDisposable.add(disposable)
    }

    private fun createDownloadImageFile(url: String?, guid: Int?, dayDir: File?, tag: String): File? {
        if (url != null && url.isNotBlank() && dayDir != null && guid != null) {
            val tagIndex = url.lastIndexOf("/")
            if (tagIndex != -1) {
                val imageName = url.substring(tagIndex + 1, url.length)
                val centerTag = if (tag.isBlank()) "-" else
                    "-".plus(tag).plus("-")
                return File(dayDir, guid.toString()
                        .plus(centerTag).plus(imageName))
            }
        }
        return null
    }

    private fun requestFileDownload(url: String?, guid: Int?, dayDir: File?, tag: String) {
        val imageFile = createDownloadImageFile(url, guid, dayDir, tag)
        if (imageFile != null && url != null) {
            if (!imageFile.exists()) {
                imageFile.createNewFile()
                exeRequestFileDownload(url, imageFile)
            } else if (imageFile.length() == 0L) {
                imageFile.delete()
                imageFile.createNewFile()
                exeRequestFileDownload(url, imageFile)
            }
        }
    }

    private fun exeRequestFileDownload(url: String, imageFile: File) {
        println("=== requestFileDownload start: $url")
        val disposable = DayRepository.get().requestFileDownload(url)
                .doOnError { e ->
                    println("=== doOnError called... ===$e")
                }
                .doOnSuccess { responseBody ->
                    writeFile2Disk(responseBody, imageFile)
                }
                .subscribe({ }, { })
        compositeDisposable.add(disposable)
    }

    private fun writeFile2Disk(responseBody: ResponseBody, file: File) {
        var currentLength = 0L
        var outputStream: OutputStream? = null
        val inputStream = responseBody.byteStream() //获取下载输入流
        try {
            outputStream = FileOutputStream(file) //输出流
            var len = 0
            val buff = ByteArray(1024)
            while (len != -1) {
                len = inputStream.read(buff)
                outputStream.write(buff, 0, len)
                currentLength += len
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            println("=== writeFile2Disk save finish: " + file.absolutePath)
            if (outputStream != null) {
                try {
                    outputStream.close() //关闭输出流
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close() //关闭输入流
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun splitList(list: List<DayNewModel>, groupSize: Int): List<List<DayNewModel>> {
        val length = list.size
        // 计算可以分成多少组
        val num = (length + groupSize - 1) / groupSize
        val newList = ArrayList<List<DayNewModel>>(num)
        for (i in 0 until num) {
            // 开始位置
            val fromIndex = i * groupSize
            // 结束位置
            val toIndex = if ((i + 1) * groupSize < length) (i + 1) * groupSize else length

            newList.add(list.subList(fromIndex, toIndex))
        }
        return newList
    }

    private fun handleNewModelList(list: List<DayNewModel>): ArrayList<DayNewModel> {
        val newList = ArrayList<DayNewModel>()
        for (item in list) {
            if (!newList.contains(item)) {
                newList.add(item)
            }
        }
        return newList
    }
}
