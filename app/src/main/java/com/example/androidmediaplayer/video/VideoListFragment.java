package com.example.androidmediaplayer.video;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadata;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidmediaplayer.FileHelper;
import com.example.androidmediaplayer.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class VideoListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private VideoAdapter mVideoAdapter;






    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.video_fragment,container,false);

        mRecyclerView = view.findViewById(R.id.recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        List<File> musicList = FileHelper.getListFiles(new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Videos"),".mp4");


        mVideoAdapter = new VideoAdapter(musicList);

        mRecyclerView.setAdapter(mVideoAdapter);


        return view;
    }

    private  class VideoAdapter extends RecyclerView.Adapter<VideoHolder> {

        private List<File> mList;

        public VideoAdapter(List<File> list){
            mList = list;
        }

        @NonNull
        @Override
        public VideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new VideoHolder(layoutInflater,parent);
        }

        @Override
        public void onBindViewHolder(@NonNull VideoHolder holder, int position) {

            File video = mList.get(position);
            holder.bind(video);

        }



        @Override
        public int getItemCount() {
            return mList.size();
        }
    }


    private class VideoHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mTextViewName;
        private TextView mTextViewDuration;
        private ImageView mImageViewThumbnail;
        private File video;


        public VideoHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.video_list_item, parent, false));

            this.mTextViewName = itemView.findViewById(R.id.text_view_name);
            mTextViewDuration = itemView.findViewById(R.id.text_view_duration);
            mImageViewThumbnail = itemView.findViewById(R.id.image_view_thumbail);
            itemView.setOnClickListener(this);

        }


        public void bind(File video){
            video = video;
            this.mTextViewName.setText(video.getName());


            Bitmap bMap = ThumbnailUtils.createVideoThumbnail(video.getAbsolutePath(),
                    MediaStore.Video.Thumbnails.MICRO_KIND);

            MediaMetadataRetriever m = new MediaMetadataRetriever();

            mImageViewThumbnail.setImageBitmap(bMap);
            m.setDataSource(video.getPath());

            String  duration = m.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);

            this.mTextViewDuration.setText(duration);

        }

        @Override
        public void onClick(View v) {



        }
    }

}
