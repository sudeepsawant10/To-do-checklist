package com.sith.todochecklist;

import java.util.ArrayList;

public class SingleCheckList {
    private long id;
    private String topicName;
    private ArrayList<SingleTask> singleTaskArrayList = new ArrayList<>();

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

    public ArrayList<SingleTask> getSingleTaskArrayList() {
        return singleTaskArrayList;
    }

    public void setSingleTaskArrayList(ArrayList<SingleTask> singleTaskArrayList) {
        this.singleTaskArrayList = singleTaskArrayList;
    }
}
