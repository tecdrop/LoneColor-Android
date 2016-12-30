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

package com.appgramming.lonecolor;

import android.app.Activity;
import android.os.Bundle;

import com.appgramming.lonecolor.utils.ColorClipboardParameter;
import com.appgramming.lonecolor.utils.ColorWallpaper;
import com.appgramming.lonecolor.utils.GoodRandomColor;
import com.appgramming.lonecolor.utils.Utils;

import java.io.IOException;

/**
 * LoneColor zero interface activity: just sets the color wallpaper and finishes.
 */
public class LoneColorActivity extends Activity {

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
            colorParam = ColorClipboardParameter.getColor(getApplication());
        } catch (Exception ignored) {
            // An unexpected exception while trying to get the color code from the clipboard
            // can crash the app at startup. Ignore any exceptions, we will generate a random
            // color anyway.
        }

        // If there is no valid color value in the clipboard, generate a random color
        final int color = (colorParam != null) ? colorParam : GoodRandomColor.nextColor();

        try {
            // Set the color wallpaper
            ColorWallpaper.setColorWallpaper(this, color);

            // Success: copy the color code to the clipboard
            Utils.copyText(this, Utils.colorToHex(color));

            // Go to the home screen
            Utils.goHome(this);

        } catch (IOException e) {

            // Write the stack trace to System.err and copy the reason of the failure to clipboard
            e.printStackTrace();
            Utils.copyText(this, e.toString());
        }
    }
}
