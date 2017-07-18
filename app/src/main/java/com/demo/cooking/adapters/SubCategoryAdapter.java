package com.demo.cooking.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.cooking.R;
import com.demo.cooking.activities.MealsActivity;
import com.demo.cooking.models.SubCategory;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Shady Shalaby on 26/04/2017.
 */
public class SubCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<SubCategory> subCategories;
    private LayoutInflater inflater;

    public SubCategoryAdapter(Context context) {
        this.subCategories = new ArrayList<SubCategory>();
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setSubCategories(ArrayList<SubCategory> subCategories) {
        this.subCategories = subCategories;
        notifyDataSetChanged();
    }

    public ArrayList<SubCategory> getSubCategories() {
        return subCategories;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_item, parent, false);
        return new SubCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SubCategoryViewHolder cHolder = (SubCategoryViewHolder) holder;

        cHolder.txt_subCategoryName.setText(subCategories.get(position).getName());
        Picasso.with(context)
                .load(subCategories.get(position).getImage())
//                .placeholder(R.drawable.pizzafood)
                .placeholder(R.color.colorPrimaryDark)
                .into(cHolder.img_subCategoryBG);
    }

    @Override
    public int getItemCount() {
        return subCategories.size();
    }

    class SubCategoryViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        Context context;

        @BindView(R.id.txt_itemName)
        TextView txt_subCategoryName;
        @BindView(R.id.img_itemBG)
        ImageView img_subCategoryBG;

        public SubCategoryViewHolder(View itemView) {
            super(itemView);

            context = itemView.getContext();
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            context.startActivity(
                    MealsActivity.getIntent(context, subCategories.get(getAdapterPosition()))
            );
        }

    }
}

