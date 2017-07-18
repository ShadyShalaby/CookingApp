package com.demo.cooking.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.cooking.R;
import com.demo.cooking.activities.FoodDetailsActivity;
import com.demo.cooking.models.Meal;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Shady Shalaby on 04/06/2017.
 */
public class MealsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<Meal> meals;
    private LayoutInflater inflater;

    public MealsAdapter(Context context) {
        this.meals = new ArrayList<Meal>();
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setMeals(ArrayList<Meal> meals) {
        this.meals = meals;
        notifyDataSetChanged();
    }

    public void addMeal(Meal meal) {
        meals.add(meal);
        notifyDataSetChanged();
    }

    public ArrayList<Meal> getMeals() {
        return meals;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_item, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MealViewHolder cHolder = (MealViewHolder) holder;

        cHolder.txt_mealName.setText(meals.get(position).getName());
        Picasso.with(context)
                .load(meals.get(position).getImage())
//                .placeholder(R.drawable.pizzafood)
                .placeholder(R.color.colorPrimaryDark)
                .into(cHolder.img_mealBG);
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    protected class MealViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        Context context;

        @BindView(R.id.txt_itemName)
        TextView txt_mealName;
        @BindView(R.id.img_itemBG)
        ImageView img_mealBG;

        public MealViewHolder(View itemView) {
            super(itemView);

            context = itemView.getContext();
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
//            Toast.makeText(context, "Hello " + getAdapterPosition() + " " + getLayoutPosition(), Toast.LENGTH_SHORT).show();
            context.startActivity(FoodDetailsActivity.getIntent(context, meals.get(getAdapterPosition())));
        }

    }
}

