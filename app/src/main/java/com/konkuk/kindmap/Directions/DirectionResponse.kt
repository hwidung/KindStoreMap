package com.konkuk.kindmap.Directions

data class DirectionsResponse(
    val route: List<Route>?
)

data class Route(
    val traoptimal: List<Traoptimal>?
)

data class Traoptimal(
    val path: List<List<Double>>?
)
