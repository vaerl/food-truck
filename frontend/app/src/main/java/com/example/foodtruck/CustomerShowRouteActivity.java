package com.example.foodtruck;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.foodtruck.adapter.AdvancedCustomerShowRouteAdapter;
import com.example.foodtruck.model.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomerShowRouteActivity extends AppCompatActivity {

    private String TAG = getClass().getSimpleName();

    Location[] locations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_route);
        ListView lv = findViewById(R.id.customer_show_route_list);

        Log.d(TAG, "show route: try to get locations");

        Map<String, String> params = new HashMap<String, String>();
        params.put("Content-Type", "application/json");

        RequestQueue queue = Volley.newRequestQueue(this);
        String operatorId = "1";

        GsonRequest<Location[]> request = new GsonRequest<>(Request.Method.GET, DataService.BACKEND_URL + "/operator/" + operatorId + "/route", Location[].class, params, response -> {
            if (response!= null) {
                locations = response;
                AdvancedCustomerShowRouteAdapter advancedToDoAdapter = new AdvancedCustomerShowRouteAdapter(this, 0, locations);
                lv.setAdapter(advancedToDoAdapter);
            }
        }, error -> {
            Log.e(TAG, "Could not get current location!", error);
        });
        queue.add(request);

        String[] test = {"123", "456"};
    }

}