package edu.westga.comp4420.notes_app.view.codebehind;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.util.List;
import java.util.ArrayList;
import edu.westga.comp4420.notes_app.model.Note;

public class NoteWindow {

    private NoteAddedListener noteAddedListener;
    private MainWindow mainWindow;
    private Note note;

    @FXML
    private Button addTopic;

    @FXML
    private Button backButton;

    @FXML
    private Button removeTopic;

    @FXML
    private TextArea noteTextBox;

    @FXML
    private ListView<String> topicsListView;

    @FXML
    private TextField topicInput;

    public void setMainWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    public void setNoteAddedListener(NoteAddedListener listener) {
        this.noteAddedListener = listener; 
    }

    public void setNoteText(String noteText) {
        if (this.note == null) {
            this.note = new Note(noteText);
        } else {
            this.note.setText(noteText);
        }
    }

    public void setNote(Note note) {
        if (this.note == null) {
            this.note = new Note(note.getText());
            this.note.setTopics(note.getTopics() != null ? note.getTopics() : new ArrayList<>());
        } else {
            this.note.setText(note.getText());
            this.note.setTopics(note.getTopics() != null ? note.getTopics() : new ArrayList<>());
        }
        this.noteTextBox.setText(this.note.getText());
        this.topicsListView.getItems().setAll(this.note.getTopics());
    }

    @FXML
    void handleBackButton(ActionEvent event) {
        String noteText = this.noteTextBox.getText();
        List<String> topics = this.topicsListView.getItems();
        if (noteText != null && !noteText.isEmpty()) {
            this.createOrUpdateNote(noteText, topics);
            System.out.println("Adding note: " + this.note);
        } else {
            System.out.println("Note is null");
        }
        
        Stage stage = (Stage) this.backButton.getScene().getWindow();
        stage.close();
    }

    private void createOrUpdateNote(String noteText, List<String> topics) {
        if (this.note == null) {
            this.createNewNote(noteText, topics);
        } else {
            this.updateNote(noteText, topics);
        }
    }

    private void createNewNote(String noteText, List<String> topics) {
        this.note = new Note(noteText);
        this.note.setTopics(topics);
        this.notifyNoteAdded();
    }

    private void updateNote(String noteText, List<String> topics) {
        this.note.setText(noteText);
        this.note.setTopics(topics);
        this.notifyNoteUpdated();
    }

    private void notifyNoteAdded() {
        if (this.noteAddedListener != null) {
            this.noteAddedListener.noteAdded(this.note);
        }
    }

    private void notifyNoteUpdated() {
        if (this.noteAddedListener != null) {
            this.noteAddedListener.noteUpdated(this.note);
        }
    }

    @FXML
    void handleRemoveTopic(ActionEvent event) {
        String selectedTopic = this.topicsListView.getSelectionModel().getSelectedItem();
        if (selectedTopic != null) {
            this.note.removeTopic(selectedTopic);
            this.topicsListView.getItems().remove(selectedTopic);
        }
    }

    @FXML
    void handleAddTopic(ActionEvent event) {
        String topic = this.topicInput.getText();
        if (topic != null && !topic.isEmpty()) {
            if (this.note == null) {
                this.note = new Note("");
            }
            this.note.addTopic(topic);
            this.topicInput.clear();
            this.topicsListView.getItems().setAll(this.note.getTopics());
        }
    }
}