package com.hunterlindsay.kmpcommercedemo.kotlin.wiring

import com.hunterlindsay.kmpcommercedemo.core.app_wiring.KMPCommerceDemoDependencies
import kotlin.test.Test
import kotlin.test.assertNotNull

/**
 * Created by Hunter Lindsay on 12/05/2026.
 */

class KMPCommerceDemoDependenciesTest {
    @Test
    fun dependenciesBuildCoreServices() {
        val dependencies = KMPCommerceDemoDependencies()

        assertNotNull(dependencies.productService)
        assertNotNull(dependencies.cartService)
        assertNotNull(dependencies.savedProductService)
    }
}