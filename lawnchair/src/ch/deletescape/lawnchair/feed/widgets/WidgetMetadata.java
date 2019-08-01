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

package ch.deletescape.lawnchair.feed.widgets;

import androidx.annotation.Nullable;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;

public class WidgetMetadata {

    public transient static Integer DEFAULT_HEIGHT = null;
    public transient static WidgetMetadata DEFAULT = new WidgetMetadata();

    @SerializedName("h")
    @Nullable
    public Integer height = DEFAULT_HEIGHT;

    @SerializedName("b")
    @NotNull
    public Boolean raiseCard = false;
}
