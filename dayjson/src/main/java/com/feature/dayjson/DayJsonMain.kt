package com.feature.dayjson

import com.feature.dayjson.network.DayRepository

object DayJsonMain {

    @JvmStatic
    fun main(args: Array<String>) {
        println("=== main init ===")
        handleDayNewList()
        println("=== main end ===")
    }

    private fun handleDayNewList() {
        val disposable = DayRepository.get()
                .getDayNewList(1, "android", "36")
                .doOnError { e ->
                    println("=== doOnError called... ===$e")
                }
                .doOnSuccess { list ->
                    println("=== doOnSuccess called... ===" + "list:" + list.size)
                }
                .subscribe({ }, { })
    }
}
