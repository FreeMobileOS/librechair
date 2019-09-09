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

package ch.deletescape.lawnchair.globalsearch.activity;

import android.os.Bundle;
import android.widget.EditText;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.launcher3.R;

import java.util.Arrays;

import ch.deletescape.lawnchair.globalsearch.activity.predictions.PredictionsListAdapter;
import ch.deletescape.lawnchair.settings.ui.SettingsBaseActivity;

public class WebSearchProviderActivity extends SettingsBaseActivity {
    private EditText searchQuery;
    private RecyclerView suggestions;
    private PredictionsListAdapter predictionsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_search_provider);
        searchQuery = findViewById(R.id.search_query);
        suggestions = findViewById(R.id.search_query_suggestions);
        suggestions.setLayoutManager(new LinearLayoutManager(this));
        suggestions.setAdapter(new PredictionsListAdapter(Arrays.asList("Test A", "Test B", "Test C"), item -> {
            // TODO make predictions work
        }));
    }
}
