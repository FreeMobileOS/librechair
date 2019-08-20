/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.launcher3.shortcuts;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.LauncherApps;
import android.content.pm.LauncherApps.ShortcutQuery;
import android.content.pm.ShortcutInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;
import ch.deletescape.lawnchair.override.CustomInfoProvider;
import com.android.launcher3.ItemInfo;
import com.android.launcher3.LauncherSettings;
import com.android.launcher3.Utilities;
import com.android.launcher3.plugin.shortcuts.ShadeShortcutManager;
import com.android.launcher3.util.ComponentKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Performs operations related to deep shortcuts, such as querying for them, pinning them, etc.
 */
public class DeepShortcutManager {

    private static final String TAG = "DeepShortcutManager";

    private static final int FLAG_GET_ALL = ShortcutQuery.FLAG_MATCH_DYNAMIC
            | ShortcutQuery.FLAG_MATCH_MANIFEST | ShortcutQuery.FLAG_MATCH_PINNED;

    private static DeepShortcutManager sInstance;

    public static ShadeShortcutManager getShadeManager() {
        return sShadeManager;
    }

    private static ShadeShortcutManager sShadeManager;
    private static final Object sInstanceLock = new Object();

    public static DeepShortcutManager getInstance(Context context) {
        synchronized (sInstanceLock) {
            if (sInstance == null) {
                sInstance = new DeepShortcutManager(context);
                sShadeManager = new ShadeShortcutManager(context, sInstance);
            }
            return sInstance;
        }
    }

    private final Context mContext;
    private final LauncherApps mLauncherApps;
    private boolean mWasLastCallSuccess;

    protected DeepShortcutManager(Context context) {
        mContext = context;
        mLauncherApps = (LauncherApps) context.getSystemService(Context.LAUNCHER_APPS_SERVICE);
        if (Utilities.ATLEAST_MARSHMALLOW && !Utilities.ATLEAST_NOUGAT_MR1) {
            mWasLastCallSuccess = true;
        }
    }

    public static boolean supportsShortcuts(ItemInfo info) {
        boolean isItemPromise = info instanceof com.android.launcher3.ShortcutInfo
                && ((com.android.launcher3.ShortcutInfo) info).hasPromiseIconUi();
        return info.itemType == LauncherSettings.Favorites.ITEM_TYPE_APPLICATION
                && !info.isDisabled() && !isItemPromise;
    }

    public static boolean supportsEdit(ItemInfo info) {
        return CustomInfoProvider.Companion.isEditable(info) || supportsShortcuts(info);
    }

    public boolean wasLastCallSuccess() {
        return mWasLastCallSuccess;
    }

    public void onShortcutsChanged(List<ShortcutInfoCompat> shortcuts) {
        // mShortcutCache.removeShortcuts(shortcuts);
    }

    /**
     * Queries for the shortcuts with the package name and provided ids.
     *
     * This method is intended to get the full details for shortcuts when they are added or updated,
     * because we only get "key" fields in onShortcutsChanged().
     */
    public List<ShortcutInfoCompat> queryForFullDetails(String packageName,
            List<String> shortcutIds, UserHandle user) {
        return query(FLAG_GET_ALL, packageName, null, shortcutIds, user);
    }

    /**
     * Gets all the manifest and dynamic shortcuts associated with the given package and user, to be
     * displayed in the shortcuts container on long press.
     */
    public List<ShortcutInfoCompat> queryForShortcutsContainer(ComponentName activity,
            List<String> ids, UserHandle user) {
        List<ShortcutInfoCompat> local = query(
                ShortcutQuery.FLAG_MATCH_MANIFEST | ShortcutQuery.FLAG_MATCH_DYNAMIC,
                activity.getPackageName(),
                activity, ids, user);
        List<ShortcutInfoCompat> shadeCompat = sShadeManager
                .getAll(activity.getPackageName(), activity).stream()
                .filter(it -> ids.contains(it.getId())).collect(
                        Collectors.toList());
        Log.d(getClass().getName(),
                "queryForShortcutsContainer: ids are " + ids + ", local shortcuts are: " + local
                        + ", and shade shortcuts are: " + shadeCompat);
        if (local.isEmpty()) {
            return shadeCompat;
        } else {
            local.addAll(shadeCompat);
            return local;
        }
    }

