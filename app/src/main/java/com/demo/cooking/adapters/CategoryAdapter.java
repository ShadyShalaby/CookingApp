package com.demo.cooking.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.cooking.R;
import com.demo.cooking.activities.SubCategoriesActivity;
import com.demo.cooking.models.Category;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Shady Shalaby on 09/04/2017.
 */

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<Category> categories;
    private LayoutInflater inflater;

    public CategoryAdapter(Context context, ArrayList<Category> categories) {
        this.categories = categories;
        this.context = context;
        inflater = LayoutInflater.from(context);

    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_item, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CategoryViewHolder cHolder = (CategoryViewHolder) holder;

        cHolder.txt_categoryName.setText(categories.get(position).getName());
        Picasso.with(context)
                .load(categories.get(position).getImage())
//                .placeholder(R.drawable.pizzafood)
                .placeholder(R.color.colorPrimaryDark)
                .into(cHolder.img_categoryBG);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class CategoryViewHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private Context context;

        @BindView(R.id.txt_itemName)
        TextView txt_categoryName;
        @BindView(R.id.img_itemBG)
        ImageView img_categoryBG;

        public CategoryViewHolder(View itemView) {
            super(itemView);

            context = itemView.getContext();
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            context.startActivity(
                    SubCategoriesActivity.getIntent(context, categories.get(getAdapterPosition()))
            );
        }
    }

}