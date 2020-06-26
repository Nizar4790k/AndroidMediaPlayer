package com.example.androidmediaplayer.video;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidmediaplayer.FileHelper;
import com.example.androidmediaplayer.R;
import com.example.androidmediaplayer.SimpleItemDecorator;

import java.io.File;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

public class VideoListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private VideoAdapter mVideoAdapter;






    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.video_list_fragment,container,false);

        mRecyclerView = view.findViewById(R.id.recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new SimpleItemDecorator(15));



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
        private File mVideo;


        public VideoHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.video_list_item, parent, false));

            this.mTextViewName = itemView.findViewById(R.id.text_view_name);
            mTextViewDuration = itemView.findViewById(R.id.text_view_duration);
            mImageViewThumbnail = itemView.findViewById(R.id.image_view_thumbail);
            itemView.setOnClickListener(this);

        }


        public void bind(File video){
            mVideo = video;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                this.mTextViewName.setText(Html.fromHtml("<strong>"+video.getName()+"</strong>",0));
            }else{
                this.mTextViewName.setText(Html.fromHtml("<strong>"+video.getName()+"</strong>"));
            }


            Bitmap bMap = ThumbnailUtils.createVideoThumbnail(video.getAbsolutePath(),
                    MediaStore.Video.Thumbnails.MICRO_KIND);

            MediaMetadataRetriever m = new MediaMetadataRetriever();

            mImageViewThumbnail.setImageBitmap(bMap);
            m.setDataSource(video.getPath());

            long duration = Long.parseLong(m.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
            duration = duration / Long.parseLong("1000");


            this.mTextViewDuration.setText(convertSecondsToHMmSs(duration));

        }

        @Override
        public void onClick(View v) {

            Intent intent = VideoFragment.newIntent(mVideo,getContext());
            startActivity(intent);

        }

        private  String convertSecondsToHMmSs(long seconds) {
            long s = seconds % 60;
            long m = (seconds / 60) % 60;
            long h = (seconds / (60 * 60)) % 24;

            if(h==0){
                 return String.format("%02d:%02d",m,s);
            }else{
                return String.format("%d:%02d:%02d", h,m,s);
            }


        }
    }

}
