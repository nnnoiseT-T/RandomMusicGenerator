package com.musicgenerator;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Exports musical pieces to MIDI files
 * 将音乐作品导出为MIDI文件
 */
public class MidiExporter {
    
    private static final int DEFAULT_RESOLUTION = 480; // Ticks per quarter note
    private static final int DEFAULT_TEMPO = 120;      // BPM
    
    /**
     * Export a musical piece to a MIDI file
     * 将音乐作品导出为MIDI文件
     */
    public static void exportToMidi(MusicalPiece piece, String filename) throws Exception {
        // Create MIDI sequence
        // 创建MIDI序列
        Sequence sequence = new Sequence(Sequence.PPQ, piece.getTicksPerBeat());
        Track[] tracks = sequence.getTracks();
        
        // Track 0: Meta events (tempo, time signature)
        // 轨道0：元事件（速度、拍号）
        Track metaTrack = sequence.createTrack();
        addMetaEvents(metaTrack, piece);
        
        // Track 1: Melody (usually piano or lead instrument)
        // 轨道1：旋律（通常是钢琴或主奏乐器）
        Track melodyTrack = sequence.createTrack();
        addNotesToTrack(melodyTrack, piece.getMelody(), 0, 0); // Channel 0, Program 0 (Acoustic Grand Piano)
        
        // Track 2: Chords (usually strings or pad)
        // 轨道2：和弦（通常是弦乐或合成器）
        Track chordTrack = sequence.createTrack();
        addChordsToTrack(chordTrack, piece.getChords(), 1, 48); // Channel 1, Program 48 (String Ensemble 1)
        
        // Track 3: Bass line
        // 轨道3：低音线
        Track bassTrack = sequence.createTrack();
        addNotesToTrack(bassTrack, piece.getBassLine(), 2, 32); // Channel 2, Program 32 (Acoustic Bass)
        
        // Write to file
        // 写入文件
        MidiSystem.write(sequence, 1, new File(filename));
        System.out.println("MIDI file exported successfully: " + filename);
    }
    
    /**
     * Add meta events (tempo, time signature) to the track
     * 向轨道添加元事件（速度、拍号）
     */
    private static void addMetaEvents(Track track, MusicalPiece piece) throws InvalidMidiDataException {
        // Set tempo
        // 设置速度
        MetaMessage tempoMsg = new MetaMessage();
        int tempo = piece.getTempo();
        int tempoMPQ = 60000000 / tempo; // Convert BPM to MPQ (microseconds per quarter note)
        byte[] tempoData = {
            (byte) ((tempoMPQ >> 16) & 0xFF),
            (byte) ((tempoMPQ >> 8) & 0xFF),
            (byte) (tempoMPQ & 0xFF)
        };
        tempoMsg.setMessage(0x51, tempoData, 3);
        track.add(new MidiEvent(tempoMsg, 0));
        
        // Set time signature
        // 设置拍号
        MetaMessage timeSigMsg = new MetaMessage();
        int timeSignature = piece.getTimeSignature();
        byte[] timeSigData = {
            (byte) timeSignature,  // Numerator
            (byte) 2,              // Denominator (power of 2: 2 = quarter note)
            (byte) 24,             // MIDI clocks per click
            (byte) 8               // 32nd notes per quarter note
        };
        timeSigMsg.setMessage(0x58, timeSigData, 4);
        track.add(new MidiEvent(timeSigMsg, 0));
        
        // Set end of track
        // 设置轨道结束
        MetaMessage endMsg = new MetaMessage();
        endMsg.setMessage(0x2F, new byte[0], 0);
        track.add(new MidiEvent(endMsg, piece.getTotalDuration()));
    }
    
    /**
     * Add notes to a MIDI track
     * 向MIDI轨道添加音符
     */
    private static void addNotesToTrack(Track track, List<Note> notes, int channel, int program) 
            throws InvalidMidiDataException {
        
        // Set instrument program
        // 设置乐器程序
        ShortMessage programMsg = new ShortMessage();
        programMsg.setMessage(ShortMessage.PROGRAM_CHANGE, channel, program, 0);
        track.add(new MidiEvent(programMsg, 0));
        
        // Add notes
        // 添加音符
        for (Note note : notes) {
            // Note on
            // 音符开始
            ShortMessage noteOn = new ShortMessage();
            noteOn.setMessage(ShortMessage.NOTE_ON, channel, note.getPitch(), note.getVelocity());
            track.add(new MidiEvent(noteOn, note.getStartTime()));
            
            // Note off
            // 音符结束
            ShortMessage noteOff = new ShortMessage();
            noteOff.setMessage(ShortMessage.NOTE_OFF, channel, note.getPitch(), 0);
            track.add(new MidiEvent(noteOff, note.getStartTime() + note.getDuration()));
        }
    }
    
    /**
     * Add chords to a MIDI track
     * 向MIDI轨道添加和弦
     */
    private static void addChordsToTrack(Track track, List<Chord> chords, int channel, int program) 
            throws InvalidMidiDataException {
        
        // Set instrument program
        // 设置乐器程序
        ShortMessage programMsg = new ShortMessage();
        programMsg.setMessage(ShortMessage.PROGRAM_CHANGE, channel, program, 0);
        track.add(new MidiEvent(programMsg, 0));
        
        // Add chord notes
        // 添加和弦音符
        for (Chord chord : chords) {
            for (Note note : chord.getNotes()) {
                // Note on
                // 音符开始
                ShortMessage noteOn = new ShortMessage();
                noteOn.setMessage(ShortMessage.NOTE_ON, channel, note.getPitch(), note.getVelocity());
                track.add(new MidiEvent(noteOn, note.getStartTime()));
                
                // Note off
                // 音符结束
                ShortMessage noteOff = new ShortMessage();
                noteOff.setMessage(ShortMessage.NOTE_OFF, channel, note.getPitch(), 0);
                track.add(new MidiEvent(noteOff, note.getStartTime() + note.getDuration()));
            }
        }
    }
    
