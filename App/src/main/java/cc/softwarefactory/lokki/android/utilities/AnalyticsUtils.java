package cc.softwarefactory.lokki.android.utilities;


import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import cc.softwarefactory.lokki.android.R;

public class AnalyticsUtils {
    private static Tracker tracker;

    public static void initAnalytics(Context context) {
        tracker = GoogleAnalytics.getInstance(context).newTracker(R.xml.analytics_global_tracker_config);
        tracker.set("&uid", Utils.getDeviceId());
    }

    public static void screenHit(String screenName) {
        tracker.setScreenName(screenName);
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }


    public static void eventHit(String category, String action, String label, long value) {
        HitBuilders.EventBuilder eventBuilder = new HitBuilders.EventBuilder()
                .setCategory(category)
                .setAction(action);
        if (label != null) {
            eventBuilder = eventBuilder.setLabel(label);
        }
        if (value != -1) {
            eventBuilder = eventBuilder.setValue(value);
        }
        tracker.send(eventBuilder.build());
    }

    public static void eventHit(String category, String action, String label) {
        eventHit(category, action, label, -1);
    }

    public static void eventHit(String category, String action, long value) {
        eventHit(category, action, null, value);
    }

    public static void eventHit(String category, String action) {
        eventHit(category, action, null, -1);
    }

}
