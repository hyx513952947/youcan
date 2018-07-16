package top.huangguaniu.youcan.components.media;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * Created by 侯延旭 on 2018/7/16.
 */
public class AudioTrackPlayer implements AudioPlayer {
    private static AudioTrackPlayer instance = null;
    private File filePcm;
    private int minSizeBuffer;
    private AudioTrack audioTrack;
    private int rateHZ = 44100;
    private int channelConfig = AudioFormat.CHANNEL_OUT_MONO;
    private int audioFormat = AudioFormat.ENCODING_PCM_16BIT;

    private AudioTrackPlayer() {
    }

    public static AudioTrackPlayer newInstance() {
        if (instance == null) {
            instance = new AudioTrackPlayer();
        }
        return instance;
    }

    @Override
    public void prepare(File file) {
        minSizeBuffer = AudioTrack.getMinBufferSize(rateHZ, channelConfig, audioFormat);
        audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, rateHZ, channelConfig, audioFormat,
                minSizeBuffer, AudioTrack.MODE_STREAM, 0);
        if (!file.toString().endsWith("pcm") && !file.toString().endsWith("PCM")) {
            throw new ClassCastException("AudioTrackPlayer:音频类型错误!!!");
        }
        this.filePcm = file;
    }

    boolean mIsPlaying;

    @Override
    public void play() {
        if (filePcm == null) {
            return;
        };
        new Thread(() -> {
            mIsPlaying = true;
            short[] buffer = new short[minSizeBuffer];
            try {
                DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(filePcm)));
                audioTrack.play();
                while (mIsPlaying && dis.available() > 0) {
                    int i = 0;
                    while (dis.available() > 0 && i < buffer.length) {
                        buffer[i] = dis.readShort();
                        i++;
                    }
                    audioTrack.write(buffer, 0, buffer.length);
                }
                dis.close();
            } catch (Exception e) {
                Log.e("slack", "error:" + e.getMessage());
            }
        }).start();
    }

    @Override
    public void stop() {
        mIsPlaying = false;
        audioTrack.stop();
        audioTrack.release();
    }

    @Override
    public void pause() {
        mIsPlaying = false;
        audioTrack.pause();
    }

    @Override
    public void rePlay() {
        mIsPlaying = true;
        if (audioTrack.getPlayState() == AudioTrack.PLAYSTATE_PAUSED){

        }
    }
}
