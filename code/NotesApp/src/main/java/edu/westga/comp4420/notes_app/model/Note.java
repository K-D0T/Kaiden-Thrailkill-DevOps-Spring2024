package edu.westga.comp4420.notes_app.model;

import java.util.List;
import java.util.ArrayList;

public class Note {
    private String text;
    private List<String> topics;

    public Note(String text) {
        this.text = text;
        this.topics = new ArrayList<>();
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTopics(List<String> topics) {
        this.topics = new ArrayList<>(topics);
    }

    public List<String> getTopics() {
        return this.topics;
    }

    public void addTopic(String topic) {
        this.topics.add(topic);
    }

    public void removeTopic(String topic) {
        this.topics.remove(topic);
    }

    public boolean hasTopic(String topic) {
        return this.topics.contains(topic);
    }
}