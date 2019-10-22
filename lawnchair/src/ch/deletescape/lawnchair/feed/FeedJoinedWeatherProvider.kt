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

@file:Suppress("NestedLambdaShadowedImplicitParameter")

package ch.deletescape.lawnchair.feed

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.ViewTreeObserver
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.StringRes
import ch.deletescape.lawnchair.*
import ch.deletescape.lawnchair.awareness.WeatherManager
import ch.deletescape.lawnchair.smartspace.weather.forecast.ForecastProvider
import com.android.launcher3.R
import com.google.android.apps.nexuslauncher.graphics.IcuDateTextView
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*
import kotlin.math.roundToInt

class FeedJoinedWeatherProvider(c: Context) : FeedProvider(c) {
    private var weatherData: ForecastProvider.CurrentWeather? = null
    private var forecastHigh: Int? = null
    private var forecastLow: Int? = null
    private var hourlyWeatherForecast: ForecastProvider.Forecast? = null
    private var dailyForecast: ForecastProvider.DailyForecast? = null
    @StringRes
    private var weatherTypeResource: Int? = null

    init {
        WeatherManager.subscribeWeather {
            weatherData = it
        }
        WeatherManager.subscribeHourly {
            hourlyWeatherForecast = it
            val today: List<Int> = it.data.filter {
                it.date.before(tomorrow())
            }.map { it.data.temperature.inUnit(context.lawnchairPrefs.weatherUnit) }
            forecastLow = Collections.min(today)
            forecastHigh = Collections.max(today)
            val condCodes = run {
                val list = newList<Int>()
                hourlyWeatherForecast!!.data.filter { it.date.before(tomorrow()) }
                        .forEach { list += it.condCode?.toList() ?: listOf(1) }
                list
            }
            val (clear, clouds, rain, snow, thunder) = WeatherTypes.getStatistics(
                    condCodes.toTypedArray())
            val type = WeatherTypes
                    .getWeatherTypeFromStatistics(clear, clouds, rain, snow, thunder)
            weatherTypeResource = WeatherTypes.getStringResource(type)
        }
        WeatherManager.subscribeDaily {
            dailyForecast = it
        }
    }

    override fun onFeedShown() {
    }

    override fun onFeedHidden() {
    }

    override fun onCreate() {
    }

    override fun onDestroy() {
    }

