package com.dictionary.ui.fragments;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dictionary.Model.Word;
import com.dictionary.R;
import com.dictionary.util.ComFunction;
import com.dictionary.util.Dac;
import com.software.shell.fab.FloatingActionButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {


    TextView txtWord;
    TextView txtType;
    TextView txtDescription;


    FloatingActionButton fabBtnFav;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_details, container, false);

        txtWord = (TextView) v.findViewById(R.id.txtWord);
        txtType = (TextView) v.findViewById(R.id.txtType);

        txtDescription = (TextView) v.findViewById(R.id.txtDescription);
        txtDescription.setMovementMethod(new ScrollingMovementMethod());

        fabBtnFav = (FloatingActionButton) v.findViewById(R.id.btn_fabfav);
        // TODO Get Bundle Element Here
        Bundle arg = getArguments();
        String word = arg.getString("word");
        String wordType = arg.getString("wordType");
        String wordDesc = arg.getString("wordDesc");
        String isFav = arg.getString("fav");
        Log.e("Fav", isFav);

        if (Boolean.valueOf(isFav)) {
            fabBtnFav.setImageDrawable(getResources().getDrawable(R.drawable.ic_fav));
        } else {
            fabBtnFav.setImageDrawable(getResources().getDrawable(R.drawable.ic_unfav_white));
        }

        // TODO Display the Element according to place
        final Word receive_word = new Word(word, wordType, wordDesc, isFav);
        display(receive_word);
        final Dac dc = new Dac();
        char firstChar = receive_word.getWord().charAt(0);
        ComFunction com = new ComFunction();
        final String table_name = com.gettableName(firstChar);

        fabBtnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Boolean.valueOf(receive_word.getFav())) {
                    String fav = "false";
                    Boolean result = dc.UpdateFav(receive_word, table_name, fav);
                    fabBtnFav.setImageDrawable(getResources().getDrawable(R.drawable.ic_unfav_white));
                    receive_word.setFav(fav);

                }else{
                    String fav = "true";
                    Boolean result = dc.UpdateFav(receive_word, table_name, fav);
                    fabBtnFav.setImageDrawable(getResources().getDrawable(R.drawable.ic_fav));
                    receive_word.setFav(fav);

                }
            }
        });


        return v;
    }

    void display(Word w) {
        txtWord.setText(w.getWord());
        if (w.getWordType().equals("n")) {
            txtType.setText("(Noun)");
        } else if (w.getWordType().equals("adj")) {
            txtType.setText("(Adjective)");
        } else if (w.getWordType().equals("prefix")) {
            txtType.setText("(Prefix)");
        } else {
            txtType.setText("(Unknown Type)");
        }
        txtDescription.setText(Html.fromHtml(w.getDesc()));
    }

}
