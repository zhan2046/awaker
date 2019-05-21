package com.future.awaker.email;

import android.support.annotation.NonNull;

import java.io.File;


public class SendMailUtil {

    private static final String EMAIL_TITLE = "来自 (awaker android app) 用户反馈";

    //qq
    private static final String HOST = "smtp.qq.com";
    private static final String PORT = "587";
    private static final String FROM_ADD = "ruzhan666@foxmail.com"; // 发送方邮箱
    private static final String FROM_PSW = "mmrhffjtghlsdjjd"; // 发送方邮箱授权码
    private static final String TO_ADD = "dev19921116@gmail.com"; // 发到哪个邮件去

    public static void send(final File file, String contentData) {
        final MailInfo mailInfo = createMail(contentData);
        final MailSender sms = new MailSender();
        new Thread(() -> sms.sendFileMail(mailInfo, file)).start();
    }

    public static void send(String contentData) {
        final MailInfo mailInfo = createMail(contentData);
        final MailSender sms = new MailSender();
        new Thread(() -> sms.sendTextMail(mailInfo)).start();
    }

    @NonNull
    private static MailInfo createMail(String contentData) {
        final MailInfo mailInfo = new MailInfo();
        mailInfo.setMailServerHost(HOST);
        mailInfo.setMailServerPort(PORT);
        mailInfo.setValidate(true);
        mailInfo.setUserName(FROM_ADD); // 你的邮箱地址
        mailInfo.setPassword(FROM_PSW);// 您的邮箱密码
        mailInfo.setFromAddress(FROM_ADD); // 发送的邮箱
        mailInfo.setToAddress(TO_ADD); // 发到哪个邮件去
        mailInfo.setSubject(EMAIL_TITLE); // 邮件主题
        mailInfo.setContent(contentData); // 邮件文本
        return mailInfo;
    }
}
