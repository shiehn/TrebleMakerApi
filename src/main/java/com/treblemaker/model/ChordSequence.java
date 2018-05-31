package com.treblemaker.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chord_sequences")
public class ChordSequence {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "song_name")
    private String songName;

    @Column(name = "time_signature")
    private String timeSignature;

    @Column(name = "key")
    private String harmonicKey;

    @OneToOne(fetch=FetchType.EAGER)
    @JoinTable(name="genres")
    @JoinColumn(name="id")
    private Genre genre;

    @OneToMany(fetch=FetchType.EAGER)
    @JoinTable(name = "chord_sequence_chords",
            joinColumns = @JoinColumn(name = "chord_sequence_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "chord_id", referencedColumnName = "id"))
    @OrderColumn(name="sequence_order")
    private List<HiveChord> chords = new ArrayList<>();

    public long getId() {
        return id;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getTimeSignature() {
        return timeSignature;
    }

    public void setTimeSignature(String timeSignature) {
        this.timeSignature = timeSignature;
    }

    public String getHarmonicKey() {
        return harmonicKey;
    }

    public void setHarmonicKey(String harmonicKey) {
        this.harmonicKey = harmonicKey;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public List<HiveChord> getChords() {
        return chords;
    }

    public void setChords(List<HiveChord> chords) {
        this.chords = chords;
    }

    /*
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "harmonic_loop_chords",
            joinColumns = @JoinColumn(name = "harmonic_loop_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "chord_id", referencedColumnName = "id"))
    private List<HiveChord> chords = new ArrayList<HiveChord>();
     */
}
