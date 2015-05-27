package cc.softwarefactory.lokki.android.utilities.variability;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import cc.softwarefactory.lokki.android.R;
import cc.softwarefactory.lokki.android.utilities.Utils;

public class VariationUtils {

    public static final String KEY_VARIATION_JSON = "variationJson";


    public static int getTheme(Context context) {
        JSONObject variationJson = getVariationJson(context);
        if (variationJson == null || variationJson.length() == 0) {
            return R.style.LokkiTheme;
        }
        try {
            if (variationJson.getInt("theme") != 0) {
                return R.style.AltTheme;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return R.style.LokkiTheme;
    }

    protected static JSONObject getVariationJson(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        String variationJsonString = prefs.getString(KEY_VARIATION_JSON, "{}");
        try {
            return new JSONObject(variationJsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
