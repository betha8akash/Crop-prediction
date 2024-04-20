package com.example.farmwise;

import android.content.Context;
import android.content.SharedPreferences;

public class LanguageHelper {
    private static final String PREF_LANGUAGE = "pref_language";
    private static final String DEFAULT_LANGUAGE = "English"; // Default language if none is selected

    private SharedPreferences preferences;

    public LanguageHelper(Context context) {
        preferences = context.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE);
    }

    public void saveLanguage(String language) {
        preferences.edit().putString(PREF_LANGUAGE, language).apply();
    }

    public String getLanguage() {
        return preferences.getString(PREF_LANGUAGE, DEFAULT_LANGUAGE);
    }
}
