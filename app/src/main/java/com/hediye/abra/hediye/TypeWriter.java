package com.hediye.abra.hediye;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

public class TypeWriter{
    private CharSequence mText;
    private int mIndex;
    long sleep=0;
    boolean Stop=false;
    private long mDelay = 150; // in ms
    private int say=0;
    private int say1=0;

    TextView textView;

    public TypeWriter(TextView textView){
        this.textView=textView;
    }

    private Handler mHandler = new Handler();
    private Runnable characterAdder = new Runnable() {
        @Override
        public void run() {

             try {
                    say=MainActivity.say;
                    if(say==MainActivity.nowPosition)
                        if(say==pageCount){
                            SetText();
                        }

             }catch (Exception ex){}

            mHandler.postDelayed(characterAdder, mDelay);

        }
    };


    CharSequence NewText="";
    private void SetText(){
        try {

            if(mText.equals("")){
                Editor().putInt("Pos",0).commit();
            }


            if (mIndex-1 >= mText.length()) {
                sleep=sleep+mDelay;
                if(sleep>=2000){
                    MainActivity.NextPage();
                    sleep=0;
                }
            }


            if(!Stop){
                NewText=mText.subSequence(0, mIndex++);
                textView.setText(NewText);
            }

            if(NewText.length()>0)
                if(mText.subSequence(NewText.length()-1,NewText.length()).equals(",")){
                    sleep=sleep+mDelay;
                    Stop=true;
                    if(sleep>=1000){
                        Stop=false;
                        sleep=0;
                    }
                }




        } catch (Exception e) {}
    }

    public SharedPreferences.Editor Editor() {
        final SharedPreferences prefSettings = context.getSharedPreferences("",0);
        final SharedPreferences.Editor editor = prefSettings.edit();
        return editor;
    }
    public SharedPreferences SharedPrefences() {
        final SharedPreferences prefSettings = context.getSharedPreferences("",0);
        return prefSettings;
    }

    int color;
    int pageCount;
    Context context;

    public void animateText(CharSequence txt, int color, Context context, int pageCount) {
        this.color=color;
        this.pageCount=pageCount;
        mText = txt;
        this.context=context;

        if(SharedPrefences().getInt("Pos",0)>pageCount){
            textView.setText(mText);
        }else {
            mIndex = 0;
            textView.setText("");
            mHandler.removeCallbacks(characterAdder);
            mHandler.postDelayed(characterAdder, mDelay);
        }


    }
    long savemDelay;
    public void setCharacterDelay(long m) {
        mDelay = m;
        savemDelay=m;
    }
}