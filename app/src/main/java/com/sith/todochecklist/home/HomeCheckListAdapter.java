package com.sith.todochecklist.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sith.todochecklist.R;

import java.util.ArrayList;

public class HomeCheckListAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<HomeCheckListModel> checkListArrayList;
    private OnSingleCheckListClickListener onSingleCheckListClickListener;

    public HomeCheckListAdapter(Context context, ArrayList<HomeCheckListModel> checkListArrayList, OnSingleCheckListClickListener onSingleCheckListClickListener) {
        this.context = context;
        this.checkListArrayList = checkListArrayList;
        this.onSingleCheckListClickListener = onSingleCheckListClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_checklist_design,parent,false);
        return new HomeCheckListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HomeCheckListModel homeCheckListModel = checkListArrayList.get(position);
        //type cast holder to singleYTSongHolder
        HomeCheckListHolder homeCheckListHolder = (HomeCheckListHolder) holder;
        //set song name to singleYTSongHolder taken from MainActivity arraylist
        homeCheckListHolder.topicName.setText(homeCheckListModel.getTopicName());
        homeCheckListHolder.ivEditCheckList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSingleCheckListClickListener.onSingleCheckList(homeCheckListModel);
            }
        });

    }

    @Override
    public int getItemCount() {
        return checkListArrayList.size();
    }

    //inner class
    public class HomeCheckListHolder extends RecyclerView.ViewHolder {

        private TextView topicName;
        private TextView tvCheckListTime;
        private ImageView ivEditCheckList;
        //find that view created using view holder on recycler view
        public HomeCheckListHolder(@NonNull View itemView) {
            super(itemView);
            topicName = itemView.findViewById(R.id.topicName);
            tvCheckListTime = itemView.findViewById(R.id.tvCheckListTime);
            ivEditCheckList = itemView.findViewById(R.id.ivEditCheckList);

        }
    }

    //interface to edit checklist will be use in home
    public interface OnSingleCheckListClickListener {
        //abstract method
        public void onSingleCheckList(HomeCheckListModel homeCheckListModel);
    }

}
