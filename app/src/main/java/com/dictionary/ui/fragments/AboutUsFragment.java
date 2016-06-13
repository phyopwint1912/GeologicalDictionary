package com.dictionary.ui.fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.method.ScrollingMovementMethod;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dictionary.R;
import com.dictionary.util.ComFunction;

/**
 * Created by winhtaikaung on 4/1/16.
 */
public class AboutUsFragment extends Fragment {
    TextView txtAboutUs;
    TextView txtVersion;
    TextView txtDevelopername;
    TextView txtContact;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_aboutus, container, false);
        txtAboutUs = (TextView) v.findViewById(R.id.txtAboutUs);
        txtVersion = (TextView) v.findViewById(R.id.txtVersionNumber);
        txtDevelopername = (TextView) v.findViewById(R.id.txtDevelopername);
        txtContact = (TextView) v.findViewById(R.id.txtContact);

        ComFunction comFunction = new ComFunction();
        txtVersion.setText("V "+comFunction.getAppVersion(getActivity()));
        txtDevelopername.setText("Developer : "+ getString(R.string.about_us_detail));


        //Making Anchor Style in HTML
        SpannableString content = new SpannableString("phyopwinthu@gmail.com");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        txtContact.setText(content);
        txtContact.setTextColor(Color.BLUE);

        txtContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"phyopwinthu@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "About Geological Dictionary");
                i.putExtra(Intent.EXTRA_TEXT   , "Enter your comments");
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }
}
