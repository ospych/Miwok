package com.example.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.Objects;


public class NumberFragment extends Fragment {
    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;

    AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {

                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                            focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        mMediaPlayer.pause();
                        mMediaPlayer.seekTo(0);
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        mMediaPlayer.start();
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        releaseMediaPlayer();
                    }
                }
            };

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener(){
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);

        mAudioManager = (AudioManager) Objects.requireNonNull(getActivity()).getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<Word>();

        words.add(new Word(getString(R.string.number_one), getString(R.string.miwok_number_one),
                R.drawable.number_one, R.raw.number_one));
        words.add(new Word(getString(R.string.number_two), getString(R.string.miwok_number_two),
                R.drawable.number_two, R.raw.number_two));
        words.add(new Word(getString(R.string.number_three), getString(R.string.miwok_number_three),
                R.drawable.number_three, R.raw.number_three));
        words.add(new Word(getString(R.string.number_four), getString(R.string.miwok_number_four),
                R.drawable.number_four, R.raw.number_four));
        words.add(new Word(getString(R.string.number_five), getString(R.string.miwok_number_five),
                R.drawable.number_five, R.raw.number_five));
        words.add(new Word(getString(R.string.number_six), getString(R.string.miwok_number_six),
                R.drawable.number_six, R.raw.number_six));
        words.add(new Word(getString(R.string.number_seven), getString(R.string.miwok_number_seven),
                R.drawable.number_seven, R.raw.number_seven));
        words.add(new Word(getString(R.string.number_eight), getString(R.string.miwok_number_eight),
                R.drawable.number_eight, R.raw.number_eight));
        words.add(new Word(getString(R.string.number_nine), getString(R.string.miwok_number_nine),
                R.drawable.number_nine, R.raw.number_nine));
        words.add(new Word(getString(R.string.number_ten), getString(R.string.miwok_number_ten),
                R.drawable.number_ten, R.raw.number_ten));

        WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.category_numbers);

        ListView listView = rootView.findViewById(R.id.list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                releaseMediaPlayer(); //for release audio in the middle and start new
                Word word = words.get(i);

                int result = mAudioManager.requestAudioFocus(mAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mMediaPlayer = MediaPlayer.create(getActivity(), word.getAudioResourceId());
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });

        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;

            mAudioManager.abandonAudioFocus(mAudioFocusChangeListener);
        }
    }
}
