package com.sith.todochecklist.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sith.todochecklist.R;
import com.sith.todochecklist.edit.EditCheckList;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements HomeCheckListAdapter.OnSingleCheckListClickListener {
    public static final String C_KEY_CHECK_LIST_ID = "C_KEY_CHECK_LIST_ID";
    public static final String C_KEY_CHECK_LIST_TOPIC_NAME = "C_KEY_CHECK_LIST_TOPIC_NAME";
    private Context context = this;
    private ImageView ivAddCheckList;
    private RecyclerView checkListRecylerView;
    private ArrayList<HomeCheckListModel> checkListArrayList = new ArrayList<>();
    private HomeCheckListAdapter homeCheckListAdapter;
    private DatabaseReference rootRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rootRef = FirebaseDatabase.getInstance().getReference();

        ivAddCheckList = findViewById(R.id.ivAddCheckList);
        checkListRecylerView = findViewById(R.id.checkListRecylerView);

        //set adapter for checkListRecyclerView
        checkListRecylerView.setLayoutManager(new LinearLayoutManager(context));
        homeCheckListAdapter = new HomeCheckListAdapter(context, checkListArrayList, this::onSingleCheckList);
        checkListRecylerView.setAdapter(homeCheckListAdapter);

        ivAddCheckList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, EditCheckList.class));
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        /*
            UPDATE THE LATEST DATA ON UI IF USER REVISETED APP
        */

        DatabaseReference ref = rootRef.child("CheckList");
        //To take latest data from db
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //SingleCheckList => id, topicName, singleTaskArray[]List(id, taskName, is_done)
                ArrayList<HomeCheckListModel> homeCheckListArrayListModel = new ArrayList<>();
                Iterable<DataSnapshot> checkListChildren = dataSnapshot.getChildren();
                for(DataSnapshot singleChildren : checkListChildren) {
                    HomeCheckListModel c = singleChildren.getValue(HomeCheckListModel.class);
                    homeCheckListArrayListModel.add(c);
                }

                // pass updated data to adaptor
                checkListArrayList.clear();
                checkListArrayList.addAll(homeCheckListArrayListModel);

                homeCheckListAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };

          ref.addValueEventListener(eventListener);
//        ref.addListenerForSingleValueEvent(eventListener);
        refreshUI();

    }
    private void refreshUI(){

    }

    @Override
    public void onSingleCheckList(HomeCheckListModel homeCheckListModel) {
        String checkListId = String.valueOf(homeCheckListModel.getId());
        String checkListTopicName = homeCheckListModel.getTopicName();
        Intent intent = new Intent(context, EditCheckList.class);
        intent.putExtra(C_KEY_CHECK_LIST_ID, checkListId);
        intent.putExtra(C_KEY_CHECK_LIST_TOPIC_NAME, checkListTopicName);
        startActivity(intent);
        finish();

    }

}