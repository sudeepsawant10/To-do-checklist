package com.sith.todochecklist.edit;

import static com.sith.todochecklist.home.MainActivity.C_KEY_CHECK_LIST_ID;
import static com.sith.todochecklist.home.MainActivity.C_KEY_CHECK_LIST_TOPIC_NAME;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Context;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sith.todochecklist.R;
import com.sith.todochecklist.home.HomeCheckListModel;
import com.sith.todochecklist.home.MainActivity;

import java.util.ArrayList;
import java.util.Locale;

public class EditCheckList extends AppCompatActivity {
    private Context context=this;
    private Button btnSave, btnAddTask;
    private EditText etCheckListTopicName;
    private TextInputEditText etCheckListTask;
    private TextView topicName;
    private ArrayList<HomeCheckListModel> checkListArrayList;
    private DatabaseReference rootRef;
    private String editedTopicId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_check_list);
        etCheckListTopicName = findViewById(R.id.etCheckListTopicName);
        btnSave = findViewById(R.id.btnSave);
        etCheckListTask = findViewById(R.id.etCheckListTask);
        btnAddTask = findViewById(R.id.btnAddTask);
        //list checklist
        topicName = findViewById(R.id.topicName);

        rootRef = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        if(intent != null) {
            String checkListId = intent.getStringExtra(C_KEY_CHECK_LIST_ID);
            String checkListTopicName = intent.getStringExtra(C_KEY_CHECK_LIST_TOPIC_NAME);

            if(checkListId != null) {
                StringBuilder random_checklist_name_with_id = new StringBuilder(String.valueOf(checkListId));

                // get a sentence with seperated words
                String[] words = checkListTopicName.split("\\s+");
                for (int i = 0; i < words.length; i++) {
                    // You may want to check for a non-word character before blindly
                    // performing a replacement
                    // It may also be necessary to adjust the character class
                    words[i] = words[i].replaceAll("[^\\w]", "").toLowerCase(Locale.ROOT);
                    random_checklist_name_with_id.append("_").append(words[i]);
                }
                editedTopicId = String.valueOf(random_checklist_name_with_id);
                DatabaseReference ref = rootRef.child("CheckList").child(editedTopicId);
                ValueEventListener eventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Iterable<DataSnapshot> checkListChildren = dataSnapshot.getChildren();
                        for(DataSnapshot singleChildren : checkListChildren) {

                        }
                        etCheckListTopicName.setText(checkListTopicName);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                };
                ref.addValueEventListener(eventListener);
           }
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("CheckList");

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String topicId;
                String topicName = etCheckListTopicName.getText().toString();
                long random_id = System.currentTimeMillis();//random id in millis
                StringBuilder random_checklist_name_with_id = new StringBuilder(String.valueOf(random_id));

                if(editedTopicId == null) {

                    // get a sentence with seperated words
                    random_checklist_name_with_id = new StringBuilder(String.valueOf(random_id));
                    String[] words = topicName.split("\\s+");
                    for (int i = 0; i < words.length; i++) {
                        // You may want to check for a non-word character before blindly
                        // performing a replacement
                        // It may also be necessary to adjust the character class
                        words[i] = words[i].replaceAll("[^\\w]", "").toLowerCase(Locale.ROOT);
                        random_checklist_name_with_id.append("_").append(words[i]);
                    }
                    topicId = String.valueOf(random_checklist_name_with_id);
                }
                else {
                    topicId = editedTopicId;
                }

                HomeCheckListModel homeCheckListModel = new HomeCheckListModel();
                homeCheckListModel.setId(random_id);
                homeCheckListModel.setTopicName(topicName);

                myRef.child(topicId).setValue(homeCheckListModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        startActivity(new Intent(context, MainActivity.class));
                        finish();
                    }
                });


            }
        });

    }
}