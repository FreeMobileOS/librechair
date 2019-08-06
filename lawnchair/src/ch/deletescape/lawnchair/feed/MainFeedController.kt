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

package ch.deletescape.lawnchair.feed

import android.annotation.SuppressLint
import android.content.Context
import ch.deletescape.lawnchair.LawnchairApp
import ch.deletescape.lawnchair.feed.widgets.FeedWidgetsProvider
import ch.deletescape.lawnchair.fromStringRes
import ch.deletescape.lawnchair.lawnchairPrefs
import ch.deletescape.lawnchair.newList
import ch.deletescape.lawnchair.reflection.ReflectionUtils
import ch.deletescape.lawnchair.util.extensions.d
import com.android.launcher3.R

@SuppressLint("StaticFieldLeak")
private var theController: MainFeedController? = null

fun getFeedController(c: Context): MainFeedController {
    if (theController == null) {
        theController = MainFeedController(c)
    }
    return theController!!;
}

class MainFeedController(val context: Context) {

    fun getProviders(): List<FeedProvider> {
        val providers = newList<FeedProvider>()
        (context.applicationContext as LawnchairApp).lawnchairPrefs.feedProviders.toList()
                .iterator().forEach {
                    providers.add(
                        ReflectionUtils.inflateFeedProvider(it, context))
                }
        return providers
    }

    companion object {
        fun getDisplayName(provider: String, context: Context): String {
            return when (provider) {
                CalendarEventProvider::class.java.name -> context.getString(
                    R.string.title_feed_provider_calendar)
                FeedWeatherProvider::class.java.name -> context.getString(
                    R.string.title_feed_provider_weather)
                FeedWeatherStatsProvider::class.java.name -> context.getString(
                                    R.string.title_feed_provider_weather_stats)
                FeedForecastProvider::class.java.name -> context.getString(
                    R.string.title_feed_provider_forecast)
                FeedDailyForecastProvider::class.java.name -> context.getString(
                                    R.string.title_feed_provider_daily_forecast)
                RemoteFeedProvider::class.java.name -> context.getString(
                        R.string.title_feed_provider_remote_feeds)
                WikipediaNewsProvider::class.java.name -> context.getString(
                    R.string.title_feed_provider_wikipedia_news)
                WikipediaFunFactsProvider::class.java.name -> context.getString(
                    R.string.title_feed_provider_wikipedia_fun_facts)
                WikinewsFeedProvider::class.java.name -> context.getString(
                    R.string.title_feed_provider_wikinews)
                TheGuardianFeedProvider::class.java.name -> context.getString(
                    R.string.title_feed_provider_the_guardian)
                BBCFeedProvider::class.java.name -> context.getString(
                                    R.string.title_feed_provider_bbc)
                GSyndicationFeedProvider::class.java.name -> context.getString(
                    R.string.title_feed_provider_google_news)
                CustomizableRSSProvider::class.java.name -> context.getString(
                                    R.string.title_feed_provider_customizable_rss)
                DeviceStateProvider::class.java.name -> R.string.title_feed_provider_device_state.fromStringRes(
                        context)
                FeedWidgetsProvider::class.java.name -> context.getString(
                        R.string.title_feed_provider_widgets)
                DailySummaryFeedProvider::class.java.name -> R.string.title_feed_provider_daily_summary.fromStringRes(
                        context)
                PredictedAppsProvider::class.java.name -> R.string.title_card_suggested_apps.fromStringRes(
                        context)

                else -> error("No such provider ${provider}")
            }
        }

        fun getFeedProviders(): List<String> {
            return listOf(CalendarEventProvider::class.java.name,
                          FeedWeatherProvider::class.java.name,
                          FeedWeatherStatsProvider::class.java.name,
                          FeedDailyForecastProvider::class.java.name,
                          FeedForecastProvider::class.java.name,
                          RemoteFeedProvider::class.java.name,
                          WikipediaNewsProvider::class.java.name,
                          WikipediaFunFactsProvider::class.java.name,
                          WikinewsFeedProvider::class.java.name,
                          TheGuardianFeedProvider::class.java.name,
                          BBCFeedProvider::class.java.name,
                          CustomizableRSSProvider::class.java.name,
                          GSyndicationFeedProvider::class.java.name,
                          DeviceStateProvider::class.java.name,
                          FeedWidgetsProvider::class.java.name,
                          DailySummaryFeedProvider::class.java.name,
                          PredictedAppsProvider::class.java.name)
                    .also { d("getFeedProviders: feed providers are $it ") }
        }
    }
}