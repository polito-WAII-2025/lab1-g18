package org.example

import kotlin.math.*

fun main() {
    val waypointList = mutableListOf<Waypoint>()

    val csv = readCsv("./RouteAnalyzer/src/main/resources/waypoints.csv")
    csv.forEach { row: List<String> ->
        waypointList.add(Waypoint(row[0].toDouble(), row[1].toDouble(), row[2].toDouble()))
    }
    val parameters = readYaml("./RouteAnalyzer/src/main/resources/custom-parameters.yml")

}


/**
 * Returns the waypoint with the highest altitude.
 * @param waypoints the list of waypoints
 * @return the farthest waypoint from the start and the maximum distance from the start
 */
fun maxDistanceFromStart(waypoints: List<Waypoint>, earthRadius: Double): Pair<Waypoint, Double> {
    val start = waypoints.first()
    var maxDistance = Double.MIN_VALUE
    var farthestWaypoints = waypoints.first()
    for (waypoint in waypoints.drop(1)) {
        val distance = haversine(start.latitude, start.longitude, waypoint.latitude, waypoint.latitude, earthRadius)
        if (distance > maxDistance) {
            maxDistance = distance
            farthestWaypoints = waypoint
        }
    }
    return Pair(farthestWaypoints, maxDistance)
}


fun mostFrequentedArea(areaRadiusKm: Double, waypointList: List<Waypoint>): Pair<Waypoint, Int> {
    TODO()
}


/**
 * Returns the waypoints that are outside a geofence.
 * @param waypoints the list of waypoints
 * @param geofenceCenterLatitude the latitude of the geofence center
 * @param geofenceCenterLongitude the longitude of the geofence center
 * @param geofenceRadiusKm the radius of the geofence in kilometers
 * @param earthRadius the radius of the Earth
 * @return a pair containing the waypoints outside the geofence and the number of waypoints outside the geofence
 */
fun waypointsOutsideGeofence(
    waypoints: List<Waypoint>,
    geofenceCenterLatitude: Double,
    geofenceCenterLongitude: Double,
    geofenceRadiusKm: Double,
    earthRadius: Double
): List<Waypoint> {
    val outsideGeofence = mutableListOf<Waypoint>()
    for (waypoint in waypoints) {
        val distance = haversine(
            geofenceCenterLatitude,
            geofenceCenterLongitude,
            waypoint.latitude,
            waypoint.longitude,
            earthRadius
        )
        if (distance > geofenceRadiusKm) {
            outsideGeofence.add(waypoint)
        }
    }
    return outsideGeofence
}


/**
 * Calculates the distance between two points on the Earth's surface using the haversine formula.
 * @param lat1 the latitude of the first point
 * @param lon1 the longitude of the first point
 * @param lat2 the latitude of the second point
 * @param lon2 the longitude of the second point
 * @param earthRadius the radius of the Earth
 * @return the distance between the two points
 */
fun haversine(lat1: Double, lon1: Double, lat2: Double, lon2: Double, earthRadius: Double): Double {
    val dLat = Math.toRadians(lat2 - lat1)
    val dLon = Math.toRadians(lon2 - lon1)
    val a = sin(dLat / 2).pow(2.0) + cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) * sin(dLon / 2).pow(2.0)
    val c = 2 * asin(sqrt(a))
    return earthRadius * c
}


