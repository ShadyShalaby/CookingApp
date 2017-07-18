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
import com.demo.cooking.adapters.SubCategoryAdapter;
import com.demo.cooking.models.Category;
import com.demo.cooking.models.SubCategory;
import com.demo.cooking.network.NetworkManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Response;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubCategoriesActivity extends AppCompatActivity {

    final static String TAG = SubCategoriesActivity.class.getName();

    SubCategoryAdapter subCategoryAdapter;
    ArrayList<SubCategory> subCategories;
    Category category;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.recView_subCategories)
    RecyclerView rec_subCategories;

    Dialog dialog;

    public static Intent getIntent(Context context, Category category) {
        Intent intent = new Intent(context, SubCategoriesActivity.class);
        intent.putExtra("category", category);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_categories);

        ButterKnife.bind(this);

        if (getIntent().hasExtra("category"))
            category = (Category) getIntent().getSerializableExtra("category");

        subCategoryAdapter = new SubCategoryAdapter(this);
        rec_subCategories.setLayoutManager(new LinearLayoutManager(this));

        rec_subCategories.setAdapter(subCategoryAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        subCategories = new ArrayList<>();

        showProgress();
        NetworkManager
                .getInstance()
                .getSubCategories(
                        this,
                        NetworkManager.buildSubCategoryParams(category.getId()),
                        new FutureCallback<Response<String>>() {
                            @Override
                            public void onCompleted(Exception e, Response<String> result) {
                                if (e != null) {
                                    Toast.makeText(SubCategoriesActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    hideProgress();
                                    return;
                                }

                                Log.i(TAG, "Sub Categories call result " + result.getResult());
                                subCategories = new Gson().fromJson(
                                        result.getResult(),
                                        new TypeToken<ArrayList<SubCategory>>() {
                                        }.getType());
                                subCategoryAdapter.setSubCategories(subCategories);
                                hideProgress();
                            }
                        });
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
