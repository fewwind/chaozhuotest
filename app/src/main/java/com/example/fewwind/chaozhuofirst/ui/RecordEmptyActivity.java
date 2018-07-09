package com.example.fewwind.chaozhuofirst.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaRecorder;
import android.media.MediaScannerConnection;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.SparseIntArray;
import android.view.Display;
import android.view.Surface;
import android.view.Window;
import android.view.WindowManager;

import com.example.fewwind.chaozhuofirst.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RecordEmptyActivity extends AppCompatActivity {


    public static final String TAG = "RecordEmptyActivity";
    private static int DISPLAY_WIDTH = 1280;
    private static int DISPLAY_HEIGHT = 720;
    private static int RECORD_REQUEST_CODE = 100;
    private int mScreenDensity;
    boolean isRecording = false;
    private MediaRecorder mMediaRecorder;
    private VirtualDisplay mVirtualDisplay;
    private MediaProjection mMediaProjection;
    private MediaProjectionManager mProjectionManager;
    private MediaProjectionCallback mMediaProjectionCallback;
    private LocalReceiver localReceiver;
    private String mRecordPath;
    private static final SparseIntArray ORIENTTIONS = new SparseIntArray();
    private int mRotation;

    static {
        ORIENTTIONS.append(Surface.ROTATION_0, 0);
        ORIENTTIONS.append(Surface.ROTATION_90, 0);
        ORIENTTIONS.append(Surface.ROTATION_180, 0);
        ORIENTTIONS.append(Surface.ROTATION_270, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                , WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        setContentView(R.layout.activity_record_empty);
        setVideoSize();
        localReceiver = new LocalReceiver();
        initRecorder();
        isStartRecordScreen();
    }

    //是否开启录制
    private void isStartRecordScreen() {
        if (!isRecording) {
            recordScreen();
        } else {
            stopRecordScreen();
        }
    }

    //初始化录制参数
    private void initRecorder() {
        try {
            mMediaRecorder = new MediaRecorder();
            mProjectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);// 音频源
            mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);// 视频源
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);//视频输出格式
            mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);// 视频录制格式
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);// 音频格式
            mMediaRecorder.setVideoSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);// 设置分辨率
            mMediaRecorder.setVideoFrameRate(24);//帧率
            //这里的路径我是直接写死了。。。
            File movies = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
            if (!movies.exists()) {
                movies.mkdirs();
            }
            Date date = new Date(System.currentTimeMillis());
            SimpleDateFormat format = new SimpleDateFormat("'Octopus'_yyyyMMdd_HHmmss");
            String result = format.format(date) + ".mp4";
            mMediaRecorder.setOutputFile(mRecordPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES) + File.separator + result);//存储路径
            mMediaRecorder.setVideoEncodingBitRate(5242880);//视频清晰度
            int orientataion = ORIENTTIONS.get(mRotation);
            mMediaRecorder.setOrientationHint(orientataion);//设置旋转方向
            mMediaRecorder.prepare();
        } catch (Exception e) {
            e.printStackTrace();
            finish();
        }
    }

    //开始录制
    private void recordScreen() {
        if (mMediaProjection == null) {
            startActivityForResult(mProjectionManager.createScreenCaptureIntent(), RECORD_REQUEST_CODE);
            return;
        }

        mVirtualDisplay = createVirtualDisplay();
        mMediaRecorder.start();
        isRecording = true;
        changeState();
    }

    //停止录制
    private void stopRecordScreen() {
        destroyMediaProjection();
        finish();
        MediaScannerConnection.scanFile(getApplicationContext(), new String[]{mRecordPath}, null, null);
    }

    private VirtualDisplay createVirtualDisplay() {
        return mMediaProjection.createVirtualDisplay("ScreenRecorder", DISPLAY_WIDTH, DISPLAY_HEIGHT, mScreenDensity,
                DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR, mMediaRecorder.getSurface(), null, null);
    }

    private void changeState() {
        if (isRecording) {
        } else {
        }
    }

    //录制回调
    private class MediaProjectionCallback extends MediaProjection.Callback {
        @Override
        public void onStop() {
            stopRecordScreen();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RECORD_REQUEST_CODE) {

            if (resultCode != RESULT_OK) {
//                Toast.makeText(RecordEmptyActivity.this, "录屏权限被禁止了啊", Toast.LENGTH_SHORT).show();
                isRecording = false;
                changeState();
                stopRecordScreen();
                if (new File(mRecordPath).exists()) {
                    new File(mRecordPath).delete();
                }
                return;
            }
            mMediaProjectionCallback = new MediaProjectionCallback();
            mMediaProjection = mProjectionManager.getMediaProjection(resultCode, data);
            mMediaProjection.registerCallback(mMediaProjectionCallback, null);
            mVirtualDisplay = createVirtualDisplay();
            mMediaRecorder.start();
            isRecording = true;
            changeState();
            moveTaskToBack(true);
        }

    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyMediaProjection();
        unregisterReceiver(localReceiver);
        MediaScannerConnection.scanFile(getApplicationContext(), new String[]{mRecordPath}, null, null);
    }

    @Override
    public void overridePendingTransition(int enterAnim, int exitAnim) {
        super.overridePendingTransition(0, 0);
    }

    class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
        }
    }

    //释放录制的资源
    private void destroyMediaProjection() {
        try {
            if (isRecording) {
                mMediaRecorder.stop();
                mMediaRecorder.release();
                //            mMediaRecorder.reset();
                mMediaRecorder = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mMediaProjection != null) {
            if (mMediaProjectionCallback != null)
                mMediaProjection.unregisterCallback(mMediaProjectionCallback);
            mMediaProjection.stop();
            mMediaProjection = null;
        }
        if (mVirtualDisplay != null) {
            mVirtualDisplay.release();
            mVirtualDisplay = null;
        }
        isRecording = false;
        changeState();
    }

    private void setVideoSize() {
        mRotation = getWindowManager().getDefaultDisplay().getRotation();
        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getRealSize(point);
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        mScreenDensity = metrics.densityDpi;
        int width = point.x;
        int height = point.y;
        DISPLAY_WIDTH = Math.max(width, height);
        DISPLAY_HEIGHT = Math.min(width, height);
        if (mRotation == Surface.ROTATION_0 || mRotation == Surface.ROTATION_180) {
            DISPLAY_WIDTH = Math.min(width, height);
            DISPLAY_HEIGHT = Math.max(width, height);
        }
        if (DISPLAY_HEIGHT > 1080) {
            DISPLAY_WIDTH = (int) (DISPLAY_WIDTH * 1080.f / DISPLAY_HEIGHT);
            DISPLAY_HEIGHT = 1080;
        }
    }

}