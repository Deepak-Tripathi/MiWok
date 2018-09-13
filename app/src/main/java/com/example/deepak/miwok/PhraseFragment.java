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
public class PhraseFragment extends android.support.v4.app.Fragment {


    public PhraseFragment() {
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

    private MediaPlayer.OnCompletionListener mcomplete = new MediaPlayer.OnCompletionListener() {

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

        Words.add(new Word("Where are you going?", "minto wuksus", R.raw.phrase_where_are_you_going));
        Words.add(new Word("What is your name?", "tinnә oyaase'nә", R.raw.phrase_what_is_your_name));
        Words.add(new Word("My name is...", "oyaaset", R.raw.phrase_my_name_is));
        Words.add(new Word("How are you feeling?", "michәksәs", R.raw.phrase_how_are_you_feeling));
        Words.add(new Word("I’m feeling good.", "kuchi achit", R.raw.phrase_im_feeling_good));
        Words.add(new Word("Are you coming?", "әәnәs'aa?", R.raw.phrase_are_you_coming));
        Words.add(new Word("Yes, I’m coming.", "hәә’ әәnәm", R.raw.phrase_yes_im_coming));
        Words.add(new Word("I’m coming.", "әәnәm", R.raw.phrase_im_coming));
        Words.add(new Word("Let’s go.", "yoowutis", R.raw.phrase_lets_go));
        Words.add(new Word("Come here.", "әnni'nem,", R.raw.phrase_come_here));


        WordAdapter itemsAdapter = new WordAdapter(getActivity(), Words, R.color.category_phrases);

        ListView listView = (ListView) rootView.findViewById(R.id.word_list);

        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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

    public void releaseMediaPlayer() {

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
