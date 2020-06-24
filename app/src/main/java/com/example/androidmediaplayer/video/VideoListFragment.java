package com.example.androidmediaplayer.video;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class VideoListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private MusicAdapter mMusicAdapter;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music,container,false);

        mRecyclerView = view.findViewById(R.id.recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        List<File> videoList = new ArrayList<>();


        mMusicAdapter = new MusicAdapter(videoList);

        mRecyclerView.setAdapter(mMusicAdapter);


        return view;
    }

    private  class MusicAdapter extends RecyclerView.Adapter<MusicHolder> {

        private List<File> mList;

        public MusicAdapter(List<File> list){
            mList = list;
        }

        @NonNull
        @Override
        public MusicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new MusicHolder(layoutInflater,parent);
        }

        @Override
        public void onBindViewHolder(@NonNull MusicHolder holder, int position) {

            File video = mList.get(position);
            holder.bind(video);

        }



        @Override
        public int getItemCount() {
            return mList.size();
        }
    }


    private class MusicHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mTextViewName;
        private TextView mTextViewArtist;
        private Music mMusic;


        public MusicHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.music_list_item, parent, false));

            this.mTextViewName  = itemView.findViewById(R.id.text_view_name_music);
            mTextViewArtist = itemView.findViewById(R.id.text_view_artist);
            itemView.setOnClickListener(this);

        }


        public void bind(Music music){
            mMusic = music;
            this.mTextViewName.setText(getString(R.string.song,music.getName()));
            this.mTextViewArtist.setText(getString(R.string.artist,mMusic.getArtist()));
        }

        @Override
        public void onClick(View v) {

            Intent intent  = new Intent(Intent.ACTION_VIEW, Uri.parse(mMusic.getUrl()));
            startActivity(intent);


        }
    }
}
