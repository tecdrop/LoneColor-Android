/*
 * Copyright (C) 2014-2016 Appgramming. All rights reserved.
 * http://www.appgramming.com
 */
package com.appgramming.lonecolor;

import android.app.Activity;
import android.os.Bundle;

/**
 * App no-display start activity: just sets the color wallpaper and finishes.
 */
public class StartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the color wallpaper
        setColorWallpaper();

        // Finish the activity
        finish();
    }

    /**
     * Sets the color wallpaper to the color value in the Clipboard, or to a random color.
     */
    private void setColorWallpaper() {
        // Try to get the color parameter from the clipboard
        Integer colorParam = null;
        try {
            colorParam = ColorClipParameter.getColor(getApplication());
        } catch (Exception e) {
            // An unexpected exception while trying to get the color code from the clipboard
            // can crash the app at startup. Ignore any exceptions, we will generate a random
            // color anyway.
        }

        // If there is no valid color value in the clipboard, generate a random color
        final int color = colorParam != null ? colorParam : GoodRandomColor.nextColor();

        // Start the color wallpaper intent service to set the color wallpaper and report status
        // through the clipboard.
        LoneColorWallpaperService.setColorWallpaper(getApplication(), color);
    }
}
