package com.hediye.abra.hediye;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;


public class getElement {
    public String Text = "Sevgilinize-Arkadaşınıza-Ailenize-Ne Anlatma İstiyorsanız-Buradan Anlatın-Sizi Daha İyi Anlasınlar";

    ArrayList<Drawable> pitcures = new ArrayList<>();
    getElement(Context context){

        pitcures.add(context.getResources().getDrawable(R.drawable.wolf));
        pitcures.add(context.getResources().getDrawable(R.drawable.birtday));
        pitcures.add(context.getResources().getDrawable(R.drawable.twoheart));
        pitcures.add(context.getResources().getDrawable(R.drawable.emoji_4));
        pitcures.add(context.getResources().getDrawable(R.drawable.time));
        pitcures.add(context.getResources().getDrawable(R.drawable.blackheart));

    }
}
