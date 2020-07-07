package com.example.foodtruck;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class DataService {

    private static DataService dataService = new DataService();

    private static SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public static String BACKEND_URL = "http://192.168.0.115:8080/api";
    public static String USER_TYPE = "user_type";

    private DataService() {
    }

    public static DataService getInstance(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return dataService;
    }

    public boolean setEntry(String tag, String value) {
        editor = preferences.edit();
        editor.putString(tag, value);
        editor.apply();
        return true;
    }

    public String getEntry(String tag) {
        return preferences.getString(tag, "getEntry-Default");
    }

    public boolean getUserType(UserType type) {
        return setEntry(USER_TYPE, type.toString());
    }

    public UserType getUserType() {
        if (preferences.getString(USER_TYPE, UserType.CUSTOMER.toString()).equals(UserType.CUSTOMER.toString())) {
            return UserType.CUSTOMER;
        } else {
            return UserType.OPERATOR;
        }
    }

    public enum UserType {
        CUSTOMER, OPERATOR
    }
}