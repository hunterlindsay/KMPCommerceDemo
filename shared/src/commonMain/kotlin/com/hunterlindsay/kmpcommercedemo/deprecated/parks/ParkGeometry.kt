package com.hunterlindsay.kmpcommercedemo.concerns.deprecated.parks

/**
 * Created by Hunter Lindsay on 11/05/2026.
 */

sealed class ParkGeometry {
    class Polygon(
        val rings: List<List<Coordinate>>
    ) : ParkGeometry()

    class MultiPolygon(
        val polygons: List<List<List<Coordinate>>>
    ) : ParkGeometry()
}