package com.demo.cooking.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.cooking.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Shady Shalaby on 08/06/2017.
 */

public class IngredientsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<String> ingredients;
    private Context context;
    private LayoutInflater inflater;

    public IngredientsAdapter(Context context) {
        this.ingredients = new ArrayList<String>();
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }

    public void addIngredient(String ingredient) {
        this.ingredients.add(ingredient);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new IngredientVH(inflater.inflate(R.layout.item_simple_text, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((IngredientVH) holder).tv_ingredient.setText("• " + ingredients.get(position));
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    class IngredientVH extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_simple)
        TextView tv_ingredient;

        public IngredientVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
