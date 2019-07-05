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

package ch.deletescape.lawnchair.feed;

import android.content.Context;
import android.view.View;
import com.android.launcher3.R;
import java.util.Arrays;
import java.util.List;

public class FeedWeatherProvider extends FeedProvider {

    public FeedWeatherProvider(Context c) {
        super(c);
    }

    @Override
    public void onFeedShown() {
        // TODO
    }

    @Override
    public void onFeedHidden() {
        // TODO
    }

    @Override
    public void onCreate() {
        // TODO
    }

    @Override
    public void onDestroy() {
        // TODO
    }

    @Override
    public List<Card> getCards() {
        return Arrays.asList(new Card(getContext().getDrawable(R.drawable.weather_03), getContext().getString(R.string.title_card_weather_temperature), parent -> new View(getContext()), Card.Companion.getTEXT_ONLY()));
    }
}
