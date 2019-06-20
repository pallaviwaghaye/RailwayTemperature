package com.webakruti.railwaytemperature.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.webakruti.railwaytemperature.R;

public class AboutUsFragment extends Fragment implements View.OnClickListener{
    private View rootView;
    private LinearLayout linearLayoutContactLink;
    private TextView textViewMobileNumber;
    private TextView textViewAboutUsText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       rootView = inflater.inflate(R.layout.fragment_about_us, container, false);
       linearLayoutContactLink = (LinearLayout)rootView.findViewById(R.id.linearLayoutContactLink);
       textViewAboutUsText = (TextView)rootView.findViewById(R.id.textViewAboutUsText);

       textViewAboutUsText.setText("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");

       textViewMobileNumber = (TextView)rootView.findViewById(R.id.textViewMobileNumber);
       linearLayoutContactLink.setOnClickListener(this);
       textViewMobileNumber.setOnClickListener(this);
       return rootView;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.linearLayoutContactLink :
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse("http://www.indianrailways.gov.in/"));
                startActivity(browserIntent);
                break;

            case R.id.textViewMobileNumber :
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:9876543210"));
                startActivity(callIntent);
                break;


        }
    }
}
