package com.sith.todochecklist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CheckListAdapter.OnSingleCheckListClickListener {
    public static final String C_KEY_CHECK_LIST_ID = "C_KEY_CHECK_LIST_ID";
    public static final String C_KEY_CHECK_LIST_TOPIC_NAME = "C_KEY_CHECK_LIST_TOPIC_NAME";
    private Context context = this;
    private ImageView ivAddCheckList;
    private RecyclerView checkListRecylerView;
    private ArrayList<SingleCheckList> checkListArrayList = new ArrayList<>();
    private CheckListAdapter checkListAdapter;
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
        checkListAdapter = new CheckListAdapter(context, checkListArrayList, this::onSingleCheckList);
        checkListRecylerView.setAdapter(checkListAdapter);

        ivAddCheckList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, TopicCheckList.class));
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        DatabaseReference ref = rootRef.child("CheckList");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //SingleCheckList => id, topicName, singleTaskArray[]List(id, taskName, is_done)
                ArrayList<SingleCheckList> singleCheckListArrayList = new ArrayList<>();
                Iterable<DataSnapshot> checkListChildren = dataSnapshot.getChildren();
                for(DataSnapshot singleChildren : checkListChildren) {
                    SingleCheckList c = singleChildren.getValue(SingleCheckList.class);
                    singleCheckListArrayList.add(c);
                }

                //modified list
                checkListArrayList.clear();
                checkListArrayList.addAll(singleCheckListArrayList);

                checkListAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };

          ref.addValueEventListener(eventListener);
//        ref.addListenerForSingleValueEvent(eventListener);

    }
    @Override
    public void onSingleCheckList(SingleCheckList singleCheckList) {
        String checkListId = String.valueOf(singleCheckList.getId());
        String checkListTopicName = singleCheckList.getTopicName();
        Intent intent = new Intent(context, TopicCheckList.class);
        intent.putExtra(C_KEY_CHECK_LIST_ID, checkListId);
        intent.putExtra(C_KEY_CHECK_LIST_TOPIC_NAME, checkListTopicName);
        startActivity(intent);
        finish();

    }

}