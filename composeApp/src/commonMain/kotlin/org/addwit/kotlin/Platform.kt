package org.addwit.kotlin

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform