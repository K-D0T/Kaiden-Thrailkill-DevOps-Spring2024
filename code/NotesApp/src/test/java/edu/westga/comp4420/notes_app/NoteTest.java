package edu.westga.comp4420.notes_app;

import edu.westga.comp4420.notes_app.model.Note;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class NoteTest {

    @Test
    public void testConstructor() {
        Note note = new Note("Test note0");
        assertEquals("Test note0", note.getText());
        assertTrue(note.getTopics().isEmpty());
    }

    @Test
    public void testSetText() {
        Note note = new Note("Test note2");
        note.setText("New text");
        assertEquals("New text", note.getText());
    }

    @Test
    public void testSetTopics() {
        Note note = new Note("Test note3");
        note.setTopics(List.of("Topic1", "Topic2"));
        assertEquals(List.of("Topic1", "Topic2"), note.getTopics());
    }

    @Test
    public void testAddTopic() {
        Note note = new Note("Test note4");
        note.addTopic("New topic");
        assertTrue(note.getTopics().contains("New topic"));
    }

    @Test
    public void testRemoveTopic() {
        Note note = new Note("Test note5");
        note.setTopics(List.of("Topic1", "Topic2"));
        note.removeTopic("Topic1");
        assertFalse(note.getTopics().contains("Topic1"));
    }

    @Test
    public void testHasTopic() {
        Note note = new Note("Test note6");
        note.setTopics(List.of("Topic1", "Topic2"));
        assertTrue(note.hasTopic("Topic1"));
        assertFalse(note.hasTopic("Topic3"));
    }
}