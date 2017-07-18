package com.demo.cooking.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.cooking.R;
import com.demo.cooking.models.MealStep;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Shady Shalaby on 08/06/2017.
 */

public class StepsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<MealStep> steps;
    private Context context;
    private LayoutInflater inflater;

    public StepsAdapter(Context context) {
        this.steps = new ArrayList<>();
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public ArrayList<MealStep> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<MealStep> steps) {
        this.steps = steps;
        notifyDataSetChanged();
    }

    public void addStep(MealStep step) {
        this.steps.add(step);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StepVH(inflater.inflate(R.layout.item_simple_text, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((StepVH) holder).tv_step.setText("• " + steps.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    class StepVH extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_simple)
        TextView tv_step;

        StepVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
