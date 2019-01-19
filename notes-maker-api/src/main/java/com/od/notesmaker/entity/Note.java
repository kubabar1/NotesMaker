package com.od.notesmaker.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name="notes")
public class Note implements Serializable {

    @Id
    @Column(name = "ID")
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="userID")
    private User user;

    @Column(name = "name")
    private String name;

    @Column(name = "content")
    private String content;

    @Column(name = "creationDate")
    private Timestamp creationDate;

    public Note() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return id == note.id &&
                Objects.equals(user, note.user) &&
                Objects.equals(name, note.name) &&
                Objects.equals(content, note.content) &&
                Objects.equals(creationDate, note.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, name, content, creationDate);
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", user=" + user +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}
