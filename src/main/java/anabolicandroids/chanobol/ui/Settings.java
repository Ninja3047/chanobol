package anabolicandroids.chanobol.ui;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;

import anabolicandroids.chanobol.App;
import anabolicandroids.chanobol.R;
import anabolicandroids.chanobol.ui.scaffolding.Prefs;
import anabolicandroids.chanobol.util.BaseSettings;
import anabolicandroids.chanobol.util.Util;

// The following resources have been helpful:
// http://stackoverflow.com/a/3026922/283607
public class Settings extends BaseSettings {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs);

        Preference p = findPreference(Prefs.EXTERNAL_WEBM);
        if (Build.VERSION.SDK_INT < 15) {
            p.setSummary("In-app WebM playback needs at least Android 3.0");
            p.setEnabled(false);
            p.getEditor().putBoolean(Prefs.EXTERNAL_WEBM, true).apply();
        }

        p = findPreference(Prefs.IMMERSIVE_MODE);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            p.setSummary("Immersive Mode needs at least Android 4.4");
            p.setEnabled(false);
            p.getEditor().putBoolean(Prefs.IMMERSIVE_MODE, false).apply();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            p.setSummary("Experimental");
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
        if (key.equals(Prefs.HIDABLE_TOOLBAR)) {
            // For the case that the toolbar is not fully protracted and the user
            // disables the auto-hiding toolbar it needs to be fully protracted afterwards.
            App.needToProtractToolbar = true;
        }
        if (key.equals(Prefs.THEME)) {
            Util.restartApp(getApplication(), this);
        }
    }
}