    override fun getCards(): List<Card> {
        return if (weatherData != null) listOf(
                Card(null, null, object : Card.Companion.InflateHelper {
                    @SuppressLint("SetTextI18n")
                    override fun inflate(parent: ViewGroup): View {
                        val v = LayoutInflater.from(parent.context)
                                .inflate(R.layout.unified_weather, parent, false)
                        val highLow = v.findViewById(R.id.weather_hud_day_night) as TextView
                        val information = v.findViewById(R.id.weather_hud_information) as TextView
                        val currentInformation =
                                v.findViewById(R.id.weather_hud_current_temp) as TextView
                        val currentIcon = v.findViewById(R.id.weather_hud_icon) as ImageView
                        val hourlyLayout =
                                v.findViewById(R.id.unified_weather_forecast) as LinearLayout
                        val dailyLayout = v.findViewById(R.id.unified_weather_daily) as LinearLayout

                        if (context.lawnchairPrefs.showVerticalDailyForecast) {
                            dailyLayout.orientation = LinearLayout.VERTICAL
                        }
                        if (context.lawnchairPrefs.showVerticalHourlyForecast) {
                            hourlyLayout.orientation = LinearLayout.VERTICAL
                        }
                        (hourlyLayout.parent as View).apply {
                            setOnTouchListener { _, _ ->
                                controllerView?.disallowInterceptCurrentTouchEvent = true
                                false
                            }
                        }
                        (dailyLayout.parent as View).apply {
                            setOnTouchListener { _, _ ->
                                controllerView?.disallowInterceptCurrentTouchEvent = true
                                false
                            }
                        }
                        (hourlyLayout.parent as HorizontalScrollView).isHorizontalScrollBarEnabled =
                                false
                        (dailyLayout.parent as HorizontalScrollView).isHorizontalScrollBarEnabled =
                                false
                        hourlyWeatherForecast?.data
                                ?.forEach {
                                    hourlyLayout.addView(
                                            LayoutInflater.from(hourlyLayout.context).inflate(
                                                    if (!context.lawnchairPrefs.showVerticalHourlyForecast) R.layout.narrow_forecast_item else R.layout.straight_forecast_item,
                                                    parent,
                                                    false).apply {
                                                val temperature = findViewById<TextView>(R.id.forecast_current_temperature)
                                                val time = findViewById<TextView>(R.id.forecast_current_time)
                                                val icon = findViewById<ImageView>(R.id.forecast_weather_icon)

                                                icon.setImageBitmap(it.data.icon)
                                                val zonedDateTime = ZonedDateTime
                                                        .ofInstant(it.date.toInstant(),
                                                                ZoneId.of("UTC"))
                                                        .withZoneSameInstant(ZoneId.systemDefault())
                                                time.text = formatTime(zonedDateTime, context)
                                                temperature.text = it.data.temperature.toString(
                                                        context.lawnchairPrefs.weatherUnit)

                                                if (useWhiteText(backgroundColor,
                                                                context) && !context.lawnchairPrefs.elevateWeatherCard) {
                                                    time.setTextColor(Color.WHITE)
                                                    temperature.setTextColor(Color.WHITE)
                                                }
                                                layoutParams = LinearLayout
                                                        .LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
                                                        .apply {
                                                            weight = 1f
                                                        }
                                            })
                                }

                        (dailyLayout.parent as ViewGroup).apply {
                            viewTreeObserver.addOnPreDrawListener(object :
                                    ViewTreeObserver.OnPreDrawListener {
                                override fun onPreDraw(): Boolean {
                                    val w = v.measuredWidth
                                    dailyLayout.childs.forEach {
                                        it.apply {
                                            viewTreeObserver.addOnGlobalLayoutListener {
                                                if (context.lawnchairPrefs.showVerticalDailyForecast) {
                                                    layoutParams = LinearLayout.LayoutParams(
                                                            w,
                                                            WRAP_CONTENT)
                                                } else {
                                                    val width =
                                                            (w / context.lawnchairPrefs.feedDailyForecastItemCount.roundToInt())
                                                    layoutParams.width = width
                                                }
                                            }
                                        }
                                    }
                                    viewTreeObserver.removeOnPreDrawListener(this)
                                    return true
                                }
                            })
                        }
                        (hourlyLayout.parent as ViewGroup).apply {
                            viewTreeObserver.addOnPreDrawListener(object :
                                    ViewTreeObserver.OnPreDrawListener {
                                override fun onPreDraw(): Boolean {
                                    val w = v.measuredWidth
                                    hourlyLayout.childs.forEach {
                                        it.apply {
                                            viewTreeObserver.addOnGlobalLayoutListener {
                                                if (context.lawnchairPrefs.showVerticalHourlyForecast) {
                                                    layoutParams = LinearLayout.LayoutParams(
                                                            w,
                                                            WRAP_CONTENT)
                                                } else {
                                                    val width =
                                                            (w / context.lawnchairPrefs.feedForecastItemCount.roundToInt())
                                                    layoutParams.width = width
                                                }
                                            }
                                        }
                                    }
                                    viewTreeObserver.removeOnPreDrawListener(this)
                                    return true
                                }
                            })
                        }
                        dailyForecast?.dailyForecastData
                                ?.forEach {
                                    dailyLayout.addView(
                                            LayoutInflater.from(hourlyLayout.context).inflate(
                                                    if (context.lawnchairPrefs.showVerticalDailyForecast) R.layout.straight_forecast_item else R.layout.narrow_forecast_item,
                                                    dailyLayout, false).apply {
                                                val temperature = findViewById<TextView>(R.id.forecast_current_temperature)
                                                val time = findViewById<TextView>(R.id.forecast_current_time)
                                                val icon = findViewById<ImageView>(R.id.forecast_weather_icon)

                                                icon.setImageBitmap(it.icon)
                                                val zonedDateTime = ZonedDateTime
                                                        .ofInstant(it.date.toInstant(),
                                                                ZoneId.of("UTC"))
                                                        .withZoneSameInstant(ZoneId.systemDefault())

                                                val tomorrowDate = tomorrowL()
                                                val nextDayAfterTomorrow = tomorrowL(tomorrowL())

                                                if (context.lawnchairPrefs.showVerticalDailyForecast) {
                                                    if (zonedDateTime.toInstant().toEpochMilli() in tomorrowDate until nextDayAfterTomorrow) {
                                                        time.text = context.getString(
                                                                R.string.title_weather_item_tomorrow)
                                                    } else {
                                                        time.text =
                                                                IcuDateTextView.getDateFormat(
                                                                        context,
                                                                        false, null, false)
                                                                        .format(Date.from(
                                                                                Instant.ofEpochSecond(
                                                                                        zonedDateTime.toEpochSecond())))
                                                    }

                                                } else {
                                                    time.text =
                                                            "${zonedDateTime.month.value} / ${zonedDateTime.dayOfMonth}"
                                                }
                                                temperature.text = "${it.low.toString(
                                                        context.lawnchairPrefs.weatherUnit)} / ${it.high.toString(
                                                        context.lawnchairPrefs.weatherUnit)}"

                                                if (useWhiteText(backgroundColor,
                                                                context) && !context.lawnchairPrefs.elevateWeatherCard) {
                                                    time.setTextColor(Color.WHITE)
                                                    temperature.setTextColor(Color.WHITE)
                                                }
                                                layoutParams = LinearLayout
                                                        .LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
                                                        .apply {
                                                            weight = 1f
                                                        }
                                            })
                                }
                        currentInformation.text =
                                weatherData?.temperature?.toString(
                                        context.lawnchairPrefs.weatherUnit)
                        currentIcon.setImageBitmap(weatherData?.icon)
                        if (forecastHigh != null && forecastLow != null) {
                            highLow.text =
                                    "${forecastHigh}${context.lawnchairPrefs.weatherUnit.suffix} / ${forecastLow}${context.lawnchairPrefs.weatherUnit.suffix}"
                        }
                        information.text = weatherTypeResource?.let { context.getString(it) }
                        if (useWhiteText(backgroundColor, parent.context)) {
                            highLow.setTextColor(
                                    context.getColor(R.color.textColorPrimary))
                            information.setTextColor(
                                    context.getColor(R.color.textColorPrimary))
                            currentInformation.setTextColor(
                                    context.getColor(R.color.textColorPrimary))
                        }
                        return v
                    }
                },
                        Card.NO_HEADER or if (context.lawnchairPrefs.elevateWeatherCard) Card.RAISE else Card.DEFAULT,
                        "nosort,top"))
        else mutableListOf()
    }

    override fun isVolatile() = true
}