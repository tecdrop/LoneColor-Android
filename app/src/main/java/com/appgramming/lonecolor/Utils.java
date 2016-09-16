/*
 * Copyright (C) 2014-2016 Appgramming. All rights reserved.
 * http://www.appgramming.com
 */
package com.appgramming.lonecolor;

import android.content.Context;
import android.content.Intent;

/**
 * Static utility methods.
 */
class Utils {

    /**
     * Goes home.
     */
    static void goHome(Context context) {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(startMain);
    }
}
