package com.example.deepak.miwok;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word>{

    private static final String LOG_TAG = WordAdapter.class.getSimpleName();

    private int listColor;

    public WordAdapter(Activity context, ArrayList<Word> Words ,int color) {

        super(context, 0, Words);

        listColor =color;

    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }


        Word currentWord = getItem(position);

        TextView MiwokTextView = (TextView) listItemView.findViewById(R.id.miwokTxt);

        MiwokTextView.setText(currentWord.getMiwokTranslation());

        TextView EngTextView = (TextView) listItemView.findViewById(R.id.engTxt);

        EngTextView.setText(currentWord.getDefaultTranslation());

        ImageView image=(ImageView) listItemView.findViewById(R.id.Image);

        if(currentWord.hasImage())
        {
            image.setImageResource(currentWord.getmImageResourceID());

            image.setVisibility(View.VISIBLE);
        }

        else
        {
            image.setVisibility(View.GONE);
        }

        View textContainer = listItemView.findViewById(R.id.text_container);

        View iconContainer = listItemView.findViewById(R.id.play_icon);
        // Find the color that the resource ID maps to
        int color = ContextCompat.getColor(getContext(), listColor);
        // Set the background color of the text container View
        textContainer.setBackgroundColor(color);

        iconContainer.setBackgroundColor(color);


        return listItemView;
    }

}

