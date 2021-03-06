package com.example.foodtruck.activities.operator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.example.foodtruck.R;
import com.example.foodtruck.adapter.AdvancedIngredientsAdapter;
import com.example.foodtruck.model.Dish;
import com.example.foodtruck.model.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class OperatorSpeiseNeuActivity extends AppCompatActivity {

    private String TAG = getClass().getSimpleName();

    private List<Ingredient> ingredients = new ArrayList<>();
    private static int RC_NEW_INGREDIENT = 1;
    public static String INTENT_NEW_INGREDIENT = "new_ingredient";
    public static String INTENT_NEW_AMOUNT = "new_amount";
    ListView lv;
    AdvancedIngredientsAdapter advancedIngredientsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_speiseneu);
        lv = findViewById(R.id.new_dish_ingredients);
    }

    public void speiseAnlegen(View v) {
        String name = ((EditText) findViewById(R.id.neu_name_input)).getText().toString();
        if (name.equals("")) {
            ((EditText) findViewById(R.id.neu_name_input)).setError("Name fehlt");
            return;
        }
        if (((EditText) findViewById(R.id.neu_preis_input)).getText().toString().equals("")) {
            ((EditText) findViewById(R.id.neu_preis_input)).setError("Preis fehlt");
            return;
        }
        double basePrice = Double.parseDouble(((EditText) findViewById(R.id.neu_preis_input)).getText().toString());
        Dish dish = new Dish(name, basePrice, ingredients);
        Intent returnIntent = new Intent();
        returnIntent.putExtra(OperatorSpeisekarteActivity.INTENT_NEW_DISH, dish);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    public void openAddzutat(View v) {
        startActivityForResult(new Intent(this, OperatorAddzutatActivity.class), RC_NEW_INGREDIENT);
    }

    public void backButton(View v) {
        Intent in = new Intent(this, OperatorSpeisekarteActivity.class);
        startActivity(in);
    }

    public void ownerHome(View v) {
        Intent in = new Intent(this, OperatorMenuActivity.class);
        startActivity(in);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == RC_NEW_INGREDIENT && data.hasExtra(INTENT_NEW_INGREDIENT) && data.hasExtra(INTENT_NEW_AMOUNT)) {
                String ingredient = (String) data.getSerializableExtra(INTENT_NEW_INGREDIENT);
                int amount = data.getIntExtra(INTENT_NEW_AMOUNT, 1);
                ingredients.add(new Ingredient(ingredient, amount));
                Ingredient[] ingredientsArray = new Ingredient[ingredients.size()];
                advancedIngredientsAdapter = new AdvancedIngredientsAdapter(this, 0, ingredients.toArray(ingredientsArray));
                lv.setAdapter(advancedIngredientsAdapter);
            }
        } else {
            Log.e(TAG, "onActivityResult: resultCode != 0");
        }
    }
}