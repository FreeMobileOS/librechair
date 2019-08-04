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
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Vibrator
import android.provider.Settings
import android.support.design.widget.Snackbar
import android.support.v4.graphics.ColorUtils
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import ch.deletescape.lawnchair.*
import ch.deletescape.lawnchair.feed.impl.Interpolators
import ch.deletescape.lawnchair.reflection.ReflectionUtils
import ch.deletescape.lawnchair.theme.ThemeManager
import ch.deletescape.lawnchair.util.extensions.d
import com.android.launcher3.R

class FeedAdapter(var providers: List<FeedProvider>, private val themeManager: ThemeManager,
                  backgroundColor: Int, private val context: Context) : RecyclerView.Adapter<CardViewHolder>() {

    private lateinit var recyclerView: RecyclerView

    var backgroundColor: Int = 0
        set(value) {
            d("init: backgroundColor is now ${value}")
            field = value
        }

    init {
        d("init: backgroundColor is ${backgroundColor}")
        this.backgroundColor = backgroundColor
    }

    private val cards = ArrayList<Card>()

    init {
        providers.forEach {
            it.onAttachedToAdapter(this)
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.addItemDecoration(Decoration(
            recyclerView.resources.getDimension(R.dimen.dimen_feed_card_padding).toInt()))
        this.recyclerView = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder(parent, viewType, backgroundColor)
    }

    override fun getItemViewType(position: Int): Int {
        return cards[position].type
    }

    fun refresh(): Int {
        cards.clear()
        val toSort: MutableList<List<Card>> = ArrayList()
        providers.iterator().forEach {
            toSort += it.cards
        }
        val algorithm = ReflectionUtils.inflateSortingAlgorithm(
            LawnchairPreferences.getInstanceNoCreate().feedPresenterAlgorithm)
        d("refresh: sorting algorithm is $algorithm")
        cards += algorithm.sort(* toSort.toTypedArray()).filter { !context.lawnchairPrefs.feedDisabledCards.contains(it.identifier) }
        return cards.size
    }

    @SuppressLint("MissingPermission")
    override fun getItemCount(): Int {
        if (cards.isEmpty()) {
            cards += Card(null, null, object : Card.Companion.InflateHelper {
                override fun inflate(parent: ViewGroup): View {
                    return LayoutInflater.from(ContextThemeWrapper(parent.context, if (useWhiteText(backgroundColor, parent.context)) R.style.SettingsTheme_V2_Dark else R.style.SettingsTheme_V2)).inflate(R.layout.empty_feed, parent, false)
                }

            }, Card.NO_HEADER, "nosort,top")
            if (Settings.Global.getInt(context.contentResolver,
                                       Settings.Global.AIRPLANE_MODE_ON,
                                       0) != 0) {
                cards += Card(R.drawable.ic_round_airplanemode_active_24px.fromDrawableRes(
                        context).duplicateAndSetColour((if (useWhiteText(backgroundColor,
                                                                         context)) R.color.qsb_background else R.color.qsb_background_dark).fromColorRes(
                        context)), R.string.title_card_airplane_mode_on.fromStringRes(context),
                              { _, _ ->
                                  View(context)
                              }, Card.TEXT_ONLY, "nosort,top",
                              "feedAirplaneModeIndicator".hashCode())
            }
            if ((context.getSystemService(
                            Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo?.isConnected != true) {
                cards += Card(R.drawable.ic_round_wifi_off_24dp.fromDrawableRes(context)
                                      .duplicateAndSetColour(
                                              (if (useWhiteText(backgroundColor, context))
                                                  R.color.qsb_background else R.color.qsb_background_dark)
                                                      .fromColorRes(context)),
                              R.string.title_card_network_disconnected.fromStringRes(context),
                              { _, _ ->
                                  View(context)
                              }, Card.TEXT_ONLY, "nosort,top",
                              "feedNetworkModeIndicator".hashCode())
            }
        }
        return cards.size;
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        providers.iterator().forEachRemaining {
            it.onDestroy()
        }
    }

    @SuppressLint("MissingPermission") override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        var isDeleteActive = false
        holder.itemView.animate().scaleX(1f).scaleY(1f)

        if (cards[holder.adapterPosition].canHide) {
            holder.itemView.setOnLongClickListener {
                isDeleteActive = true
                holder.itemView.animate().scaleX(0.7f).scaleY(0.7f).duration = 100
                LayoutInflater.from(holder.itemView.context)
                        .inflate(R.layout.card_remove_hint, holder.itemView as ViewGroup, false)
                        .apply {
                            background = ColorDrawable(Color.BLACK)
                            alpha = 0f
                            animate()?.alpha(.7f)?.duration = 500
                        }.also { (holder.itemView).addView(it) }
                true
            }

            holder.itemView.setOnTouchListener { view: View, motionEvent: MotionEvent ->
                if (isDeleteActive && motionEvent.action == MotionEvent.ACTION_UP) {
                    holder.itemView.animate().scaleX(0f).scaleY(0f).setInterpolator(Interpolators.ACCEL).duration = 500
                    (context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator).vibrate(20)
                    val backupCards = cards.clone() as List<Card>
                    holder.itemView.context.lawnchairPrefs.feedDisabledCards
                            .add(cards[holder.adapterPosition].identifier)
                    runOnNewThread {
                        cards.removeAt(holder.adapterPosition)
                        holder.itemView.post {
                            (holder.itemView as ViewGroup).removeView(
                                    holder.itemView.findViewById<View>(R.id.card_removal_hint));
                            notifyItemRemoved(holder.adapterPosition)
                            Snackbar.make(holder.itemView, R.string.item_removed, Snackbar.LENGTH_SHORT)
                                    .setAction(R.string.undo) {
                                        runOnNewThread {
                                            holder.itemView.context.lawnchairPrefs.feedDisabledCards
                                                    .remove(cards[holder.adapterPosition].identifier)
                                            cards.clear()
                                            cards.addAll(backupCards)
                                            holder.itemView.post {
                                                notifyItemInserted(holder.adapterPosition)
                                                recyclerView.scrollToPosition(holder.adapterPosition)
                                            }
                                        }
                                    }.show()
                        }
                    }
                    return@setOnTouchListener true
                } else if (isDeleteActive && motionEvent.action == MotionEvent.ACTION_CANCEL) {
                    isDeleteActive = false
                    holder.itemView.animate().scaleX(1f).scaleY(1f).duration = 100
                    (holder.itemView as ViewGroup)
                            .removeView(holder.itemView.findViewById<View>(R.id.card_removal_hint));
                }

                return@setOnTouchListener false
            }
        } else {
            holder.itemView.setOnLongClickListener(null)
            holder.itemView.setOnTouchListener(null)
        }
        if (holder.itemViewType and Card.NO_HEADER != 1) {
            holder.description?.text = cards[holder.adapterPosition].title
            holder.icon?.setImageDrawable(cards[holder.adapterPosition].icon)
        }
        holder.viewHolder.removeAllViewsInLayout()
        holder.viewHolder.addView(cards[holder.adapterPosition].inflateHelper.inflate(
                holder.viewHolder).also { (it.parent as ViewGroup?)?.removeView(it) })
        if (holder.itemViewType and Card.RAISE != 0 && ThemeManager.isDark(
                    themeManager.getCurrentFlags())) {
            (holder.itemView as CardView).setCardBackgroundColor(
                holder.itemView.context.getColor(R.color.qsb_background_dark))
        } else if (holder.itemViewType and Card.RAISE != 0) {
            (holder.itemView as CardView).setCardBackgroundColor(
                holder.itemView.context.getColor(R.color.qsb_background))
        }
        if (holder.itemView is CardView) {
            holder.itemView.radius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                                                               LawnchairPreferences.getInstance(
                                                                   holder.itemView.context).feedCornerRounding,
                                                               holder.itemView.context.resources.displayMetrics)
        }
    }

}

class CardViewHolder : RecyclerView.ViewHolder {
    val icon: ImageView? by lazy {
        itemView.findViewById(R.id.card_provider_small_icon) as ImageView?
    }
    val description: TextView? by lazy { itemView.findViewById(R.id.card_title) as TextView? }
    val viewHolder: LinearLayout by lazy {
        itemView.findViewById(R.id.card_view_holder) as LinearLayout
    }

    constructor(parent: ViewGroup, type: Int, backgroundColor: Int) : super(
        LayoutInflater.from(parent.context).inflate(when (type) {
                                                        Card.DEFAULT -> R.layout.card_default
                                                        Card.DEFAULT or Card.NARROW -> R.layout.card_narrow
                                                        Card.DEFAULT or Card.RAISE -> R.layout.card_raised
                                                        Card.DEFAULT or Card.RAISE or Card.NARROW -> R.layout.card_raised_narrow
                                                        Card.DEFAULT or Card.TEXT_ONLY -> R.layout.card_text_only
                                                        Card.DEFAULT or Card.RAISE or Card.TEXT_ONLY -> R.layout.card_raised_text_only
                                                        Card.DEFAULT or Card.NO_HEADER -> R.layout.card_default_no_header
                                                        Card.DEFAULT or Card.RAISE or Card.NO_HEADER -> R.layout.card_raised_no_header

                                                        else -> error("invalid bitmask")
                                                    }, parent, false)) {
        if (type and Card.TEXT_ONLY == 1) {
            viewHolder.visibility = View.GONE
        }

        d("constructor: luminace for background ${backgroundColor} is ${ColorUtils.calculateLuminance(
            backgroundColor)}")

        if (type and Card.RAISE == 0 && description != null && useWhiteText(backgroundColor, viewHolder.context)) {
            description!!.setTextColor(description!!.context.getColor(R.color.textColorPrimary))
        } else if (type and Card.RAISE == 0) {
            description?.setTextColor(
                    description?.context?.getColor(R.color.textColorPrimaryInverse) ?: 0)
        }
    }
}

private class Decoration(private val spaceHeight: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView,
                                state: RecyclerView.State) {
        with(outRect) {
            if (parent.getChildAdapterPosition(view) == 0) {
                top = spaceHeight
            }
            left = spaceHeight
            right = spaceHeight
            bottom = spaceHeight
        }
    }
}