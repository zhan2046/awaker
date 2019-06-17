package com.ruzhan.email

import javax.mail.Authenticator
import javax.mail.PasswordAuthentication


class MyAuthenticator : Authenticator {

    private var userName: String? = null
    private var password: String? = null

    constructor(username: String, password: String) {
        this.userName = username
        this.password = password
    }

    override fun getPasswordAuthentication(): PasswordAuthentication {
        return PasswordAuthentication(userName, password)
    }
}
