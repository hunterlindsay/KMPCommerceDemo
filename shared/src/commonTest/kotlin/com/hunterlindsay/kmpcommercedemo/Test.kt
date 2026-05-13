package com.hunterlindsay.kmpcommercedemo

import com.hunterlindsay.kmpcommercedemo.deprecated.Greeting
import kotlin.test.Test
import kotlin.test.assertTrue

class CommonGreetingTest {

    @Test
    fun testExample() {
        assertTrue(Greeting().greet().contains("Hello"), "Check 'Hello' is mentioned")
    }
}