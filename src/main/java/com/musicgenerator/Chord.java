package com.musicgenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a musical chord containing multiple notes
 * 表示一个和弦，包含多个音符
 */
public class Chord {
    private List<Note> notes;
    private int duration;
    private long startTime;
    
    public Chord() {
        this.notes = new ArrayList<>();
        this.duration = 0;
        this.startTime = 0;
    }
    
    public Chord(List<Note> notes, int duration, long startTime) {
        this.notes = new ArrayList<>(notes);
        this.duration = duration;
        this.startTime = startTime;
    }
    
    /**
     * Add a note to the chord
     * 向和弦添加音符
     */
    public void addNote(Note note) {
        notes.add(note);
        if (note.getDuration() > duration) {
            duration = note.getDuration();
        }
    }
    
    /**
     * Create a major chord based on root note
     * 基于根音创建大三和弦
     */
    public static Chord createMajorChord(int rootPitch, int duration, long startTime) {
        Chord chord = new Chord();
        chord.startTime = startTime;
        chord.duration = duration;
        
        // Major chord: root, major third, perfect fifth
        chord.addNote(new Note(rootPitch, duration, 100, startTime));
        chord.addNote(new Note(rootPitch + 4, duration, 100, startTime));
        chord.addNote(new Note(rootPitch + 7, duration, 100, startTime));
        
        return chord;
    }
    
    /**
     * Create a minor chord based on root note
     * 基于根音创建小三和弦
     */
    public static Chord createMinorChord(int rootPitch, int duration, long startTime) {
        Chord chord = new Chord();
        chord.startTime = startTime;
        chord.duration = duration;
        
        // Minor chord: root, minor third, perfect fifth
        chord.addNote(new Note(rootPitch, duration, 100, startTime));
        chord.addNote(new Note(rootPitch + 3, duration, 100, startTime));
        chord.addNote(new Note(rootPitch + 7, duration, 100, startTime));
        
        return chord;
    }
    
    /**
     * Create a diminished chord based on root note
     * 基于根音创建减三和弦
     */
    public static Chord createDiminishedChord(int rootPitch, int duration, long startTime) {
        Chord chord = new Chord();
        chord.startTime = startTime;
        chord.duration = duration;
        
        // Diminished chord: root, minor third, diminished fifth
        chord.addNote(new Note(rootPitch, duration, 100, startTime));
        chord.addNote(new Note(rootPitch + 3, duration, 100, startTime));
        chord.addNote(new Note(rootPitch + 6, duration, 100, startTime));
        
        return chord;
    }
    
    // Getters
    public List<Note> getNotes() { return new ArrayList<>(notes); }
    public int getDuration() { return duration; }
    public long getStartTime() { return startTime; }
    public int getNoteCount() { return notes.size(); }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Chord{notes=[");
        for (int i = 0; i < notes.size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(notes.get(i).getNoteName());
        }
        sb.append("], duration=").append(duration).append(", startTime=").append(startTime).append("}");
        return sb.toString();
    }
} 