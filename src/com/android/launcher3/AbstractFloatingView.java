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

package com.android.launcher3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.IntDef;

import com.android.launcher3.userevent.nano.LauncherLogProto.Action;
import com.android.launcher3.util.TouchController;
import com.android.launcher3.views.ActivityContext;
import com.android.launcher3.views.BaseDragLayer;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static android.view.accessibility.AccessibilityEvent.TYPE_VIEW_FOCUSED;
import static android.view.accessibility.AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED;
import static android.view.accessibility.AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED;
import static com.android.launcher3.compat.AccessibilityManagerCompat.isAccessibilityEnabled;
import static com.android.launcher3.compat.AccessibilityManagerCompat.sendCustomAccessibilityEvent;

/**
 * Base class for a View which shows a floating UI on top of the launcher UI.
 */
public abstract class AbstractFloatingView extends LinearLayout implements TouchController {

    @IntDef(flag = true, value = {
            TYPE_FOLDER,
            TYPE_ACTION_POPUP,
            TYPE_WIDGETS_BOTTOM_SHEET,
            TYPE_WIDGET_RESIZE_FRAME,
            TYPE_WIDGETS_FULL_SHEET,
            TYPE_ON_BOARD_POPUP,
            TYPE_DISCOVERY_BOUNCE,
            TYPE_SNACKBAR,

            TYPE_QUICKSTEP_PREVIEW,
            TYPE_TASK_MENU,
            TYPE_OPTIONS_POPUP,
            TYPE_SETTINGS_SHEET
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface FloatingViewType {}
    public static final int TYPE_FOLDER = 1 << 0;
    public static final int TYPE_ACTION_POPUP = 1 << 1;
    public static final int TYPE_WIDGETS_BOTTOM_SHEET = 1 << 2;
    public static final int TYPE_WIDGET_RESIZE_FRAME = 1 << 3;
    public static final int TYPE_WIDGETS_FULL_SHEET = 1 << 4;
    public static final int TYPE_ON_BOARD_POPUP = 1 << 5;
    public static final int TYPE_DISCOVERY_BOUNCE = 1 << 6;
    public static final int TYPE_SNACKBAR = 1 << 7;

    // Popups related to quickstep UI
    public static final int TYPE_QUICKSTEP_PREVIEW = 1 << 8;
    public static final int TYPE_TASK_MENU = 1 << 9;
    public static final int TYPE_OPTIONS_POPUP = 1 << 10;

    // Custom popups
    public static final int TYPE_SETTINGS_SHEET = 1 << 11;

    public static final int TYPE_ALL = TYPE_FOLDER | TYPE_ACTION_POPUP
            | TYPE_WIDGETS_BOTTOM_SHEET | TYPE_WIDGET_RESIZE_FRAME | TYPE_WIDGETS_FULL_SHEET
            | TYPE_QUICKSTEP_PREVIEW | TYPE_ON_BOARD_POPUP | TYPE_DISCOVERY_BOUNCE | TYPE_TASK_MENU
            | TYPE_OPTIONS_POPUP | TYPE_SNACKBAR | TYPE_SETTINGS_SHEET;

    // Type of popups which should be kept open during launcher rebind
    public static final int TYPE_REBIND_SAFE = TYPE_WIDGETS_FULL_SHEET
            | TYPE_QUICKSTEP_PREVIEW | TYPE_ON_BOARD_POPUP | TYPE_DISCOVERY_BOUNCE;

    // Usually we show the back button when a floating view is open. Instead, hide for these types.
    public static final int TYPE_HIDE_BACK_BUTTON = TYPE_ON_BOARD_POPUP | TYPE_DISCOVERY_BOUNCE
            | TYPE_SNACKBAR;

    public static final int TYPE_ACCESSIBLE = TYPE_ALL
            & ~TYPE_DISCOVERY_BOUNCE & ~TYPE_QUICKSTEP_PREVIEW;

    // These view all have particular operation associated with swipe down interaction.
    public static final int TYPE_STATUS_BAR_SWIPE_DOWN_DISALLOW = TYPE_WIDGETS_BOTTOM_SHEET |
            TYPE_WIDGETS_FULL_SHEET | TYPE_WIDGET_RESIZE_FRAME | TYPE_ON_BOARD_POPUP |
            TYPE_DISCOVERY_BOUNCE | TYPE_TASK_MENU ;

    protected boolean mIsOpen;

    public AbstractFloatingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AbstractFloatingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * We need to handle touch events to prevent them from falling through to the workspace below.
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return true;
    }

    public final void close(boolean animate) {
        animate &= !Utilities.isPowerSaverPreventingAnimation(getContext());
        if (mIsOpen) {
            BaseActivity.fromContext(getContext()).getUserEventDispatcher()
                    .resetElapsedContainerMillis("container closed");
        }
        handleClose(animate);
        mIsOpen = false;
    }

    protected abstract void handleClose(boolean animate);

    public abstract void logActionCommand(int command);

    public final boolean isOpen() {
        return mIsOpen;
    }

    protected void onWidgetsBound() {
    }

    protected abstract boolean isOfType(@FloatingViewType int type);

    /** @return Whether the back is consumed. If false, Launcher will handle the back as well. */
    public boolean onBackPressed() {
        logActionCommand(Action.Command.BACK);
        close(true);
        return true;
    }

    @Override
    public boolean onControllerTouchEvent(MotionEvent ev) {
        return false;
    }

    protected void announceAccessibilityChanges() {
        Pair<View, String> targetInfo = getAccessibilityTarget();
        if (targetInfo == null || !isAccessibilityEnabled(getContext())) {
            return;
        }
        sendCustomAccessibilityEvent(
                targetInfo.first, TYPE_WINDOW_STATE_CHANGED, targetInfo.second);

        if (mIsOpen) {
            sendAccessibilityEvent(TYPE_VIEW_FOCUSED);
        }
        ActivityContext.lookupContext(getContext()).getDragLayer()
                .sendAccessibilityEvent(TYPE_WINDOW_CONTENT_CHANGED);
    }

    protected Pair<View, String> getAccessibilityTarget() {
        return null;
    }

    protected static <T extends AbstractFloatingView> T getOpenView(
            ActivityContext activity, @FloatingViewType int type) {
        BaseDragLayer dragLayer = activity.getDragLayer();
        // Iterate in reverse order. AbstractFloatingView is added later to the dragLayer,
        // and will be one of the last views.
        if (dragLayer != null) {
            for (int i = dragLayer.getChildCount() - 1; i >= 0; i--) {
                View child = dragLayer.getChildAt(i);
                if (child instanceof AbstractFloatingView) {
                    AbstractFloatingView view = (AbstractFloatingView) child;
                    if (view.isOfType(type) && view.isOpen()) {
                        return (T) view;
                    }
                }
            }
        }
        return null;
    }

    public static void closeOpenContainer(ActivityContext activity,
            @FloatingViewType int type) {
        AbstractFloatingView view = getOpenView(activity, type);
        if (view != null) {
            view.close(true);
        }
    }

    public static void closeOpenViews(ActivityContext activity, boolean animate,
            @FloatingViewType int type) {
        BaseDragLayer dragLayer = activity.getDragLayer();
        // Iterate in reverse order. AbstractFloatingView is added later to the dragLayer,
        // and will be one of the last views.
        for (int i = dragLayer.getChildCount() - 1; i >= 0; i--) {
            View child = dragLayer.getChildAt(i);
            if (child instanceof AbstractFloatingView) {
                AbstractFloatingView abs = (AbstractFloatingView) child;
                if (abs.isOfType(type)) {
                    abs.close(animate);
                }
            }
        }
    }

    public static void closeAllOpenViews(ActivityContext activity, boolean animate) {
        closeOpenViews(activity, animate, TYPE_ALL);
        activity.finishAutoCancelActionMode();
    }

    public static void closeAllOpenViews(ActivityContext activity) {
        closeAllOpenViews(activity, true);
    }

    public static void closeAllOpenViewsExcept(BaseDraggingActivity activity, boolean animate,
                                               @FloatingViewType int type) {
        closeOpenViews(activity, animate, TYPE_ALL & ~type);
        activity.finishAutoCancelActionMode();
    }

    public static void closeAllOpenViewsExcept(BaseDraggingActivity activity,
                                               @FloatingViewType int type) {
        closeAllOpenViewsExcept(activity, true, type);
    }

    public static AbstractFloatingView getTopOpenView(ActivityContext activity) {
        return getTopOpenViewWithType(activity, TYPE_ALL);
    }

    public static AbstractFloatingView getTopOpenViewWithType(ActivityContext activity,
            @FloatingViewType int type) {
        return getOpenView(activity, type);
    }
}
