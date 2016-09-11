/*
 * Copyright (C) 2014-2016 Appgramming. All rights reserved.
 * http://www.appgramming.com
 */
package com.appgramming.lonecolor;

import android.app.IntentService;
import android.app.WallpaperManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * An {@link IntentService} subclass for changing the current system wallpaper to a bitmap
 * filled with the specified solid color.
 */
public class LoneColorWallpaperService extends IntentService {

    /**
     * An integer extra field that holds the color that should fill the wallpaper.
     */
    private static final String EXTRA_COLOR = "com.appgramming.intent.extra.Color";

    /**
     * The default color to use if no valid color was sent to the Color Wallpaper Service.
     */
    private static final int DEFAULT_COLOR = Color.BLACK;

    /**
     * The color format to copy to the clipboard.
     */
    private static final String COLOR_FORMAT = "#%1$06X";

    /**
     * Mask used to access the RGB components of a color int, without the alpha.
     */
    private static final int RGB_MASK = 0xFFFFFF;

    /**
     * One pixel size.
     */
    private static final int ONE_PIXEL = 1;

    /**
     * Creates the LoneColorWallpaperService IntentService.
     */
    public LoneColorWallpaperService() {
        super("LoneColorWallpaperService");
    }

    /**
     * Starts the LoneColorWallpaperService to change the current system wallpaper to a bitmap
     * filled with the specified solid color. If the service is already performing a task this
     * action will be queued.
     */
    public static void setColorWallpaper(Context context, int color) {
        final Intent intent = new Intent(context, LoneColorWallpaperService.class);
        intent.putExtra(EXTRA_COLOR, color);
        context.startService(intent);
    }

    /**
     * Do the actual work on the worker thread: create, fill and set the color wallpaper.
     *
     * @param intent The value passed to {@link Context#startService(Intent)}.
     */
    @Override
    protected void onHandleIntent(Intent intent) {

        // Get the desired color from the intent
        final int color = intent.getIntExtra(EXTRA_COLOR, DEFAULT_COLOR);

        try {

            // Get the Wallpaper Manager and the desired minimum width and height for the wallpaper
            final WallpaperManager wpManager = WallpaperManager.getInstance(this);

            // Create the 1px color bitmap
            final Bitmap colorBitmap = createColorBitmap(color);

            // Set the wallpaper
            wpManager.setBitmap(colorBitmap);

            // Success: copy the color code to the clipboard
            statusOK(color);

        } catch (Exception e) {
            // Write the stack trace to System.err and copy the reason of the failure to clipboard
            e.printStackTrace();
            statusFailure(e.toString());
        }
    }

    /**
     * Returns a mutable 1px x 1px bitmap filled with the specified color.
     *
     * @param color  The color to fill the bitmap
     */
    private static Bitmap createColorBitmap(int color) {
        final Bitmap colorBitmap = Bitmap.createBitmap(ONE_PIXEL, ONE_PIXEL, Bitmap.Config.ARGB_8888);
        colorBitmap.eraseColor(color);
        return colorBitmap;
    }

    /**
     * Copy a text to clipboard.
     *
     * @param text Text to copy.
     */
    private void copyText(CharSequence text) {
        final ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        final ClipData clip = ClipData.newPlainText(getString(R.string.app_name), text);
        clipboard.setPrimaryClip(clip);
    }

    /**
     * Copies the color to the clipboard as an OK status.
     *
     * @param color The color to copy.
     */
    private void statusOK(int color) {
        copyText(String.format(COLOR_FORMAT, RGB_MASK & color));
    }

    /**
     * Copies the error message to the clipboard as a failure status.
     *
     * @param message The error message.
     */
    private void statusFailure(CharSequence message) {
        copyText(message);
    }
}