    /**
     * Gets all the manifest and dynamic shortcuts associated with the given package and user, to be
     * displayed in the shortcuts container on long press.
     */
    public List<ShortcutInfoCompat> queryForShortcutsContainer(String packageName,
            List<String> ids, UserHandle user) {
        List<ShortcutInfoCompat> local = query(
                ShortcutQuery.FLAG_MATCH_MANIFEST | ShortcutQuery.FLAG_MATCH_DYNAMIC, packageName,
                null, ids, user);
        List<ShortcutInfoCompat> shadeCompat = sShadeManager.getAll(packageName, null).stream()
                .filter(it -> ids.contains(it.getId())).collect(
                        Collectors.toList());
        Log.d(getClass().getName(),
                "queryForShortcutsContainer: ids are " + ids + ", local shortcuts are: " + local
                        + ", and shade shortcuts are: " + shadeCompat);
        if (local.isEmpty()) {
            return shadeCompat;
        } else {
            local.addAll(shadeCompat);
            return local;
        }
    }


    /**
     * Removes the given shortcut from the current list of pinned shortcuts. (Runs on background
     * thread)
     */
    @TargetApi(25)
    public void unpinShortcut(final ShortcutKey key) {
        String packageName = key.componentName.getPackageName();
        String id = key.getId();
        UserHandle user = key.user;
        List<String> pinnedIds = extractIds(queryForPinnedShortcuts(packageName, user));
        pinnedIds.remove(id);
        try {
            mLauncherApps.pinShortcuts(packageName, pinnedIds, user);
            mWasLastCallSuccess = true;
        } catch (SecurityException | IllegalStateException e) {
            Log.w(TAG, "Failed to unpin shortcut", e);
            mWasLastCallSuccess = false;
        }
    }

    /**
     * Adds the given shortcut to the current list of pinned shortcuts. (Runs on background thread)
     */
    @TargetApi(25)
    public void pinShortcut(final ShortcutKey key) {
        String packageName = key.componentName.getPackageName();
        String id = key.getId();
        UserHandle user = key.user;
        List<String> pinnedIds = extractIds(queryForPinnedShortcuts(packageName, user));
        pinnedIds.add(id);
        try {
            mLauncherApps.pinShortcuts(packageName, pinnedIds, user);
            mWasLastCallSuccess = true;
        } catch (SecurityException | IllegalStateException e) {
            Log.w(TAG, "Failed to pin shortcut", e);
            mWasLastCallSuccess = false;
        }
    }

    @TargetApi(25)
    public void startShortcut(String packageName, String id, Intent intent,
            Bundle startActivityOptions, UserHandle user) {
        sInstance.startShortcut(packageName, id, intent, startActivityOptions, user);
    }

    @TargetApi(25)
    public void startShortcutReal(String packageName, String id, Intent intent,
            Bundle startActivityOptions, UserHandle user) {
        if (Utilities.ATLEAST_NOUGAT_MR1) {
            try {
                mLauncherApps.startShortcut(packageName, id, intent.getSourceBounds(),
                        startActivityOptions, user);
                mWasLastCallSuccess = true;
            } catch (SecurityException | IllegalStateException e) {
                Log.e(TAG, "Failed to start shortcut", e);
                mWasLastCallSuccess = false;
            }
        } else {
            mContext.startActivity(ShortcutInfoCompatBackport.stripPackage(intent),
                    startActivityOptions);
        }
    }

    @TargetApi(25)
    public Drawable getShortcutIconDrawable(ShortcutInfoCompat shortcutInfo, int density) {
        return sShadeManager.getShortcutIconDrawable(shortcutInfo, density);
    }

