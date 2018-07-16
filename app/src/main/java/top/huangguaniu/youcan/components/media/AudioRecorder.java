package top.huangguaniu.youcan.components.media;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.support.annotation.NonNull;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;
import top.huangguaniu.youcan.ui.main.views.Logger;

/**
 * Created by 侯延旭 on 2018/7/13.
 */
public class AudioRecorder implements RecordManager {
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
    private AudioRecord audioRecord;

    private ThreadPoolExecutor executorService = new ThreadPoolExecutor(2, 4,
            30, TimeUnit.SECONDS, new SynchronousQueue<>(), new ThreadFactory() {
        @Override
        public Thread newThread(@NonNull Runnable r) {
            Thread thread = new Thread(r);
            thread.setName(THREADTAG.get());
            return thread;
        }
    });

    private AtomicReference<String> THREADTAG = new AtomicReference<>();

    private List<String> pcmCaches = new ArrayList<>();

    private boolean STATE_RECODING = false;

    private String wavFile;

    public AudioRecorder() {
        init();
    }

    private void init() {
        bufferSizeInBytes = AudioRecord.getMinBufferSize(AUDIO_SAMPLE_RATE, AUDIO_CHANNEL, AUDIO_ENCODING);
    }



    private void bigTolittle(String fileName,String target) throws IOException {

        File file = new File(fileName);    //filename为pcm文件，请自行设置

        InputStream in = null;
        byte[] bytes = null;
        in = new FileInputStream(file);
        bytes = new byte[in.available()];//in.available()是得到文件的字节数
        int length=bytes.length;
        while (length!=1){
            long i=  in.read(bytes,0,bytes.length);
            if(i==-1){
                break;
            }
            length-=i;
        }
        int dataLength = bytes.length;
        int shortlength = dataLength / 2;
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes, 0, dataLength);
        ShortBuffer shortBuffer = byteBuffer.order(ByteOrder.LITTLE_ENDIAN).asShortBuffer();//此处设置大小端
        short[] shorts = new short[shortlength];
        shortBuffer.get(shorts, 0, shortlength);
        File file1 = new File(target);
        FileOutputStream fos1 = new FileOutputStream(file1);
        BufferedOutputStream bos1 = new BufferedOutputStream(fos1);
        DataOutputStream dos1 = new DataOutputStream(bos1);
        for (int i = 0; i < shorts.length; i++) {
            dos1.writeShort(shorts[i]);
        }
        dos1.close();
    }


    @Override
    public void start() {
        audioRecord = new AudioRecord(AUDIO_INPUT, AUDIO_SAMPLE_RATE, AUDIO_CHANNEL, AUDIO_ENCODING, bufferSizeInBytes);
        createNewCacheFile();
        if (audioRecord.getState() == AudioRecord.ERROR || audioRecord.getState() == AudioRecord.ERROR_BAD_VALUE || audioRecord.getState()
                != AudioRecord.STATE_INITIALIZED) {
            Logger.i("没准备好！");
            return;
        }
        STATE_RECODING = true;
        THREADTAG.set("record");
        executorService.execute(() -> {
            byte[] cache = new byte[bufferSizeInBytes];
            audioRecord.startRecording();
            try {
                Sink sink = Okio.sink(new File(pcmCaches.get(pcmCaches.size() - 1)));
                BufferedSink bufferedSink = Okio.buffer(sink);
                int recordCode;
                while (STATE_RECODING) {
                    recordCode = audioRecord.read(cache, 0, bufferSizeInBytes);
                    if (recordCode == AudioRecord.ERROR_BAD_VALUE || recordCode == AudioRecord.ERROR) {
                        continue;
                    }
                    bufferedSink.write(cache);
                }
                bufferedSink.close();
                sink.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    @Override
    public void stop() {
        STATE_RECODING = false;
        if (audioRecord.getState()==AudioRecord.STATE_INITIALIZED||audioRecord.getState() == AudioRecord.RECORDSTATE_RECORDING){
            audioRecord.stop();
            mergeAllCacheFiles();
        }
    }

    private void createNewCacheFile() {
        File cache0 = FileUtil.getRecordCacheFile(String.format("%s.pcm", System.currentTimeMillis()));
        pcmCaches.add(cache0.toString());
    }

    private void mergeAllCacheFiles() {
        THREADTAG.set("merge");
        Runnable runnable = () -> {
            try {
                File mergeFile = FileUtil.getRecordFile(String.format("%s.pcm",System.currentTimeMillis()));
                BufferedSink bufferedSink = Okio.buffer(Okio.sink(mergeFile));
                for (String fileString : pcmCaches) {
                    File file = new File(fileString);
                    if (file.length() == 0) {
                        continue;
                    }
                    BufferedSource bufferedSource = Okio.buffer(Okio.source(file));
                    byte[] source = bufferedSource.readByteArray();
                    int lastFileSize = source.length;
                    bufferedSink.write(source, 0, lastFileSize);
                    bufferedSource.close();
                    file.delete();
                }
                bufferedSink.close();
                PcmToWavUtil pcmToWavUtil = new PcmToWavUtil();
                wavFile = FileUtil.getRecordFile(String.format("%s.wav",System.currentTimeMillis())).toString();
                pcmToWavUtil.pcmToWav(mergeFile.toString(),wavFile);
                File temeptFile = FileUtil.getRecordFile(String.format("%s.pcm",System.currentTimeMillis()));
                bigTolittle(mergeFile.toString(),temeptFile.toString());
                release();
                if (onRecordStateListener!=null){
                    onRecordStateListener.onFinish(wavFile);
                    onRecordStateListener.onFinishPcm(temeptFile.toString());
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        executorService.execute(runnable);
    }

    @Override
    public void release() {
        for (String fileString : pcmCaches) {
            File file = new File(fileString);
            file.delete();
        }
        pcmCaches.clear();
        audioRecord.release();
    }

    @Override
    public void cancel() {
        release();
    }

    @Override
    public void pause() {
        STATE_RECODING = false;
        if (audioRecord.getState() == AudioRecord.RECORDSTATE_RECORDING||audioRecord.getState()==AudioRecord.STATE_INITIALIZED){
            audioRecord.stop();
        }
    }

    @Override
    public void onStateChange(int state) {
        switch (state){
            case 0:
                start();
                break;
            case 1:
                pause();
                break;
            case 2:
                stop();
                break;
            case 3:
                cancel();
                break;
        }
    }


    OnRecordStateListener onRecordStateListener;

    public void setOnRecordStateListener(OnRecordStateListener onRecordStateListener) {
        this.onRecordStateListener = onRecordStateListener;
    }

    public interface OnRecordStateListener{
        void onInitErr();
        void onFinish(String fileWav);
        void onFinishPcm(String filePcm);
    }
}
