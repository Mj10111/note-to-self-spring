package dev.notetoself.NoteToSelfSpring.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
public class Note {
    @Id
    @GeneratedValue
    private Long id;

    @Size(max=500, message = "{validation.name.size.too_long}")
    private String noteContent;
    private String dateCreated;
    private String dateUpdated;

    protected Note() {}

    public Note(String noteContent, String dateCreated, String dateUpdated){
        this.noteContent = noteContent;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String timeCreated) {
        this.dateCreated = timeCreated;
    }

    public String getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(String timeUpdated) {
        this.dateUpdated = timeUpdated;
    }

    @Override
    public String toString() {
        return String.format("Note[id=%d, noteContent='%s', dateCreated='%s', dateUpdated='%s']", id, noteContent, dateCreated, dateUpdated);
    }
}