    /**
     * Create a simple MIDI file with basic melody
     * 创建包含基本旋律的简单MIDI文件
     */
    public static void createSimpleMidi(String filename, int[] notes, int[] durations) throws Exception {
        Sequence sequence = new Sequence(Sequence.PPQ, DEFAULT_RESOLUTION);
        Track track = sequence.createTrack();
        
        // Set tempo
        // 设置速度
        MetaMessage tempoMsg = new MetaMessage();
        int tempoMPQ = 60000000 / DEFAULT_TEMPO;
        byte[] tempoData = {
            (byte) ((tempoMPQ >> 16) & 0xFF),
            (byte) ((tempoMPQ >> 8) & 0xFF),
            (byte) (tempoMPQ & 0xFF)
        };
        tempoMsg.setMessage(0x51, tempoData, 3);
        track.add(new MidiEvent(tempoMsg, 0));
        
        // Add notes
        // 添加音符
        long currentTime = 0;
        for (int i = 0; i < notes.length; i++) {
            int pitch = notes[i];
            int duration = durations[i] * DEFAULT_RESOLUTION / 4; // Convert quarter notes to ticks
            
            if (pitch > 0) { // 0 represents a rest
                // Note on
                ShortMessage noteOn = new ShortMessage();
                noteOn.setMessage(ShortMessage.NOTE_ON, 0, pitch, 100);
                track.add(new MidiEvent(noteOn, currentTime));
                
                // Note off
                ShortMessage noteOff = new ShortMessage();
                noteOff.setMessage(ShortMessage.NOTE_OFF, 0, pitch, 0);
                track.add(new MidiEvent(noteOff, currentTime + duration));
            }
            
            currentTime += duration;
        }
        
        // Set end of track
        // 设置轨道结束
        MetaMessage endMsg = new MetaMessage();
        endMsg.setMessage(0x2F, new byte[0], 0);
        track.add(new MidiEvent(endMsg, currentTime));
        
        // Write to file
        // 写入文件
        MidiSystem.write(sequence, 1, new File(filename));
        System.out.println("Simple MIDI file created: " + filename);
    }
    
    /**
     * Get available MIDI instruments
     * 获取可用的MIDI乐器
     */
    public static String[] getAvailableInstruments() {
        String[] instruments = {
            "Acoustic Grand Piano", "Bright Acoustic Piano", "Electric Grand Piano",
            "Honky-tonk Piano", "Electric Piano 1", "Electric Piano 2",
            "Harpsichord", "Clavi", "Celesta", "Glockenspiel",
            "Music Box", "Vibraphone", "Marimba", "Xylophone",
            "Tubular Bells", "Dulcimer", "Drawbar Organ", "Percussive Organ",
            "Rock Organ", "Church Organ", "Reed Organ", "Accordion",
            "Harmonica", "Tango Accordion", "Acoustic Guitar (nylon)",
            "Acoustic Guitar (steel)", "Electric Guitar (jazz)",
            "Electric Guitar (clean)", "Electric Guitar (muted)",
            "Overdriven Guitar", "Distortion Guitar", "Guitar harmonics",
            "Acoustic Bass", "Electric Bass (finger)", "Electric Bass (pick)",
            "Fretless Bass", "Slap Bass 1", "Slap Bass 2", "Synth Bass 1",
            "Synth Bass 2", "Violin", "Viola", "Cello", "Contrabass",
            "Tremolo Strings", "Pizzicato Strings", "Orchestral Harp",
            "Timpani", "String Ensemble 1", "String Ensemble 2",
            "SynthStrings 1", "SynthStrings 2", "Choir Aahs",
            "Voice Oohs", "Synth Voice", "Orchestra Hit", "Trumpet",
            "Trombone", "Tuba", "Muted Trumpet", "French Horn",
            "Brass Section", "SynthBrass 1", "SynthBrass 2", "Soprano Sax",
            "Alto Sax", "Tenor Sax", "Baritone Sax", "Oboe", "English Horn",
            "Bassoon", "Clarinet", "Piccolo", "Flute", "Recorder",
            "Pan Flute", "Blown Bottle", "Shakuhachi", "Whistle",
            "Ocarina", "Lead 1 (square)", "Lead 2 (sawtooth)",
            "Lead 3 (calliope)", "Lead 4 (chiff)", "Lead 5 (charang)",
            "Lead 6 (voice)", "Lead 7 (fifths)", "Lead 8 (bass + lead)",
            "Pad 1 (new age)", "Pad 2 (warm)", "Pad 3 (polysynth)",
            "Pad 4 (choir)", "Pad 5 (bowed)", "Pad 6 (metallic)",
            "Pad 7 (halo)", "Pad 8 (sweep)", "FX 1 (rain)", "FX 2 (soundtrack)",
            "FX 3 (crystal)", "FX 4 (atmosphere)", "FX 5 (brightness)",
            "FX 6 (goblins)", "FX 7 (echoes)", "FX 8 (sci-fi)",
            "Sitar", "Banjo", "Shamisen", "Koto", "Kalimba",
            "Bag pipe", "Fiddle", "Shanai", "Tinkle Bell", "Agogo",
            "Steel Drums", "Woodblock", "Taiko Drum", "Melodic Tom",
            "Synth Drum", "Reverse Cymbal", "Guitar Fret Noise",
            "Breath Noise", "Seashore", "Bird Tweet", "Telephone Ring",
            "Helicopter", "Applause", "Gunshot"
        };
        return instruments;
    }
} 