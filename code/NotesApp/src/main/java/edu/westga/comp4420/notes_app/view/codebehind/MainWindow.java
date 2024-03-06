package edu.westga.comp4420.notes_app.view.codebehind;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ListCell;
import javafx.scene.text.Text;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import edu.westga.comp4420.notes_app.model.Note;

public class MainWindow implements NoteAddedListener {
    private List<Note> notes;
    private int selectedIndex = -1;

    @FXML
    private ListView<String> topicsListView;

    @FXML
    private Button addNote;

    @FXML
    private ListView<Note> recentNotesListView;

    @FXML
    private Button removeNote;

    public MainWindow() {
        this.notes = new ArrayList<>();
    }

    public List<Note> getNotesByTopic(String topic) {
        return this.notes.stream()
            .filter(note -> note.hasTopic(topic))
            .collect(Collectors.toList());
    }

    public void updatetopicsListView() {
        Set<String> allTopics = new HashSet<>();
        for (Note note : this.notes) {
            allTopics.addAll(note.getTopics());
        }
        this.topicsListView.getItems().setAll(allTopics);
        this.topicsListView.refresh();
    }

    @Override
    public void noteAdded(Note note) {
        if (note != null) {
            this.recentNotesListView.getItems().add(0, note);
            this.notes.add(0, note);
            this.updatetopicsListView();
        } else {
            System.out.println("Note is null");
        }
    }

    @Override
    public void noteUpdated(Note note) {
        int selectedIndex = this.recentNotesListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex != -1) {
            this.recentNotesListView.getItems().set(selectedIndex, note);
            this.notes.set(selectedIndex, note);
            this.updatetopicsListView();
        }
    }

    public int getSelectedIndex() {
        return this.selectedIndex;
    }

    public void setSelectedIndex(int index) {
        this.selectedIndex = index;
    }

    @FXML
    void initialize() {
        this.recentNotesListView.setCellFactory(param -> new ListCell<>() {
            private Text text;

            @Override
            protected void updateItem(Note item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    if (this.text == null) {
                        this.text = new Text();
                    }
                    this.text.setText(item.getText());
                    this.text.wrappingWidthProperty().bind(MainWindow.this.recentNotesListView.widthProperty().subtract(15));
                    setGraphic(this.text);
                }
            }
        });

        this.topicsListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                List<Note> notesByTopic = this.getNotesByTopic(newSelection);
                this.recentNotesListView.getItems().setAll(notesByTopic);
            }
        });

        this.updatetopicsListView();
    }

    @FXML
    void handleEditOrAddNote(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/westga/comp4420/notes_app/view/codebehind/NoteWindow.fxml"));
            Parent root = fxmlLoader.load();

            NoteWindow noteWindowController = fxmlLoader.getController();
            noteWindowController.setNoteAddedListener(this);
            noteWindowController.setMainWindow(this);

            Note selectedNote = this.recentNotesListView.getSelectionModel().getSelectedItem();
            if (selectedNote != null) {
                noteWindowController.setNote(selectedNote);
            }

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleRemoveNote(ActionEvent event) {
        int selectedIndex = this.recentNotesListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex != -1) {
            this.recentNotesListView.getItems().remove(selectedIndex);
        } else {
            System.out.println("No note selected");
        }
    }
}