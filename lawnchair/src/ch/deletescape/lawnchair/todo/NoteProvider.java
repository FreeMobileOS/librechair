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

package ch.deletescape.lawnchair.todo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import ch.deletescape.lawnchair.todo.INoteProvider.Stub;
import com.android.launcher3.Utilities;
import java.util.List;

public class NoteProvider extends Service {

    private INoteProvider.Stub service;

    public NoteProvider() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return service != null ? service : (service = new Stub() {
            @Override
            public List<Note> getNotes() throws RemoteException {
                return Utilities.getLawnchairPrefs(getApplicationContext()).getFeedNotes();
            }
        });
    }
}
