package com.example.mylibrary.Media.soundpool;

import java.util.HashMap;

/**
 * Created by zy on 2017/10/28.
 */

public class SoundPoolBuild {

    /**
     * 音乐池大小.
     */
    private int typeMax;

    /**
     * 音乐池id.
     */
    private int soundPoolId;

    /**
     * 音乐类型.
     */
    private int type;

    /**
     * 音乐品质.
     */
    private int quality;

    /**
     * 音乐池资源.
     */
    private HashMap<Integer,Integer> soundPool;

    public int getTypeMax() {
        return typeMax;
    }

    public SoundPoolBuild setTypeMax(int typeMax) {
        this.typeMax = typeMax;
        return this;
    }

    public int getType() {
        return type;
    }

    public SoundPoolBuild setType(int type) {
        this.type = type;
        return this;
    }

    public int getQuality() {
        return quality;
    }

    public SoundPoolBuild setQuality(int quality) {
        this.quality = quality;
        return this;
    }

    public HashMap<Integer, Integer> getSoundPool() {
        return soundPool;
    }

    public SoundPoolBuild setSoundPool(HashMap<Integer, Integer> soundPool) {
        this.soundPool = soundPool;
        return this;
    }

    public int getSoundPoolId() {
        return soundPoolId;
    }

    public void setSoundPoolId(int soundPoolId) {
        this.soundPoolId = soundPoolId;
    }
}
