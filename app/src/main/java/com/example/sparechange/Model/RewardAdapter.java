package com.example.sparechange.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sparechange.R;

import java.util.List;

public class RewardAdapter extends RecyclerView.Adapter<RewardAdapter.RewardViewHolder> {

    private Context context;
    private List<Reward> rewardList;
    private onRewardListener mOnRewardListener;

    public RewardAdapter(Context context, List<Reward> rewardList, onRewardListener OnRewardListener) {
        this.context = context;
        this.rewardList = rewardList;
        this.mOnRewardListener = OnRewardListener;
    }

    @Override
    public RewardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_rewards, null);

        RewardViewHolder holder = new RewardViewHolder(view, mOnRewardListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RewardViewHolder holder, int position) {
        Reward reward = rewardList.get(position);

        holder.textViewTitle.setText(reward.getTitle());
        holder.textViewCompany.setText(reward.getCompany());
        holder.textViewPrice.setText(String.valueOf(reward.getPrice()));
        holder.imageView.setImageDrawable(context.getResources().getDrawable(reward.getImage()));

    }

    @Override
    public int getItemCount() {
        return rewardList.size();
    }

    class RewardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView textViewTitle, textViewCompany, textViewPrice;
        onRewardListener OnRewardListener;

        public RewardViewHolder(@NonNull View itemView, onRewardListener OnRewardListener) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewCompany = itemView.findViewById(R.id.textViewCompany);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);

            this.OnRewardListener = OnRewardListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            OnRewardListener.onRewardClick(getAdapterPosition());
        }
    }

    public interface onRewardListener {
        void onRewardClick(int position);

    }
}
