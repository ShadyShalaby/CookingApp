package com.demo.cooking.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.cooking.R;
import com.demo.cooking.adapters.IngredientsAdapter;
import com.demo.cooking.adapters.StepsAdapter;
import com.demo.cooking.models.Meal;
import com.demo.cooking.models.MealDetails;
import com.demo.cooking.network.NetworkManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Response;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FoodDetailsActivity extends AppCompatActivity {

    private static final String TAG = FoodDetailsActivity.class.getName();
    private static final String INTENT_MEAL = "meal";

    private Meal meal;
    private MealDetails mealDetails;
    private Dialog dialog;

    private IngredientsAdapter ingredientsAdapter;
    private StepsAdapter stepsAdapter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.tv_meal_name)
    TextView tv_mealName;
    @BindView(R.id.tv_meal_desc)
    TextView tv_mealDesc;
    @BindView(R.id.img_meal_background)
    ImageView img_mealBG;
    @BindView(R.id.rv_meal_ingredients)
    RecyclerView rv_ingredients;
    @BindView(R.id.rv_meal_steps)
    RecyclerView rv_steps;

    public static Intent getIntent(Context context, Meal meal) {

        Intent intent = new Intent(context, FoodDetailsActivity.class);
        intent.putExtra(INTENT_MEAL, meal);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);

        ButterKnife.bind(this);

        if (getIntent().hasExtra(INTENT_MEAL) && getIntent().getSerializableExtra(INTENT_MEAL) != null) {
            meal = (Meal) getIntent().getSerializableExtra(INTENT_MEAL);
            bindMealMainData();
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initLists();
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (meal == null) {
            Toast.makeText(FoodDetailsActivity.this, "onStart meal == null", Toast.LENGTH_SHORT).show();
            return;
        }

        mealDetails = new MealDetails();

        showProgress();
        NetworkManager.getInstance().getFoodDetails(
                this,
                NetworkManager.buildFoodDescriptionParams(meal.getId()),
                new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, Response<String> result) {
                        if (e != null) {
                            Toast.makeText(FoodDetailsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            hideProgress();
                            return;
                        }

                        Log.i(TAG, "Meal details call result " + result.getResult());
                        mealDetails = new Gson().fromJson(result.getResult(), new TypeToken<MealDetails>() {
                        }.getType());
                        ingredientsAdapter.setIngredients(mealDetails.getIngredients());
                        stepsAdapter.setSteps(mealDetails.getSteps());
                        hideProgress();
                    }

                });
    }

    private void bindMealMainData() {
        setSupportActionBar(toolbar);

        toolbar.setTitle(meal.getName());
        tv_mealName.setText(meal.getName());
        tv_mealDesc.setText(meal.getDescription());
        Picasso.with(this)
                .load(meal.getImage())
                .into(img_mealBG);
    }

    private void initLists() {
        ingredientsAdapter = new IngredientsAdapter(this);
        rv_ingredients.setLayoutManager(new LinearLayoutManager(this));
        rv_ingredients.setAdapter(ingredientsAdapter);

        stepsAdapter = new StepsAdapter(this);
        rv_steps.setLayoutManager(new LinearLayoutManager(this));
        rv_steps.setAdapter(stepsAdapter);
    }

    public void showProgress() {
        if (dialog == null) {
            dialog = new Dialog(this, R.style.dialog_theme);
            dialog.setCancelable(false);
        }
        if (!dialog.isShowing()) {
            mProgressBar.setVisibility(View.VISIBLE);
            dialog.show();
        }
    }

    public void hideProgress() {
        if (dialog != null && dialog.isShowing()) {
            mProgressBar.setVisibility(View.INVISIBLE);
            dialog.dismiss();
        }
    }

}
