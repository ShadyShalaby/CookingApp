package com.demo.cooking.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.demo.cooking.R;
import com.demo.cooking.adapters.CategoryAdapter;
import com.demo.cooking.models.Category;
import com.demo.cooking.network.NetworkManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Response;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    CategoryAdapter categoryAdapter;
    ArrayList<Category> categories = new ArrayList<Category>();

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recView_categories)
    RecyclerView categoriesRecyclerView;

    private Dialog dialog;

    public static Intent getIntent(Context context) {
        return new Intent(context, HomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        categoryAdapter = new CategoryAdapter(this, categories);
        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        categoriesRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
//        categoriesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        categoriesRecyclerView.setAdapter(categoryAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        categories = new ArrayList<Category>();

        showProgress();
        NetworkManager.getInstance().getCategories(this, new FutureCallback<Response<String>>() {
            @Override
            public void onCompleted(Exception e, Response<String> result) {
                if (e != null) {
                    Toast.makeText(HomeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    hideProgress();
                    return;
                }

                Log.i(HomeActivity.class.getName(), "Result " + result.getResult());
                categories = new Gson().fromJson(
                        result.getResult(),
                        new TypeToken<ArrayList<Category>>() {
                        }.getType());

                categoryAdapter.setCategories(categories);
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
            dialog.dismiss();
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}
