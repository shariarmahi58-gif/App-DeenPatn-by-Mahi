package com.example.utils

import java.util.Calendar
import java.util.Date
import kotlin.math.*

data class DailyPrayerTimes(
    val fajr: String,
    val sunrise: String,
    val dhuhr: String,
    val asr: String,
    val maghrib: String,
    val isha: String
)

object PrayerTimeCalculator {

    // Converts decimal hours to HH:MM format (e.g. 12.5 -> "12:30 PM")
    fun formatTime(decimalHours: Double, label: String = ""): String {
        if (decimalHours.isNaN() || decimalHours.isInfinite()) return "--:--"
        
        var totalMinutes = (decimalHours * 60.0).roundToInt()
        totalMinutes = (totalMinutes % 1440 + 1440) % 1440 // Clamp inside 24 hours
        
        val hours24 = totalMinutes / 60
        val minutes = totalMinutes % 60
        
        val period = if (hours24 >= 12) "PM" else "AM"
        val hours12 = if (hours24 % 12 == 0) 12 else hours24 % 12
        
        return String.format("%02d:%02d %s", hours12, minutes, period)
    }

    private fun dtr(deg: Double): Double = deg * PI / 180.0
    private fun rtd(rad: Double): Double = rad * 180.0 / PI

    private fun fixHour(hour: Double): Double {
        var h = hour
        while (h < 0) h += 24.0
        while (h >= 24) h -= 24.0
        return h
    }

    // Mathematical formula to calculate prayer times
    fun calculateTimes(date: Date, latitude: Double, longitude: Double, timezoneOffset: Double): DailyPrayerTimes {
        val calendar = Calendar.getInstance()
        calendar.time = date
        
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Step 1: Calculate Julian Date / Days since 2000
        // Approximate Julian Date calculation for simplicity and top performance
        val a = (14 - month) / 12
        val y = year + 4800 - a
        val m = month + 12 * a - 3
        val jd = day + (153 * m + 2) / 5 + 365 * y + y / 4 - y / 100 + y / 400 - 32045
        
        val d = jd - 2451545.0 // Century days

        // Step 2: Solar Parameters
        val g = 357.529 + 0.98560028 * d // Mean anomaly of Sun
        val q = 280.459 + 0.98564736 * d // Mean longitude of Sun
        val l = q + 1.915 * sin(dtr(g)) + 0.020 * sin(dtr(2 * g)) // Ecliptic longitude of Sun
        
        val ob = 23.439 - 0.00000036 * d // Obliquity of Ecliptic
        
        // Sun Declination
        val dec = rtd(asin(sin(dtr(ob)) * sin(dtr(l))))
        
        // Right Ascension
        var ra = rtd(atan2(cos(dtr(ob)) * sin(dtr(l)), cos(dtr(l))))
        ra = (ra + 360.0) % 360.0
        
        // Equation of Time (in hours)
        val eqt = q / 15.0 - ra / 15.0

        // Midday (Transit of Sun)
        val midDay = 12.0 + timezoneOffset - longitude / 15.0 - eqt

        // Hour angles for Sunrise/Sunset, Fajr, Isha
        // Standard sunrise/sunset is at angle h = 0.833 degrees below horizon (due to refraction & parallax)
        val sunriseAngle = 0.833
        val sunriseH = calculateHourAngle(sunriseAngle, latitude, dec)

        // Islam Foundation Bangladesh Fajr Angle is 18.0° below horizon
        val fajrAngle = 18.0
        val fajrH = calculateHourAngle(fajrAngle, latitude, dec)

        // Islam Foundation Bangladesh Isha Angle is 18.0° below horizon
        val ishaAngle = 18.0
        val ishaH = calculateHourAngle(ishaAngle, latitude, dec)

        // Asr Angle (Hanafi shadow factor = 2)
        val asrAngle = calculateAsrAngle(2, latitude, dec)
        val asrH = calculateHourAngle(asrAngle, latitude, dec, isPositive = true)

        // Hourly Calculation
        val sunriseDec = midDay - sunriseH / 15.0
        val sunsetDec = midDay + sunriseH / 15.0
        
        val fajrDec = midDay - fajrH / 15.0
        val dhuhrDec = midDay + (4.0 / 60.0) // Add 4 minutes post transit as safety buffer
        val asrDec = midDay + asrH / 15.0
        val maghribDec = sunsetDec + (3.0 / 60.0) // Add 3 minutes safety margin for Maghrib in BD
        val ishaDec = midDay + ishaH / 15.0 + (2.0 / 60.0) // Add 2 minutes safety margin for Isha

        return DailyPrayerTimes(
            fajr = formatTime(fixHour(fajrDec)),
            sunrise = formatTime(fixHour(sunriseDec)),
            dhuhr = formatTime(fixHour(dhuhrDec)),
            asr = formatTime(fixHour(asrDec)),
            maghrib = formatTime(fixHour(maghribDec)),
            isha = formatTime(fixHour(ishaDec))
        )
    }

    private fun calculateHourAngle(angle: Double, latitude: Double, declination: Double, isPositive: Boolean = false): Double {
        val radLat = dtr(latitude)
        val radDec = dtr(declination)
        val radAngle = dtr(angle)
        
        // cos(H) = (-sin(angle) - sin(Lat) * sin(Dec)) / (cos(Lat) * cos(Dec))
        val numerator = -sin(radAngle) - sin(radLat) * sin(radDec)
        val denominator = cos(radLat) * cos(radDec)
        
        val cosH = numerator / denominator
        
        // Clamp cosH to prevent NaN values
        val clampedCosH = cosH.coerceIn(-1.0, 1.0)
        
        val h = rtd(acos(clampedCosH))
        return if (isPositive) abs(h) else h
    }

    private fun calculateAsrAngle(shadowFactor: Int, latitude: Double, declination: Double): Double {
        val diff = abs(latitude - declination)
        val radDiff = dtr(diff)
        
        // cot(AsrAngle) = shadowFactor + tan(diff) -> AsrAngle = acot(shadowFactor + tan(diff))
        val valCot = shadowFactor + tan(radDiff)
        val angleRad = atan(1.0 / valCot)
        
        return rtd(angleRad)
    }

    // Calculates the bearing from current coordinates to the Kaaba (21.4225° N, 39.8262° E)
    fun calculateQiblaDirection(latitude: Double, longitude: Double): Double {
        val phiUser = dtr(latitude)
        val phiMecca = dtr(21.422478) // Latitude of Mecca
        val deltaLon = dtr(39.826206 - longitude) // Longitude of Mecca minus User Longitude

        val y = sin(deltaLon)
        val x = cos(phiUser) * tan(phiMecca) - sin(phiUser) * cos(deltaLon)
        
        val qiblaRad = atan2(y, x)
        val qiblaDeg = rtd(qiblaRad)
        
        return (qiblaDeg + 360.0) % 360.0
    }
}
