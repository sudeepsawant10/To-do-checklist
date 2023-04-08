package com.sith.todochecklist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CheckListAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<SingleCheckList> checkListArrayList;
    private OnSingleCheckListClickListener onSingleCheckListClickListener;

    public CheckListAdapter(Context context, ArrayList<SingleCheckList> checkListArrayList, OnSingleCheckListClickListener onSingleCheckListClickListener) {
        this.context = context;
        this.checkListArrayList = checkListArrayList;
        this.onSingleCheckListClickListener = onSingleCheckListClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_checklist,parent,false);
        return new SingleCheckListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SingleCheckList singleCheckList = checkListArrayList.get(position);
        //type cast holder to singleYTSongHolder
        SingleCheckListHolder singleCheckListHolder = (SingleCheckListHolder) holder;
        //set song name to singleYTSongHolder taken from MainActivity arraylist
        singleCheckListHolder.topicName.setText(singleCheckList.getTopicName());
        singleCheckListHolder.ivEditCheckList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSingleCheckListClickListener.onSingleCheckList(singleCheckList);
            }
        });

    }

    @Override
    public int getItemCount() {
        return checkListArrayList.size();
    }

    //inner class
    public class SingleCheckListHolder extends RecyclerView.ViewHolder {

        private TextView topicName;
        private TextView tvCheckListTime;
        private ImageView ivEditCheckList;
        //find that view created using view holder on recycler view
        public SingleCheckListHolder(@NonNull View itemView) {
            super(itemView);
            topicName = itemView.findViewById(R.id.topicName);
            tvCheckListTime = itemView.findViewById(R.id.tvCheckListTime);
            ivEditCheckList = itemView.findViewById(R.id.ivEditCheckList);

        }
    }

    //interface to edit checklist
    public interface OnSingleCheckListClickListener {
        //abstract method
        public void onSingleCheckList(SingleCheckList singleCheckList);
    }

}
