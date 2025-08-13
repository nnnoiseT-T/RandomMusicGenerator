package com.musicgenerator;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Music theory utilities for generating harmonically correct music
 * 音乐理论工具，用于生成和声正确的音乐
 */
public class MusicTheory {
    
    // Major scale intervals (whole steps and half steps)
    // 大调音阶音程（全音和半音）
    private static final int[] MAJOR_SCALE = {0, 2, 4, 5, 7, 9, 11, 12};
    
    // Minor scale intervals (natural minor)
    // 小调音阶音程（自然小调）
    private static final int[] MINOR_SCALE = {0, 2, 3, 5, 7, 8, 10, 12};
    
    // Pentatonic scale (5-note scale common in many cultures)
    // 五声音阶（在许多文化中常见的5音音阶）
    private static final int[] PENTATONIC_SCALE = {0, 2, 4, 7, 9, 12};
    
    // Blues scale (minor pentatonic with blue notes)
    // 布鲁斯音阶（小调五声音阶加蓝音）
    private static final int[] BLUES_SCALE = {0, 3, 5, 6, 7, 10, 12};
    
    // Common chord progressions in popular music
    // 流行音乐中常见的和弦进行
    private static final int[][] COMMON_PROGRESSIONS = {
        {0, 4, 5, 3},     // I-IV-V-vi (most common)
        {0, 5, 3, 4},     // I-V-vi-IV
        {0, 3, 4, 0},     // I-vi-IV-I
        {0, 4, 0, 5},     // I-IV-I-V
        {0, 5, 0, 3},     // I-V-I-vi
        {0, 6, 4, 5},     // I-vi-IV-V
        {0, 4, 5, 0},     // I-IV-V-I
        {0, 3, 6, 4}      // I-vi-ii-IV
    };
    
    private static final Random random = new Random();
    
    /**
     * Get notes in a major scale starting from root note
     * 获取从根音开始的大调音阶音符
     */
    public static int[] getMajorScale(int rootPitch) {
        int[] scale = new int[MAJOR_SCALE.length];
        for (int i = 0; i < MAJOR_SCALE.length; i++) {
            scale[i] = rootPitch + MAJOR_SCALE[i];
        }
        return scale;
    }
    
    /**
     * Get notes in a minor scale starting from root note
     * 获取从根音开始的小调音阶音符
     */
    public static int[] getMinorScale(int rootPitch) {
        int[] scale = new int[MINOR_SCALE.length];
        for (int i = 0; i < MINOR_SCALE.length; i++) {
            scale[i] = rootPitch + MINOR_SCALE[i];
        }
        return scale;
    }
    
    /**
     * Get notes in a pentatonic scale starting from root note
     * 获取从根音开始的五声音阶音符
     */
    public static int[] getPentatonicScale(int rootPitch) {
        int[] scale = new int[PENTATONIC_SCALE.length];
        for (int i = 0; i < PENTATONIC_SCALE.length; i++) {
            scale[i] = rootPitch + PENTATONIC_SCALE[i];
        }
        return scale;
    }
    
    /**
     * Get notes in a blues scale starting from root note
     * 获取从根音开始的布鲁斯音阶音符
     */
    public static int[] getBluesScale(int rootPitch) {
        int[] scale = new int[BLUES_SCALE.length];
        for (int i = 0; i < BLUES_SCALE.length; i++) {
            scale[i] = rootPitch + BLUES_SCALE[i];
        }
        return scale;
    }
    
    /**
     * Get a random chord progression
     * 获取随机和弦进行
     */
    public static int[] getRandomChordProgression() {
        return COMMON_PROGRESSIONS[random.nextInt(COMMON_PROGRESSIONS.length)];
    }
    
    /**
     * Check if two notes are harmonically consonant
     * 检查两个音符是否和声协和
     */
    public static boolean isConsonant(int pitch1, int pitch2) {
        int interval = Math.abs(pitch1 - pitch2) % 12;
        // Consonant intervals: unison, minor third, major third, perfect fourth, 
        // perfect fifth, minor sixth, major sixth, octave
        // 协和音程：同度、小三度、大三度、纯四度、纯五度、小六度、大六度、八度
        return interval == 0 || interval == 3 || interval == 4 || interval == 5 || 
               interval == 7 || interval == 8 || interval == 9;
    }
    
    /**
     * Get a harmonically related note based on current note and scale
     * 基于当前音符和音阶获取和声相关的音符
     */
    public static int getHarmonicNote(int currentNote, int[] scale, int direction) {
        // Find current note in scale
        // 在音阶中找到当前音符
        int scaleIndex = -1;
        for (int i = 0; i < scale.length; i++) {
            if (scale[i] % 12 == currentNote % 12) {
                scaleIndex = i;
                break;
            }
        }
        
        if (scaleIndex == -1) {
            // Note not in scale, return a random scale note
            // 音符不在音阶中，返回随机音阶音符
            return scale[random.nextInt(scale.length)];
        }
        
        // Move in the specified direction
        // 按指定方向移动
        int newIndex = (scaleIndex + direction + scale.length) % scale.length;
        return scale[newIndex];
    }
    
    /**
     * Get a random root note for a scale (C4 to C6 range)
     * 获取音阶的随机根音（C4到C6范围）
     */
    public static int getRandomRootNote() {
        // C4 = 60, C6 = 84
        return 60 + (random.nextInt(3) * 12) + random.nextInt(12);
    }
    
    /**
     * Get a random scale type
     * 获取随机音阶类型
     */
    public static ScaleType getRandomScaleType() {
        ScaleType[] types = ScaleType.values();
        return types[random.nextInt(types.length)];
    }
    
    /**
     * Get scale based on scale type and root note
     * 基于音阶类型和根音获取音阶
     */
    public static int[] getScale(ScaleType type, int rootNote) {
        switch (type) {
            case MAJOR:
                return getMajorScale(rootNote);
            case MINOR:
                return getMinorScale(rootNote);
            case PENTATONIC:
                return getPentatonicScale(rootNote);
            case BLUES:
                return getBluesScale(rootNote);
            default:
                return getMajorScale(rootNote);
        }
    }
    
    /**
     * Scale types enumeration
     * 音阶类型枚举
     */
    public enum ScaleType {
        MAJOR("Major"),
        MINOR("Minor"), 
        PENTATONIC("Pentatonic"),
        BLUES("Blues");
        
        private final String displayName;
        
        ScaleType(String displayName) {
            this.displayName = displayName;
        }
        
        @Override
        public String toString() {
            return displayName;
        }
    }
} 