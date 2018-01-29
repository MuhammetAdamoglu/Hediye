package com.hediye.abra.hediye;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class OneFragment extends Fragment{


    public SharedPreferences SharedPrefences() {
        final SharedPreferences prefSettings = getActivity().getSharedPreferences("",0);
        return prefSettings;
    }
    public SharedPreferences.Editor Editor() {
        final SharedPreferences prefSettings = getActivity().getSharedPreferences("",0);
        final SharedPreferences.Editor editor = prefSettings.edit();
        return editor;
    }

    String text;
    int textsize=0;
    int color;
    int pageCount;
    Drawable drawable;

    public OneFragment(){
    }

    public OneFragment(String text, int textsize, Drawable drawable, int pageCount){
        this.text=text;
        this.pageCount=pageCount;
        this.textsize=textsize;
        this.drawable=drawable;

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public int calculateAverageColor(Bitmap bitmap, int pixelSpacing) {
        //Resmin ortak rengini bulur
        int R = 0; int G = 0; int B = 0;
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        int n = 0;
        int[] pixels = new int[width * height];

        try {
            bitmap.getPixels(pixels, 0, width, 0, 0, width, height);

            for (int i = 0; i < pixels.length; i += pixelSpacing) {
                int color = pixels[i];


                R += Color.red(color);
                G += Color.green(color);
                B += Color.blue(color);
                n++;
            }
        }catch (Exception ex){
            Toast.makeText(getContext(), "calculateAverageColor(Bitmap,int)'de Hata", Toast.LENGTH_SHORT).show();
            return Color.rgb(96,123,139);
        }

        return Color.rgb(R / n, G / n, B / n);
    }



    static TextView textView;
    ImageView ımageView;
    TypeWriter typeWriter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View view = inflater.inflate(R.layout.onefragment, container, false);

        textView= (TextView) view.findViewById(R.id.textView);
        ımageView= (ImageView) view.findViewById(R.id.imageView);

        if(drawable!=null){

            color=calculateAverageColor(((BitmapDrawable)drawable).getBitmap(),10);
            ımageView.setVisibility(View.VISIBLE);
            ımageView.setImageDrawable(drawable);
        }else{
            ımageView.setVisibility(View.GONE);
            color=Color.parseColor("#B71C1C");
        }

        textView.setTextColor(color);

        typeWriter= new TypeWriter(textView);
        textView.setText("");

        typeWriter.setCharacterDelay(65);
        typeWriter.animateText(text,color,getContext(),pageCount);

        return view;
    }
}
