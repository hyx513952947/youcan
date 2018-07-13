package top.huangguaniu.youcan.components.media;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

/**
 * Created by 侯延旭 on 2018/7/13.
 */
public class AudioRecorder {
    private static AudioRecorder audioRecorder;
    // 音频源：音频输入-麦克风
    private final static int AUDIO_INPUT = MediaRecorder.AudioSource.MIC;
    // 采样率
    // 44100是目前的标准，但是某些设备仍然支持22050，16000，11025
    // 采样频率一般共分为22.05KHz、44.1KHz、48KHz三个等级
    private final static int AUDIO_SAMPLE_RATE = 44100;
    // 音频通道 单声道
    private final static int AUDIO_CHANNEL = AudioFormat.CHANNEL_IN_MONO;
    // 音频格式：PCM编码
    private final static int AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;
    // 缓冲区大小：缓冲区字节大小
    private int bufferSizeInBytes = 0;

    AudioRecord audioRecord;
    public AudioRecorder(){
        init();
    }

    private void init() {
        int minBufferSize = AudioRecord.getMinBufferSize(AUDIO_SAMPLE_RATE,AUDIO_CHANNEL,AUDIO_ENCODING);
        audioRecord = new AudioRecord(AUDIO_INPUT,AUDIO_SAMPLE_RATE,AUDIO_CHANNEL,AUDIO_ENCODING,minBufferSize);
    }

}
