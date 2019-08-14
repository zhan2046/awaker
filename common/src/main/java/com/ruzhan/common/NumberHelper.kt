package com.ruzhan.common

object NumberHelper {

    fun numberToCH(number: Int): String {
        var numberStr = number.toString()
        var contentText = ""
        when {
            numberStr.length == 1 // 個
            -> {
                contentText += getChinaNumber(number)
                return contentText
            }
            numberStr.length == 2// 十
            -> {
                contentText += if (numberStr.substring(0, 1) == "1")
                    "十"
                else
                    (getChinaNumber(number / 10) + "十")
                contentText += numberToCH(number % 10)
            }
            numberStr.length == 3// 百
            -> {
                contentText += (getChinaNumber(number / 100) + "百")
                if ((number % 100).toString().length < 2)
                    contentText += "零"
                contentText += numberToCH(number % 100)
            }
            numberStr.length == 4// 千
            -> {
                contentText += (getChinaNumber(number / 1000) + "千")
                if ((number % 1000).toString().length < 3)
                    contentText += "零"
                contentText += numberToCH(number % 1000)
            }
            numberStr.length == 5// 萬
            -> {
                contentText += (getChinaNumber(number / 10000) + "萬")
                if ((number % 10000).toString().length < 4)
                    contentText += "零"
                contentText += numberToCH(number % 10000)
            }
        }
        return contentText
    }

    fun getChinaNumber(number: String): String {
        var content = ""
        val numberList = number.map(Character::getNumericValue)
        for (item in numberList) {
            content += getChinaNumber(item)
        }
        return content
    }

    fun getChinaNumber(number: Int): String {
        return when (number) {
            0 -> "零"
            1 -> "一"
            2 -> "二"
            3 -> "三"
            4 -> "四"
            5 -> "五"
            6 -> "六"
            7 -> "七"
            8 -> "八"
            9 -> "九"
            else -> ""
        }
    }
}