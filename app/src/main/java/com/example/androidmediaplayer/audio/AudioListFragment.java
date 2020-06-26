package com.example.androidmediaplayer.audio;

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

public class AudioListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private AudioAdapter mAudioAdapter;






    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.video_list_fragment,container,false);

        mRecyclerView = view.findViewById(R.id.recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new SimpleItemDecorator(15));

        List<File> audioList = FileHelper.getListFiles(new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Audio"),".mp3");


        mAudioAdapter = new AudioAdapter(audioList);

        mRecyclerView.setAdapter(mAudioAdapter);


        return view;
    }

    private  class AudioAdapter extends RecyclerView.Adapter<AudioHolder> {

        private List<File> mList;

        public AudioAdapter(List<File> list){
            mList = list;
        }

        @NonNull
        @Override
        public AudioHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new AudioHolder(layoutInflater,parent);
        }

        @Override
        public void onBindViewHolder(@NonNull AudioHolder holder, int position) {

            File video = mList.get(position);
            holder.bind(video);

        }



        @Override
        public int getItemCount() {
            return mList.size();
        }
    }


    private class AudioHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mTextViewName;
        private TextView mTextViewDuration;
        private ImageView mImageViewAudioIcon;
        private File mAudio;


        public AudioHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.audio_list_item, parent, false));

            this.mTextViewName = itemView.findViewById(R.id.text_view_name);
            mTextViewDuration = itemView.findViewById(R.id.text_view_duration);
            mImageViewAudioIcon = itemView.findViewById(R.id.image_view_icon);
            itemView.setOnClickListener(this);

        }


        public void bind(File audio){
            mAudio = audio;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                this.mTextViewName.setText(Html.fromHtml("<strong>"+audio.getName()+"</strong>",0));
            }else{
                this.mTextViewName.setText(Html.fromHtml("<strong>"+audio.getName()+"</strong>"));
            }


            MediaMetadataRetriever m = new MediaMetadataRetriever();

            mImageViewAudioIcon.setImageResource(R.drawable.audio);
            m.setDataSource(audio.getPath());

            long duration = Long.parseLong(m.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
            duration = duration / Long.parseLong("1000");


            this.mTextViewDuration.setText(convertSecondsToHMmSs(duration));
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

        @Override
        public void onClick(View v) {

            Intent intent = AudioFragment.newIntent(mAudio,getContext());
            startActivity(intent);

        }
    }

}
