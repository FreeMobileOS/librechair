/*
 * Copyright (c) 2019 oldosfan.
 * Copyright (c) 2019 the Lawnchair developers
 *
 *     This file is part of Librechair.
 *
 *     Librechair is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Librechair is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Librechair.  If not, see <https://www.gnu.org/licenses/>.
 */

package ch.deletescape.lawnchair.feed.chips.predict;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.List;
import java.util.stream.Collectors;

import ch.deletescape.lawnchair.feed.chips.ChipProvider;
import ch.deletescape.lawnchair.feed.impl.OverlayService;

import static java.util.Collections.EMPTY_LIST;

public class PredictedAppsProvider extends ChipProvider {
    private final Context context;

    public PredictedAppsProvider(Context context) {
        this.context = context;
    }

    @Override
    public List<Item> getItems(Context context) {
        try {
            Log.d(getClass().getSimpleName(),
                    "getItems: items are " + OverlayService.CompanionService.InterfaceHolder.INSTANCE.getPredictions());
            return OverlayService.CompanionService.InterfaceHolder.INSTANCE.getPredictions().stream().map(
                    it -> {
                        try {
                            Item item = new Item();
                            item.icon = context.getPackageManager().getActivityIcon(
                                    it.getComponentKey().componentName);
                            item.title = context.getPackageManager().getActivityInfo(
                                    it.getComponentKey().componentName, 0).loadLabel(
                                    context.getPackageManager()).toString();
                            item.click = () -> context.startActivity(new Intent().setComponent(
                                    it.getComponentKey().componentName).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                            return item;
                        } catch (PackageManager.NameNotFoundException e) {
                            return null;
                        }
                    }).filter(it -> it != null).collect(Collectors.toList());
        } catch (Exception /* RemoteException */ e) {
            Log.e(getClass().getSimpleName(), "getItems: error retrieving predictions", e);
            return EMPTY_LIST;
        }
    }
}