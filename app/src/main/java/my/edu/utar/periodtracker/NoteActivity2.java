package my.edu.utar.periodtracker;

import com.google.firebase.Timestamp;

public class NoteActivity2 {
    String title;
    String note;
    Timestamp timestamp;

    public NoteActivity2() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