    @TargetApi(25)
    public Drawable getShortcutIconDrawableReal(ShortcutInfoCompat shortcutInfo, int density) {
        if (Utilities.ATLEAST_NOUGAT_MR1) {
            try {
                Drawable icon = mLauncherApps.getShortcutIconDrawable(
                        shortcutInfo.getShortcutInfo(), density);
                mWasLastCallSuccess = true;
                return icon;
            } catch (SecurityException | IllegalStateException e) {
                Log.e(TAG, "Failed to get shortcut iconView", e);
                mWasLastCallSuccess = false;
            }
        } else {
            return DeepShortcutManagerBackport.getShortcutIconDrawable(shortcutInfo, density);
        }
        return null;
    }

    /**
     * Returns the id's of pinned shortcuts associated with the given package and user.
     *
     * If packageName is null, returns all pinned shortcuts regardless of package.
     */
    public List<ShortcutInfoCompat> queryForPinnedShortcuts(String packageName, UserHandle user) {
        return queryForPinnedShortcuts(packageName, null, user);
    }

    public List<ShortcutInfoCompat> queryForPinnedShortcuts(String packageName,
            List<String> shortcutIds, UserHandle user) {
        return query(ShortcutQuery.FLAG_MATCH_PINNED, packageName, null, shortcutIds, user);
    }

    public List<ShortcutInfoCompat> queryForAllShortcuts(UserHandle user) {
        return query(FLAG_GET_ALL, null, null, null, user);
    }

    public List<ShortcutInfoCompat> queryForComponent(ComponentKey key) {
        return query(FLAG_GET_ALL, key.componentName.getPackageName(), key.componentName, null,
                key.user);
    }

    private List<String> extractIds(List<ShortcutInfoCompat> shortcuts) {
        List<String> shortcutIds = new ArrayList<>(shortcuts.size());
        for (ShortcutInfoCompat shortcut : shortcuts) {
            shortcutIds.add(shortcut.getId());
        }
        return shortcutIds;
    }

    /**
     * Query the system server for all the shortcuts matching the given parameters. If packageName
     * == null, we query for all shortcuts with the passed flags, regardless of app.
     *
     * TODO: Use the cache to optimize this so we don't make an RPC every time.
     */
    @TargetApi(25)
    public List<ShortcutInfoCompat> query(int flags, String packageName,
            ComponentName activity, List<String> shortcutIds, UserHandle user) {
        List<ShortcutInfoCompat> shortcutInfoCompats = new ArrayList<>();
        // LIBRE_CHANGED: Remove Sesame
        if (Utilities.ATLEAST_NOUGAT_MR1) {
            ShortcutQuery q = new ShortcutQuery();
            q.setQueryFlags(flags);
            if (packageName != null) {
                q.setPackage(packageName);
                q.setActivity(activity);
                q.setShortcutIds(shortcutIds);
            }
            List<ShortcutInfo> shortcutInfos = null;
            try {
                shortcutInfos = mLauncherApps.getShortcuts(q, user);
                mWasLastCallSuccess = true;
            } catch (SecurityException | IllegalStateException e) {
                Log.e(TAG, "Failed to query for shortcuts", e);
                mWasLastCallSuccess = false;
            }
            if (shortcutInfos == null) {
                return Collections.EMPTY_LIST;
            }
            for (ShortcutInfo shortcutInfo : shortcutInfos) {
                shortcutInfoCompats.add(new ShortcutInfoCompat(shortcutInfo));
            }
        } else {
        }
        return shortcutInfoCompats;
    }

    @TargetApi(25)
    public boolean hasHostPermission() {
        if (Utilities.ATLEAST_NOUGAT_MR1) {
            try {
                return mLauncherApps.hasShortcutHostPermission();
            } catch (SecurityException | IllegalStateException e) {
                Log.e(TAG, "Failed to make shortcut manager call", e);
            }
        } else {
            return true;
        }
        return false;
    }
}
