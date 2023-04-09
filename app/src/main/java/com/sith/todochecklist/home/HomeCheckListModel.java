package com.sith.todochecklist.home;

import com.sith.todochecklist.edit.EditTaskModel;

import java.util.ArrayList;

public class HomeCheckListModel {
    private long id;
    private String topicName;
    private ArrayList<EditTaskModel> editTaskModelArrayList = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public ArrayList<EditTaskModel> getSingleTaskArrayList() {
        return editTaskModelArrayList;
    }

    public void setSingleTaskArrayList(ArrayList<EditTaskModel> editTaskModelArrayList) {
        this.editTaskModelArrayList = editTaskModelArrayList;
    }
}
