package com.hunterlindsay.kmpcommercedemo

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform