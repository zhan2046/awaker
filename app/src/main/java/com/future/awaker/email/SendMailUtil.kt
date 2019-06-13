package com.future.awaker.email

import java.io.File


object SendMailUtil {

    private const val EMAIL_TITLE = "来自 (awaker android app) 用户反馈"

    //qq
    private const val HOST = "smtp.qq.com"
    private const val PORT = "587"
    private const val FROM_ADD = "ruzhan666@foxmail.com" // 发送方邮箱
    private const val FROM_PSW = "mmrhffjtghlsdjjd" // 发送方邮箱授权码
    private const val TO_ADD = "dev19921116@gmail.com" // 发到哪个邮件去

    fun send(file: File, contentData: String) {
        val mailInfo = createMail(contentData)
        val sms = MailSender()
        Thread { sms.sendFileMail(mailInfo, file) }.start()
    }

    fun send(contentData: String) {
        val mailInfo = createMail(contentData)
        val sms = MailSender()
        Thread { sms.sendTextMail(mailInfo) }.start()
    }

    private fun createMail(contentData: String): MailInfo {
        val mailInfo = MailInfo()
        mailInfo.mailServerHost = HOST
        mailInfo.mailServerPort = PORT
        mailInfo.isValidate = true
        mailInfo.userName = FROM_ADD // 你的邮箱地址
        mailInfo.password = FROM_PSW// 您的邮箱密码
        mailInfo.fromAddress = FROM_ADD // 发送的邮箱
        mailInfo.toAddress = TO_ADD // 发到哪个邮件去
        mailInfo.subject = EMAIL_TITLE // 邮件主题
        mailInfo.content = contentData // 邮件文本
        return mailInfo
    }
}
