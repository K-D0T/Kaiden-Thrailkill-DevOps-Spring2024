package edu.westga.comp4420.notes_app.view.codebehind;

import edu.westga.comp4420.notes_app.model.Note;

public interface NoteAddedListener {
    void noteAdded(Note note);
    void noteUpdated(Note note);
}