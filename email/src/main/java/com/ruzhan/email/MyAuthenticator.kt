package com.ruzhan.email

import javax.mail.Authenticator
import javax.mail.PasswordAuthentication


class MyAuthenticator(username: String, password: String) : Authenticator() {

    private var userName: String? = username
    private var password: String? = password

    override fun getPasswordAuthentication(): PasswordAuthentication {
        return PasswordAuthentication(userName, password)
    }
}
