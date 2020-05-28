package com.example.sandwichclubservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sandwichclubservice.model.Sandwich;
import com.example.sandwichclubservice.utils.JsonUtils;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        assert intent != null;
        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
            // Allow json data to be each TextView
           TextView origin = findViewById(R.id.origin_tv);
           origin.setText(sandwich.getPlaceOfOrigin());

           TextView description = findViewById(R.id.description_tv);
           description.setText(sandwich.getDescription());

           TextView alsoKnown = findViewById(R.id.also_known_tv);
           //TextUtils to join the different alsoKnownAs string and display them all.
           alsoKnown.setText(TextUtils.join(", ", sandwich.getAlsoKnownAs()));

           TextView ingredients = findViewById(R.id.ingredients_tv);
           //TextUtils to join the different ingredients string and display them all.
           ingredients.setText(TextUtils.join(", ", sandwich.getIngredients()));

    }
}
