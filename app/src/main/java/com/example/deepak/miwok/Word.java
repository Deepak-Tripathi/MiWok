package com.example.deepak.miwok;

import android.widget.ImageView;
import android.widget.TextView;

public class Word
{
    private String mDefaulatTrans ;
    private String mMiwokTran;
    private int mImageResourceID=-1;

    private final static int NO_IMAGE=-1;

    private int mAudioResourseID;


    public Word(String DefaultTran , String MiwokTran , int AudioID)
    {
        mDefaulatTrans=DefaultTran;

        mMiwokTran = MiwokTran;

        mAudioResourseID = AudioID;
    }

    public Word(String DefaultTran , String MiwokTran , int ImageID , int AudioId)
    {
        mDefaulatTrans=DefaultTran;

        mMiwokTran = MiwokTran;

        mImageResourceID = ImageID;

        mAudioResourseID =AudioId;

    }

    public String getDefaultTranslation()
    {
        return  mDefaulatTrans;
    }

    public String getMiwokTranslation()
    {

        return mMiwokTran;
    }

    public int getmImageResourceID()
    {
      return mImageResourceID;
    }

    public boolean hasImage()
    {
        return NO_IMAGE !=mImageResourceID;
    }

    public int getAudioResourceID()
    {
        return mAudioResourseID;
    }



}

