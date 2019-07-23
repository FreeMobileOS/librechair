/*
 *     Copyright (c) 2017-2019 the Lawnchair team
 *     Copyright (c)  2019 oldosfan (would)
 *     This file is part of Lawnchair Launcher.
 *
 *     Lawnchair Launcher is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Lawnchair Launcher is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Lawnchair Launcher.  If not, see <https://www.gnu.org/licenses/>.
 */

package ch.deletescape.lawnchair.smartspace.weathercom

import ch.deletescape.lawnchair.smartspace.WeatherIconProvider
import com.android.launcher3.BuildConfig


object Constants {

    object WeatherComConstants {
        val WEATHER_COM_API_KEY = BuildConfig.WEATHER_CHANNEL_KEY
        val WEATHER_COM_FORECAST_KEY =  BuildConfig.WEATHER_CHANNEL_FORECAST_KEY
        private val WEATHER_ICONS = mapOf(0 to WeatherIconProvider.CONDITION_STORM,
                                          1 to WeatherIconProvider.CONDITION_STORM,
                                          2 to WeatherIconProvider.CONDITION_STORM,
                                          3 to WeatherIconProvider.CONDITION_STORM,
                                          4 to WeatherIconProvider.CONDITION_STORM,
                                          5 to WeatherIconProvider.CONDITION_SNOW,
                                          6 to WeatherIconProvider.CONDITION_SNOW,
                                          7 to WeatherIconProvider.CONDITION_SNOW,
                                          8 to WeatherIconProvider.CONDITION_SHOWERS,
                                          9 to WeatherIconProvider.CONDITION_SHOWERS,
                                          10 to WeatherIconProvider.CONDITION_RAIN,
                                          11 to WeatherIconProvider.CONDITION_RAIN,
                                          12 to WeatherIconProvider.CONDITION_RAIN,
                                          13 to WeatherIconProvider.CONDITION_SNOW,
                                          14 to WeatherIconProvider.CONDITION_SNOW,
                                          15 to WeatherIconProvider.CONDITION_SNOW,
                                          16 to WeatherIconProvider.CONDITION_SNOW,
                                          17 to WeatherIconProvider.CONDITION_SNOW,
                                          18 to WeatherIconProvider.CONDITION_SNOW,
                                          19 to WeatherIconProvider.CONDITION_MIST,
                                          21 to WeatherIconProvider.CONDITION_MIST,
                                          22 to WeatherIconProvider.CONDITION_MIST,
                                          23 to WeatherIconProvider.CONDITION_CLOUDS,
                                          24 to WeatherIconProvider.CONDITION_CLOUDS,
                                          25 to WeatherIconProvider.CONDITION_CLOUDS,
                                          23 to WeatherIconProvider.CONDITION_CLOUDS,
                                          24 to WeatherIconProvider.CONDITION_CLOUDS,
                                          25 to WeatherIconProvider.CONDITION_CLOUDS,
                                          26 to WeatherIconProvider.CONDITION_MOST_CLOUDS,
                                          27 to WeatherIconProvider.CONDITION_MOST_CLOUDS,
                                          28 to WeatherIconProvider.CONDITION_CLOUDS,
                                          29 to WeatherIconProvider.CONDITION_CLOUDS,
                                          30 to WeatherIconProvider.CONDITION_CLOUDS,
                                          31 to WeatherIconProvider.CONDITION_CLEAR,
                                          32 to WeatherIconProvider.CONDITION_CLEAR,
                                          33 to WeatherIconProvider.CONDITION_MOST_CLOUDS,
                                          34 to WeatherIconProvider.CONDITION_MOST_CLOUDS,
                                          35 to WeatherIconProvider.CONDITION_RAIN,
                                          36 to WeatherIconProvider.CONDITION_CLEAR,
                                          37 to WeatherIconProvider.CONDITION_STORM,
                                          38 to WeatherIconProvider.CONDITION_STORM,
                                          39 to WeatherIconProvider.CONDITION_SHOWERS,
                                          40 to WeatherIconProvider.CONDITION_RAIN,
                                          41 to WeatherIconProvider.CONDITION_RAIN,
                                          42 to WeatherIconProvider.CONDITION_SNOW,
                                          43 to WeatherIconProvider.CONDITION_SNOW,
                                          44 to WeatherIconProvider.CONDITION_UNKNOWN,
                                          45 to WeatherIconProvider.CONDITION_SHOWERS,
                                          46 to WeatherIconProvider.CONDITION_SNOW,
                                          47 to WeatherIconProvider.CONDITION_STORM)
        val WEATHER_ICONS_DAY = WEATHER_ICONS.map { it.key to it.value + "d" }
        val WEATHER_ICONS_NIGHT = WEATHER_ICONS.map { it.key to it.value + "n" }
        val WEATHER_COND_MAP =
                mapOf(0 to 210, 1 to 210, 2 to 210, 3 to 210, 4 to 210, 5 to 602, 6 to 602,
                      7 to 602, 8 to 303, 9 to 303, 10 to 602, 11 to 602, 12 to 602, 13 to 602,
                      14 to 602, 15 to 602, 16 to 602, 17 to 602, 18 to 602, 19 to 701, 21 to 701,
                      22 to 701, 23 to 808, 24 to 808, 25 to 808, 23 to 808, 24 to 808, 25 to 808,
                      26 to 803, 27 to 803, 28 to 808, 29 to 808, 30 to 808, 31 to 800, 32 to 800,
                      33 to 803, 34 to 803, 35 to 602, 36 to 800, 37 to 210, 38 to 210, 39 to 303,
                      40 to 602, 41 to 602, 42 to 602, 43 to 602, 44 to 0, 45 to 303, 46 to 602,
                      47 to 210)
    }
}
