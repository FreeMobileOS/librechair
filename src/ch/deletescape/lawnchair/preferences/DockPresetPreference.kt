package ch.deletescape.lawnchair.preferences

import android.content.Context
import android.support.v7.preference.ListPreference
import android.util.AttributeSet
import com.android.launcher3.Utilities

class DockPresetPreference(context: Context, attrs: AttributeSet?) : ListPreference(context, attrs) {

    private val prefs = Utilities.getLawnchairPrefs(context)

    private val property = prefs.dockStyles::dockPreset

    private val onChangeListener = { value = "${property.get()}" }

    override fun onSetInitialValue(restoreValue: Boolean, defaultValue: Any?) {
        value = "${property.get()}"
    }

    override fun onAttached() {
        super.onAttached()
        prefs.dockStyles.addListener(onChangeListener)
    }

    override fun onDetached() {
        super.onDetached()
        prefs.dockStyles.removeListener(onChangeListener)
    }

    override fun getPersistedString(defaultReturnValue: String?): String {
        return "${property.get()}"
    }

    override fun persistString(value: String?): Boolean {
        property.set(value?.toIntOrNull() ?: 1)
        return true
    }
}
