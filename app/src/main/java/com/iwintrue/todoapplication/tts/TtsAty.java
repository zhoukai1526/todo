package com.iwintrue.todoapplication.tts;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.iwintrue.todoapplication.R;

import java.util.Locale;

public class TtsAty extends AppCompatActivity  implements TextToSpeech.OnInitListener{


    TextToSpeech tts;
    private EditText ed_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tts_aty);
        tts = new TextToSpeech(this,this);
        ed_text = (EditText) findViewById(R.id.ed_text);
    }

    public void click(View view) {

        tts.speak(ed_text.getText().toString(),TextToSpeech.QUEUE_ADD,null,"speech");
    }

    @Override
    public void onInit(int status) {

        if(status == TextToSpeech.SUCCESS){
            int result  = tts.setLanguage(Locale.US);

            if(result!=TextToSpeech.LANG_AVAILABLE&&result!=TextToSpeech.LANG_COUNTRY_AVAILABLE){
                Log.i("main","-------暂不支持这种语言的朗读------");
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tts.shutdown();
    }
}
