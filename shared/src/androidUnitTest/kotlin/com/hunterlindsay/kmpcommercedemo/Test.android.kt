package com.hunterlindsay.kmpcommercedemo

import com.hunterlindsay.kmpcommercedemo.deprecated.Greeting
import org.junit.Assert.assertTrue
import org.junit.Test

class AndroidGreetingTest {

    @Test
    fun testExample() {
        assertTrue("Check Android is mentioned", Greeting().greet().contains("Android"))
    }
}