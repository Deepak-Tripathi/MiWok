package com.example.deepak.miwok;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FamilyFragment extends  android.support.v4.app.Fragment {


    public FamilyFragment() {
        // Required empty public constructor
    }

    MediaPlayer player;

    private AudioManager audioManager;

    AudioManager.OnAudioFocusChangeListener afChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.

                // Pause playback and reset player to the start of the file. That way, we can
                // play the word from the beginning when we resume playback.
                player.pause();
                player.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                player.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }
        }
    };

    private MediaPlayer.OnCompletionListener mcomplete = new MediaPlayer.OnCompletionListener(){

        @Override
        public void onCompletion(MediaPlayer mediaPlayer1) {

            releaseMediaPlayer();

        }

    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);

        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> Words = new ArrayList<Word>();

        Words.add(new Word("Father" , "әpә",R.drawable.family_father,R.raw.family_father));
        Words.add(new Word("Mother" , "әṭa",R.drawable.family_mother,R.raw.family_mother));
        Words.add(new Word("Son" , "angsi",R.drawable.family_son,R.raw.family_son));
        Words.add(new Word("Daughter" , "tune",R.drawable.family_daughter,R.raw.family_daughter));
        Words.add(new Word("Older Brother" , "taachi",R.drawable.family_older_brother,R.raw.family_older_brother));
        Words.add(new Word("Younger Brother" , "chalitti",R.drawable.family_younger_brother,R.raw.family_younger_brother));
        Words.add(new Word("Older Sister" , "teṭe",R.drawable.family_older_sister,R.raw.family_older_sister));
        Words.add(new Word("Younger Sister" , "kolliti",R.drawable.family_younger_sister,R.raw.family_younger_sister));
        Words.add(new Word("Grandmother" , "ama",R.drawable.family_grandmother,R.raw.family_grandmother));
        Words.add(new Word("Grandfather" , "paapa",R.drawable.family_grandfather,R.raw.family_grandfather));



        WordAdapter itemsAdapter = new WordAdapter(getActivity(), Words,R.color.category_family);

        ListView listView = (ListView) rootView.findViewById(R.id.word_list);

        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Word word = Words.get(position);

                releaseMediaPlayer();

                int result = audioManager.requestAudioFocus(afChangeListener, AudioManager.STREAM_MUSIC, audioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    // WE HAVE AUDIO FOCUS NOW

                    player = MediaPlayer.create(getActivity(), word.getAudioResourceID());

                    player.start();

                    player.setOnCompletionListener(mcomplete);
                }
            }
        });

        return rootView;
    }

    public void releaseMediaPlayer()
    {

        if (player != null) {

            player.release();

            player = null;

            audioManager.abandonAudioFocus(afChangeListener);
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        releaseMediaPlayer();
    }
}
