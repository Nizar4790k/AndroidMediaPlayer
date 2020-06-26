package com.example.androidmediaplayer.video;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.Toolbar;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.androidmediaplayer.R;

import java.io.File;

public class VideoFragment  extends Fragment {

    private static String VIDEO_FILE= "com.example.androidmediaplayer.VIDEO_FILE";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.video_fragment,container,false);

        VideoView videoView = view.findViewById(R.id.video_view);

        Intent intent = getActivity().getIntent();
        File video = (File) intent.getSerializableExtra(VIDEO_FILE);




        MediaController mediaController = new MediaController(getActivity());
        mediaController.setAnchorView(videoView);

        videoView.setMediaController(mediaController);
        videoView.setVideoURI(Uri.parse(video.toURI().toString()));
        videoView.requestFocus();
        videoView.start();


        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public static  Intent newIntent(File video, Context context){

        Intent intent = new Intent(context,VideoActivity.class);
        intent.putExtra(VIDEO_FILE,video);

        return intent;


    }

}
