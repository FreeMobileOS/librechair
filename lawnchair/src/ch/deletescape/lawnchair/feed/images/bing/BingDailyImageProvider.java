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

package ch.deletescape.lawnchair.feed.images.bing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import ch.deletescape.lawnchair.feed.Card;
import ch.deletescape.lawnchair.feed.images.AbstractImageProvider;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BingDailyImageProvider extends AbstractImageProvider<String> {
    private final Map<Bitmap, String> images = new LinkedHashMap<>();
    private final Callback<BingResponse> callback = new Callback<BingResponse>() {
        @Override
        public synchronized void onResponse(Call<BingResponse> call,
                Response<BingResponse> response) {
            Log.d(BingDailyImageProvider.this.getClass().getName(), "onResponse: retrieved daily wallpaper " + response);
            if (response.isSuccessful() && response.body() != null && response.body().url != null) {
                Executors.newSingleThreadExecutor().submit(() -> {
                    synchronized (images) {
                        Log.d(BingDailyImageProvider.this.getClass().getName(),
                                "onResponse: retrieved wallpaper URL " + response.body().url);
                        try {
                            URLConnection connection = new URL(response.body().url)
                                    .openConnection();
                            Log.d(BingDailyImageProvider.this.getClass().getName(),
                                    "onResponse: opened connection " + response.body().url);
                            images.clear();
                            images.put(BitmapFactory.decodeStream(connection.getInputStream()),
                                    response.body().url);
                            Log.d(BingDailyImageProvider.this.getClass().getName(),
                                    "onResponse: retrieved bitmap for " + response.body().url);
                            lastRefresh = System.currentTimeMillis();
                        } catch (IOException e) {
                            Log.e(BingDailyImageProvider.this.getClass().getName(), "onResponse: failed to retrieve bing daily image", e);
                        }
                    }
                });
            }
        }

        @Override
        public synchronized void onFailure(Call<BingResponse> call, Throwable t) {
            Executors.newSingleThreadExecutor().submit(() -> {
                try {
                    Thread.sleep(TimeUnit.MINUTES.toMillis(1));
                    BingRetrofitServiceFactory.INSTANCE.getApi(getContext()).getPicOfTheDay().enqueue(this);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    };
    private long lastRefresh = 0;

    @SuppressWarnings("unused")
    public BingDailyImageProvider(Context c) {
        super(c);
        BingRetrofitServiceFactory.INSTANCE.getApi(c).getPicOfTheDay().enqueue(callback);
    }

    @NotNull
    @Override
    public Map<Bitmap, String> getImages() {
        return images;
    }

    @Nullable
    @Override
    public Card getHeaderCard() {
        return null;
    }

    @NotNull
    @Override
    public Function1<String, Unit> getOnRemoveListener() {
        return id -> Unit.INSTANCE;
    }

    @NotNull
    @Override
    public List<Card> getCards() {
        Log.d(getClass().getName(), "getCards: retrieving cards");
        List<Card> cards = super.getCards();
        Log.d(getClass().getName(), "getCards: cards are " + cards);
        return cards;
    }
}
