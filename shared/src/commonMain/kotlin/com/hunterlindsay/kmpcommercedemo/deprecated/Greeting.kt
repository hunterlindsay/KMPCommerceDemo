package com.hunterlindsay.kmpcommercedemo.deprecated

import com.hunterlindsay.kmpcommercedemo.Platform
import com.hunterlindsay.kmpcommercedemo.getPlatform

class Greeting {
    private val platform: Platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}