package cc.softwarefactory.lokki.android.utilities.variability;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cc.softwarefactory.lokki.android.R;
import cc.softwarefactory.lokki.android.utilities.NavDrawerMenuUtils;


public class CustomizeNavDrawerTask extends AsyncTask<Context, Integer, Boolean> {

    @Override
    protected Boolean doInBackground(Context... contexts) {
        Context context = contexts[0];
        if (context == null) {
            throw new NullPointerException("Context given to CustomizeNavDrawerTask cannot be null");
        }
        return customizeNavDrawerMenu(context);
    }

    private static boolean customizeNavDrawerMenu(Context context) {
        Resources res = context.getResources();
        String[] navDrawerMenuItems = res.getStringArray(R.array.nav_drawer_menu_items);
        System.out.println(Arrays.toString(navDrawerMenuItems));
        JSONObject variationJson = VariationUtils.getVariationJson(context);
        if (variationJson == null || variationJson.length() == 0) {
            NavDrawerMenuUtils.setNavDrawerMenuItems(navDrawerMenuItems);
            return false;
        }
        return createNewNavDrawerMenu(res, navDrawerMenuItems, variationJson);
    }

    private static boolean createNewNavDrawerMenu(Resources res, String[] navDrawerMenuItems, JSONObject variationJson) {
        List<String> newNavDrawerMenuItems = new ArrayList<>();
        try {
            JSONObject navDrawerMenuItemsJson = variationJson.getJSONObject("nav_drawer_menu_items");
            for (String menuItem : navDrawerMenuItems) {
                if (menuItem.equals(res.getString(R.string.map))
                        && navDrawerMenuItemsJson.getBoolean("map")) {
                    newNavDrawerMenuItems.add(menuItem);
                }
                if (menuItem.equals(res.getString(R.string.places))
                        && navDrawerMenuItemsJson.getBoolean("places")) {
                    newNavDrawerMenuItems.add(menuItem);
                }
                if (menuItem.equals(res.getString(R.string.contacts))
                        && navDrawerMenuItemsJson.getBoolean("contacts")) {
                    newNavDrawerMenuItems.add(menuItem);
                }
                if (menuItem.equals(res.getString(R.string.settings))
                        && navDrawerMenuItemsJson.getBoolean("settings")) {
                    newNavDrawerMenuItems.add(menuItem);
                }
                if (menuItem.equals(res.getString(R.string.about))
                        && navDrawerMenuItemsJson.getBoolean("about")) {
                    newNavDrawerMenuItems.add(menuItem);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String[] newNavDrawerMenuItemsArray = newNavDrawerMenuItems.toArray(new String[newNavDrawerMenuItems.size()]);
        NavDrawerMenuUtils.setNavDrawerMenuItems(newNavDrawerMenuItemsArray);
        return true;
    }
}
