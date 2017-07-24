package com.demo.cooking.adapters;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
    private StepVH[] myHolder;
    private boolean[] timerOn;
    private long[] timeStart;
    private boolean masterOn;
    public Thread masterTimer;

    private LayoutInflater inflater;

    public StepsAdapter(Context context) {
        this.steps = new ArrayList<>();
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public ArrayList<MealStep> getSteps() {
        return steps;
    }

    public void setSteps(final ArrayList<MealStep> steps) {
        this.steps = steps;
        myHolder=new StepVH[steps.size()];
        timerOn = new boolean[steps.size()];
        for(int i=0;i<timerOn.length;i++){
            timerOn[i]=false;
        }
        timeStart = new long[steps.size()];
        masterOn=true;
        masterTimer=new Thread(){
            @Override
            public void run(){
                MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.timer_end);
                while(true){
                    if(timerOn!=null)
                        for(int i=0;i<timerOn.length;i++){
                            if(timerOn[i]){
                                long next=timeStart[i]+steps.get(i).getSecondTimer()*1000+999; //mili second error
                                long left=next-System.currentTimeMillis();
                                left/=1000;
                                left=Math.max(left,0);
                                final long left2=left;
                                final int i2=i;
                                myHolder[i].timer_left.post(
                                    new Runnable() {
                                        @Override
                                        public void run() {
                                            myHolder[i2].timer_left.setText(getTimer((int)left2));
                                        }
                                    }
                                );
                                if(left==0){
                                    timerOn[i]=false;
                                    mediaPlayer.start();
                                    //play some sound;
                                }
                            }
                        }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    hashCode();// delay thread
                }
            }
        };
        masterTimer.start();
        notifyDataSetChanged();
    }

    public void addStep(MealStep step) {
        this.steps.add(step);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StepVH(inflater.inflate(R.layout.item_text_with_timer, parent, false));
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        myHolder[position]=((StepVH) holder);
        ((StepVH) holder).tv_step.setText("• " + steps.get(position).getDescription());
        if(steps.get(position).getSecondTimer()==0) {
            ((StepVH) holder).timer_left.setText("");
//            ((StepVH) holder).timer_left.setVisibility(View.GONE);
            ((StepVH) holder).timer_button.setVisibility(View.GONE);
//            ((StepVH) holder).timer_button

        } else {
            final int pos=position;
            final StepVH tmpHolder=(StepVH) holder;
            ((StepVH) holder).timer_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pressTimer(pos,tmpHolder.timer_button);
                }
            });
            ((StepVH) holder).timer_left.setText(getTimer(steps.get(position).getSecondTimer()));
            ((StepVH) holder).timer_left.setText(getTimer(steps.get(position).getSecondTimer()));
        }
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    public String getTimer(int seconds){
        String sec="",min="";
        sec=(seconds%60)+"";
        min=(seconds/60)+"";
        if(sec.length()<2)
            sec="0"+sec;
        if(min.length()<2)
            min="0"+min;
        return min+":"+sec;
    }

    public void pressTimer(int i,ImageButton imb){
        if(timerOn[i]){
            timerOn[i]=false;
            imb.setBackgroundResource(R.drawable.timer);
            myHolder[i].timer_left.setText(getTimer(steps.get(i).getSecondTimer()));
        } else {
            timeStart[i]=System.currentTimeMillis(); // time first because thread is too fast
            imb.setBackgroundResource(R.drawable.timer_on);
            timerOn[i]=true;
        }
    }

    class StepVH extends RecyclerView.ViewHolder {

        @BindView(R.id.step_detail)
        TextView tv_step;
        @BindView(R.id.timer_button)
        ImageButton timer_button;
        @BindView(R.id.timer_left)
        TextView timer_left;

        StepVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
