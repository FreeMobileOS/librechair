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
 *     but WITHOUT ANY WARRANTY without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Lawnchair Launcher.  If not, see <https://www.gnu.org/licenses/>.
 */

package ch.deletescape.lawnchair.feed

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.database.CursorIndexOutOfBoundsException
import android.graphics.Color
import android.net.Uri
import android.provider.CalendarContract
import android.util.Log
import android.view.View
import android.view.ViewGroup
import ch.deletescape.lawnchair.*
import ch.deletescape.lawnchair.feed.tabs.TabController
import ch.deletescape.lawnchair.util.extensions.d
import com.android.launcher3.R
import com.google.android.apps.nexuslauncher.graphics.IcuDateTextView
import java.time.Instant
import java.util.*
import kotlin.collections.ArrayList

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class CalendarEventProvider(context: Context) : FeedProvider(context) {
    private val calendarDrawable by lazy {
        context.getDrawable(R.drawable.ic_event_black_24dp)!!.duplicateAndSetColour(
                if (useWhiteText(backgroundColor, context)) Color.WHITE else Color.DKGRAY)
    }
    private val calendarDrawableColoured by lazy {
        context.getDrawable(R.drawable.ic_event_black_24dp)!!
                .duplicateAndSetColour(context.getColorAttr(R.attr.colorAccent))
    }

    override fun onFeedShown() {
        // TODO
    }

    override fun onFeedHidden() {
        // TODO
    }

    override fun onCreate() {
        // TODO
    }

    override fun onDestroy() {
        // TODO
    }

    override fun getCards(): List<Card> {
        d("getCards: retrieving calendar cards...")
        if (context.checkSelfPermission(
                        android.Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            return emptyList()
        }
        val cards = ArrayList<Card>()
        run {
            val currentTime = GregorianCalendar()
            val endTime = GregorianCalendar()
            endTime.add(Calendar.DAY_OF_MONTH,
                        if (context.lawnchairPrefs.feedTabController == TabController::class.qualifiedName) 5 else 60)
            Log.v(javaClass.name,
                  "getCards: searching for events between " + currentTime + " and " + endTime.toString())
            val query =
                    "(( " + CalendarContract.Events.DTSTART + " >= " + currentTime.getTimeInMillis() + " ) AND ( " + CalendarContract.Events.DTSTART + " <= " + endTime.getTimeInMillis() + " ))"
            val eventCursorNullable: Cursor? = context.contentResolver
                    .query(CalendarContract.Events.CONTENT_URI,
                           arrayOf(CalendarContract.Instances.TITLE,
                                   CalendarContract.Instances.DTSTART,
                                   CalendarContract.Instances.DTEND,
                                   CalendarContract.Instances.DESCRIPTION,
                                   CalendarContract.Events._ID,
                                   CalendarContract.Instances.CUSTOM_APP_PACKAGE,
                                   CalendarContract.Events.EVENT_LOCATION,
                                   CalendarContract.Calendars.CALENDAR_COLOR), query, null,
                           CalendarContract.Instances.DTSTART + " ASC")
            if (eventCursorNullable == null) {
                Log.v(javaClass.name,
                      "getCards: query is null, probably since there are no events that meet the specified criteria")
                return emptyList()
            }
            try {
                val eventCursor = eventCursorNullable
                eventCursor.moveToFirst()
                while (!eventCursor.isAfterLast) {
                    val title = eventCursor.getString(0).take(25)
                    Log.v(javaClass.name, "getCards: query found event")
                    Log.v(javaClass.name, "getCards:     title: " + title)
                    val startTime = GregorianCalendar()
                    startTime.timeInMillis = eventCursor.getLong(1)
                    Log.v(javaClass.name, "getCards:     startTime: " + startTime)
                    val eventEndTime = GregorianCalendar()
                    eventEndTime.timeInMillis = eventCursor.getLong(2)
                    Log.v(javaClass.name, "getCards:     eventEndTime: " + eventEndTime)
                    val description = eventCursor.getString(3)
                    val diff = startTime.timeInMillis - currentTime.timeInMillis
                    Log.v(javaClass.name, "getCards: difference in milliseconds: " + diff)
                    val diffSeconds = diff / 1000
                    val diffMinutes = diff / (60 * 1000)
                    val diffHours = diff / (60 * 60 * 1000)
                    val diffDays = diff / (24 * 60 * 60 * 1000)
                    var text: String
                    if (diffDays > 20) {
                        text = IcuDateTextView.getDateFormat(context, true, null, false)
                                .format(Date.from(Instant.ofEpochMilli(startTime.timeInMillis)))
                    } else if (diffDays >= 1) {
                        text = if (diffDays < 1 || diffDays > 1) context.getString(
                                R.string.title_text_calendar_feed_provider_in_d_days,
                                diffDays) else context.getString(R.string.tomorrow)
                    } else if (diffHours > 4) {
                        text = context
                                .getString(R.string.title_text_calendar_feed_in_d_hours, diffHours)
                    } else {
                        text = if (diffMinutes <= 0) context.getString(
                                R.string.reusable_str_now) else context.getString(
                                if (diffMinutes < 1 || diffMinutes > 1) R.string.subtitle_smartspace_in_minutes else R.string.subtitle_smartspace_in_minute,
                                diffMinutes)
                    }
                    val intent = Intent(Intent.ACTION_VIEW)
                    if (eventCursor.getString(5) != null) {
                        if (context.packageManager.getApplicationEnabledSetting(
                                        eventCursor.getString(
                                                5)!!) != PackageManager.COMPONENT_ENABLED_STATE_DISABLED) {
                            intent.`package` = eventCursor.getString(5)!!
                        }
                    }
                    val address = eventCursor.getString(6)
                    intent.data = Uri
                            .parse("content://com.android.calendar/events/" + eventCursor.getLong(
                                    4).toString())
                    cards.add(Card(
                            if (context.lawnchairPrefs.feedShowCalendarColour) calendarDrawableColoured.duplicateAndSetColour(
                                    eventCursor.getInt(7).setAlpha(
                                            255)) else calendarDrawableColoured,
                            (if (title.trim().isEmpty()) context.getString(
                                    R.string.placeholder_empty_title) else "$title • ") + text,
                            object : Card.Companion.InflateHelper {
                                override fun inflate(parent: ViewGroup): View {
                                    return getCalendarFeedView(description, address, parent.context,
                                                               parent)
                                }
                            },
                            if (address?.isNotEmpty() != false || description?.isNotEmpty() != false) Card.RAISE else Card.RAISE or Card.TEXT_ONLY,
                            if (diffMinutes < 120) "nosort,top" else ""))
                    eventCursor.moveToNext()
                }
                eventCursor.close()
            } catch (e: Throwable) {
            }
        }
        run {
            val currentTime = GregorianCalendar()
            Log.v(javaClass.name,
                  "getCards: searching for events that are active at ${currentTime}")
            val query =
                    "(( " + CalendarContract.Events.DTSTART + " <= " + currentTime.getTimeInMillis() + " ) AND ( " + CalendarContract.Events.DTEND + " >= " + currentTime.getTimeInMillis() + " ))"
            val eventCursorNullable: Cursor? = context.contentResolver
                    .query(CalendarContract.Events.CONTENT_URI,
                           arrayOf(CalendarContract.Instances.TITLE,
                                   CalendarContract.Instances.DTSTART,
                                   CalendarContract.Instances.DTEND,
                                   CalendarContract.Instances.DESCRIPTION,
                                   CalendarContract.Instances.ALL_DAY, CalendarContract.Events._ID),
                           query, null, CalendarContract.Instances.DTSTART + " ASC")
            if (eventCursorNullable == null) {
                Log.v(javaClass.name,
                      "getCards: query is null, probably since there are no events that meet the specified criteria")
                return cards
            }
            try {
                val eventCursor = eventCursorNullable
                eventCursor.moveToFirst()
                while (!eventCursor.isAfterLast) {
                    val title = eventCursor.getString(0).take(25)
                    Log.v(javaClass.name, "getCards: query found event")
                    Log.v(javaClass.name, "getCards:     title: " + title)
                    val startTime = GregorianCalendar()
                    startTime.timeInMillis = eventCursor.getLong(1)
                    Log.v(javaClass.name, "getCards:     startTime: " + startTime)
                    val eventEndTime = GregorianCalendar()
                    eventEndTime.timeInMillis = eventCursor.getLong(2)
                    Log.v(javaClass.name, "getCards:     eventEndTime: " + eventEndTime)
                    val text = if (title == null || title.trim().isEmpty()) context.getString(
                            R.string.placeholder_empty_title) else "$title • ${context.getString(
                            if (eventCursor.getInt(
                                            4) != 0) R.string.reusable_string_all_day_event else R.string.ongoing)}"

                    eventCursor.moveToNext()
                    cards.add(Card(calendarDrawable, text, object : Card.Companion.InflateHelper {
                        override fun inflate(parent: ViewGroup): View {
                            return View(parent.context)
                        }
                    }, Card.TEXT_ONLY, "nosort,top"))
                }
                eventCursor.close()
            } catch (e: CursorIndexOutOfBoundsException) {
            }
        }
        return cards
    }
}