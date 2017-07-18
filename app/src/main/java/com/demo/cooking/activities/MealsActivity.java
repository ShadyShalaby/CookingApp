package com.demo.cooking.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.demo.cooking.R;
import com.demo.cooking.adapters.MealsAdapter;
import com.demo.cooking.models.Meal;
import com.demo.cooking.models.SubCategory;
import com.demo.cooking.network.NetworkManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Response;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MealsActivity extends AppCompatActivity {

    final static String TAG = MealsActivity.class.getName();

    private SubCategory subCategory;
    private ArrayList<Meal> meals;
    private MealsAdapter mealsAdapter;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.rv_meals)
    RecyclerView rv_meals;

    Dialog dialog;

    public static Intent getIntent(Context context, SubCategory subCategory) {
        Intent intent = new Intent(context, MealsActivity.class);
        intent.putExtra("subCategory", subCategory);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals);

        ButterKnife.bind(this);

        if (getIntent().hasExtra("subCategory"))
            subCategory = (SubCategory) getIntent().getSerializableExtra("subCategory");

        mealsAdapter = new MealsAdapter(this);
        rv_meals.setLayoutManager(new LinearLayoutManager(this));

        rv_meals.setAdapter(mealsAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        meals = new ArrayList<Meal>();

        showProgress();
        NetworkManager.getInstance().getSubCategoryMeals(
                this,
                NetworkManager.buildMealsParams(subCategory.getId()),
                new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, Response<String> result) {
                        if (e != null) {
                            Toast.makeText(MealsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            hideProgress();
                            return;
                        }

                        Log.i(TAG, "Meals of subcategory call result " + result.getResult());
                        meals = new Gson().fromJson(result.getResult(), new TypeToken<ArrayList<Meal>>() {
                        }.getType());
                        mealsAdapter.setMeals(meals);
                        hideProgress();
                    }

                }
        );
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
