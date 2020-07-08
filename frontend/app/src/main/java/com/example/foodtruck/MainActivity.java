package com.example.foodtruck;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;

import com.example.foodtruck.activities.RegisterActivity;
import com.example.foodtruck.activities.customer.CustomerLocationActivity;
import com.example.foodtruck.activities.customer.CustomerMenuActivity;
import com.example.foodtruck.model.user.Customer;
import com.example.foodtruck.model.user.Operator;
import com.example.foodtruck.model.user.User;
import com.example.foodtruck.activities.operator.OwnerMenuActivity;

public class MainActivity extends Activity {

    private String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_type_chooser_layout);
        VolleyLog.DEBUG = true;

        findViewById(R.id.type_customer_button).setOnClickListener(view -> {
            Log.d(TAG, "onCreate: user is customer, saving.");
            DataService.getInstance(this).setEntry(DataService.USER_TYPE, DataService.UserType.CUSTOMER.toString());
            setContentView(R.layout.activity_general_login);
        });

        findViewById(R.id.type_operator_button).setOnClickListener(view -> {
            Log.d(TAG, "onCreate: user is operator, saving.");
            DataService.getInstance(this).setEntry(DataService.USER_TYPE, DataService.UserType.OPERATOR.toString());
            setContentView(R.layout.activity_general_login);
        });
    }

    public void login(View v) {
        Log.d(TAG, "login: trying to find user.");
        // check user_type make request
        RequestQueue queue = Volley.newRequestQueue(this);
        String name = ((EditText) findViewById(R.id.name_editText)).getText().toString();
        String password = ((EditText) findViewById(R.id.passwort_editText)).getText().toString();
        GsonRequest<User> request = new GsonRequest<>(Request.Method.POST, DataService.BACKEND_URL + "/user/login", new User(name, password), User.class, DataService.getStandardHeader(), response -> {
            if (response.getName().equalsIgnoreCase(name)) {
                if (DataService.getInstance(this).getUserType() == DataService.UserType.CUSTOMER) {
                    startActivity(new Intent(this, CustomerMenuActivity.class));
                } else {
                    startActivity(new Intent(this, OwnerMenuActivity.class));
                }
            } else {
                showLoginError();
            }
        }, error -> {
            Log.e(TAG, "login: user is not registered!", error);
            showLoginError();
        });
        queue.add(request);
    }

    public void showLoginError() {
        ((EditText) findViewById(R.id.name_editText)).setError("Falscher Benutzername");
        ((EditText) findViewById(R.id.passwort_editText)).setError("Falsches Passwort");
    }

    public void registrieren(View v) {
        startActivity(new Intent(this, RegisterActivity.class));
    }

}
