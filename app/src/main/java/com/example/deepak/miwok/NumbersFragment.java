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
public class NumbersFragment extends android.support.v4.app.Fragment {


    public NumbersFragment() {
        // Required empty public constructor
    }

    private MediaPlayer player;

    private AudioManager audioManager;

    private AudioManager.OnAudioFocusChangeListener afChangeListener = new AudioManager.OnAudioFocusChangeListener() {
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
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.word_list, container, false);

        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> Words = new ArrayList<Word>();

        Words.add(new Word("One", "lutti", R.drawable.number_one, R.raw.number_one));
        Words.add(new Word("Two", "otiiko", R.drawable.number_two, R.raw.number_two));
        Words.add(new Word("Three", "tolookosu", R.drawable.number_three, R.raw.number_three));
        Words.add(new Word("Four", "oyyisa", R.drawable.number_four, R.raw.number_four));
        Words.add(new Word("Five", "massokka", R.drawable.number_five, R.raw.number_five));
        Words.add(new Word("Six", "temmokka", R.drawable.number_six, R.raw.number_six));
        Words.add(new Word("Seven", "kenekaku", R.drawable.number_seven, R.raw.number_seven));
        Words.add(new Word("Eight", "kawinta", R.drawable.number_eight, R.raw.number_eight));
        Words.add(new Word("Nine", "wo’e", R.drawable.number_nine, R.raw.number_nine));
        Words.add(new Word("Ten", "na’aacha", R.drawable.number_ten, R.raw.number_ten));


        WordAdapter itemsAdapter = new WordAdapter(getActivity(), Words, R.color.category_numbers);

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
