package cc.softwarefactory.lokki.android.utilities.variability;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONObject;

import cc.softwarefactory.lokki.android.constants.Constants;

public class VariationRequestTask extends AsyncTask<Context, Integer, Boolean>{

    private static String VariationApiUrl = Constants.VARIATION_API_URL;

    private static boolean getVariation(Context context) {
        AQuery aq = new AQuery(context);

        AjaxCallback<JSONObject> cb = new AjaxCallback<>();
        cb.url(VariationApiUrl).type(JSONObject.class);
        System.out.println("REQUESTING VARIATION FROM: " + VariationApiUrl);
        aq.sync(cb);

        int statusCode = cb.getStatus().getCode();
        System.out.println("CALLBACK STATUS: " + statusCode);
        if (statusCode != 200) {
            return false;
        }

        JSONObject variationJson = cb.getResult();
        if (variationJson != null) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            System.out.println("SAVING VARIATION JSON: " + variationJson.toString());
            prefs.edit().putString(VariationUtils.KEY_VARIATION_JSON, variationJson.toString()).commit();
            return true;
        }
        return false;
    }

    @Override
    protected Boolean doInBackground(Context... contexts) {
        System.out.println("VARIATION REQUEST TASK STARTED");
        if (contexts[0] != null) {
            return getVariation(contexts[0]);
        }
        return false;
    }

    // For dependency injection
    public static void setVariationApiUrl(String url) {
        VariationApiUrl = url;
    }
}
