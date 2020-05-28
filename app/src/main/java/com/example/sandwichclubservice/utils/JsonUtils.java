package com.example.sandwichclubservice.utils;

import android.text.TextUtils;

import com.example.sandwichclubservice.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    // JSON Api key names
    private static final String ROOT_NAME = "name";
    private static final String MAIN_NAME ="mainName";
    private static final String OTHER_NAMES = "alsoKnownAs";
    private static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String IMAGE = "image";
    private static final String INGREDIENTS = "ingredients";
    private static final String DESCRIPTION = "description";

    public static Sandwich parseSandwichJson(String sandwichJson) {
        String mainName = "";
        String description = "";
        String sandwichImage = "";
        String placeOfOrigin = "";
        List<String> otherName = new ArrayList<>();
        List<String> ingredients = new ArrayList<>();
        // Return early
        if (TextUtils.isEmpty(sandwichJson)) {
            return null;
        }

        try{
            JSONObject sandwich = new JSONObject(sandwichJson);
            JSONObject sandwichName = sandwich.getJSONObject(ROOT_NAME);
            // JSONObject name parsing
            mainName = sandwichName.getString(MAIN_NAME);
            JSONArray alsoKnownAs = sandwichName.getJSONArray(OTHER_NAMES);
            for(int n = 0; n < alsoKnownAs.length(); n++)
            {
                otherName.add(alsoKnownAs.getString(n));
            }
            // other keys parsing
            placeOfOrigin = sandwich.getString(PLACE_OF_ORIGIN);
            description = sandwich.getString(DESCRIPTION);
            sandwichImage = sandwich.getString(IMAGE);
            // JSONArray parsing
            JSONArray sandwichIngredient = sandwich.getJSONArray(INGREDIENTS);

            for(int n = 0; n < sandwichIngredient.length(); n++)
            {
                ingredients.add(sandwichIngredient.getString(n));
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return new Sandwich(mainName, otherName, placeOfOrigin, description, sandwichImage, ingredients);
    }
}
