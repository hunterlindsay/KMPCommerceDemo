package com.hunterlindsay.kmpcommercedemo.concerns.deprecated.parks

/**
 * Created by Hunter Lindsay on 11/05/2026.
 */

class Coordinate(val latitude: Double, val longitude: Double) {
     fun toPair(): Pair<Double, Double> {
        return Pair(latitude, longitude)
    }
}