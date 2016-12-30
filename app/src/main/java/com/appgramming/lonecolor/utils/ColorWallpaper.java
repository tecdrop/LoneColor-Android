/*
 * Copyright (C) 2014-2016 Appgramming
 * http://www.appgramming.com
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

package com.appgramming.lonecolor.utils;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;

import java.io.IOException;

/**
 * Utility class for setting a color wallpaper.
 */

public final class ColorWallpaper {

    /**
     * The minimum safe wallpaper pixel size.
     */
    private static final int MIN_SAFE_SIZE = 100;

    private ColorWallpaper() {
    }

    /**
     * Create a color filled bitmap and changes the current system wallpaper to this bitmap.
     */
    public static void setColorWallpaper(Context context, int color) throws IOException {

        // Get the Wallpaper Manager
        final WallpaperManager wpManager = WallpaperManager.getInstance(context);

        // Create the pitch black bitmap
        final Bitmap pitchBlackBitmap = createColorBitmap(color, MIN_SAFE_SIZE, MIN_SAFE_SIZE);

        // Set the wallpaper
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // On Android N and above use the new API to set both the general system wallpaper and
            // the lock-screen-specific wallpaper
            wpManager.setBitmap(pitchBlackBitmap, null, true, WallpaperManager.FLAG_SYSTEM | WallpaperManager.FLAG_LOCK);
        } else {
            wpManager.setBitmap(pitchBlackBitmap);
        }
    }

    /**
     * Returns a mutable bitmap filled with the specified color.
     */
    @SuppressWarnings("SameParameterValue")
    private static Bitmap createColorBitmap(int color, int width, int height) {
        final Bitmap colorBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        colorBitmap.eraseColor(color);
        return colorBitmap;
    }
}
