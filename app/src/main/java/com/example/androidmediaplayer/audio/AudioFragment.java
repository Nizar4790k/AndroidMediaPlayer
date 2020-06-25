package com.example.androidmediaplayer.audio;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.androidmediaplayer.R;

import java.io.File;
import java.io.IOException;

public class AudioFragment extends Fragment  implements MediaPlayer.OnPreparedListener, MediaController.MediaPlayerControl, View.OnClickListener
{

    private MediaPlayer mediaPlayer;
    private MediaController mediaController;
    private ImageView mImageView;
    private File mAudioFile;

    private Handler handler = new Handler();

    private static String AUDIO_FILE = "com.example.androidmediaplayer.audio.AUDIO_FILE";
    private static final String TAG = "AudioPlayer";
    private View mainView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.audio_fragment,container,false);

        mImageView = mainView.findViewById(R.id.image_view_audio);
        mImageView.setImageResource(R.drawable.audio);

        mAudioFile = (File) getActivity().getIntent().getSerializableExtra(AUDIO_FILE);
        TextView textView = mainView.findViewById(R.id.now_playing_text);
        textView.setText(mAudioFile.getName());



        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);

        mainView.setOnClickListener(this);


        mediaController = new MediaController(getActivity());





        try {
            mediaPlayer.setDataSource(mAudioFile.getAbsolutePath());
            mediaPlayer.prepare();
            mediaPlayer.start();

        } catch (IOException e) {
            Log.e(TAG, "Could not open file " + mAudioFile + " for playback.", e);
        }




        return mainView;
    }



    @Override
    public void onStop() {
        super.onStop();
        mediaController.hide();
        mediaPlayer.stop();
        mediaPlayer.release();
    }



    //--MediaPlayerControl methods----------------------------------------------------
    public void start() {
        mediaPlayer.start();
    }

    public void pause() {
        mediaPlayer.pause();
    }

    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    public void seekTo(int i) {
        mediaPlayer.seekTo(i);
    }

    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    public int getBufferPercentage() {
        return 0;
    }

    public boolean canPause() {
        return true;
    }

    public boolean canSeekBackward() {
        return true;
    }

    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }
    //--------------------------------------------------------------------------------

    public void onPrepared(MediaPlayer mediaPlayer) {
        Log.d(TAG, "onPrepared");
        mediaController.setMediaPlayer(this);
        mediaController.setAnchorView(mainView);

        handler.post(new Runnable() {
            public void run() {
                mediaController.setEnabled(true);
                mediaController.show();
            }
        });
    }







            public static Intent newIntent(File file,Context context){

                Intent intent = new Intent(context,AudioActivity.class);
                intent.putExtra(AUDIO_FILE,file);

                return intent;

            }


    @Override
    public void onClick(View view) {
        mediaController.show();
    }
}
