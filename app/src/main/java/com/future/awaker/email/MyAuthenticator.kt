package com.future.awaker.email

import javax.mail.Authenticator
import javax.mail.PasswordAuthentication


class MyAuthenticator : Authenticator {

    internal var userName: String? = null
    internal var password: String? = null

    constructor() {}

    constructor(username: String, password: String) {
        this.userName = username
        this.password = password
    }

    override fun getPasswordAuthentication(): PasswordAuthentication {
        return PasswordAuthentication(userName, password)
    }
}
