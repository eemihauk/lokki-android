package cc.softwarefactory.lokki.android.utilities.variability;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import cc.softwarefactory.lokki.android.R;

public class VariationUtils {

    public static final String KEY_VARIATION_JSON = "variationJson";


    public static int getTheme(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        String variationJsonString = prefs.getString(KEY_VARIATION_JSON, "{}");
        System.out.println("APPLY VARIATION JSON THEME: " + variationJsonString);
        try {
            JSONObject variationJson = new JSONObject(variationJsonString);
            if (variationJson.getInt("theme") != 0) {
                return R.style.AltTheme;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return R.style.LokkiTheme;
    }


    public static boolean applyVariation(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        String variationJsonString = prefs.getString(KEY_VARIATION_JSON, "{}");
        System.out.println("APPLY VARIATION JSON STRING" + variationJsonString.toString());
        try {
            JSONObject variationJson = new JSONObject(variationJsonString);
            if (variationJson.getInt("theme") != 0) {
                context.setTheme(R.style.AltTheme);
                System.out.println("THEME AFTER CHANGE: " + context.getTheme().toString());
                return true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

}
