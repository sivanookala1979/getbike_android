package com.vave.getbike.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vave.getbike.R;

public class ShareActivity extends BaseActivity implements View.OnClickListener {

    TextView shareCodeTextView;
    TextView shareOnSocialMediaTextView;
    Button whatsAppButton;
    Button emailButton;
    Button messageTextButton;
    String shareCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        addToolbarView();

        whatsAppButton = (Button) findViewById(R.id.whatsApp_button);
        emailButton = (Button) findViewById(R.id.mail_button);
        messageTextButton = (Button) findViewById(R.id.message_button);
        shareCodeTextView = (TextView) findViewById(R.id.share_code);
        shareOnSocialMediaTextView = (TextView) findViewById(R.id.share_on_social_media);
        shareCode = "ramkoti0565"; //This value need to be retrived from DB.

        shareCodeTextView.setText(shareCode);

        whatsAppButton.setOnClickListener(this);
        emailButton.setOnClickListener(this);
        messageTextButton.setOnClickListener(this);
        shareOnSocialMediaTextView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.whatsApp_button:
                Intent whatsAppIntent = new Intent();
                whatsAppIntent.setAction(Intent.ACTION_SEND);
                whatsAppIntent.setType("text/plain");
                whatsAppIntent.putExtra(Intent.EXTRA_SUBJECT, "getbike Referral Code");
                whatsAppIntent.putExtra(Intent.EXTRA_TEXT, "Welcome to getbike family our Referral code is " + shareCode);
                whatsAppIntent.setPackage("com.whatsapp");
                startActivity(whatsAppIntent);
                break;
            case R.id.mail_button:
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{""});
                email.putExtra(Intent.EXTRA_SUBJECT, "getbike Referral Code");
                email.putExtra(Intent.EXTRA_TEXT, "Welcome to getbike family our Referral code is " + shareCode);
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Choose an Email client :"));
                break;
            case R.id.message_button:
                Intent messageIntent = new Intent(Intent.ACTION_VIEW);
                messageIntent.setType("vnd.android-dir/mms-sms");
                messageIntent.putExtra("sms_body", "Welcome to getbike family our Referral code is " + shareCode);
                startActivity(messageIntent);
                break;
            case R.id.share_on_social_media:
                System.out.println("Clicked on share_on_social_media");
                Intent sharingIntent = new Intent();
                sharingIntent.setAction(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "getbike Referral Code");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, "Welcome to getbike family our Referral code is " + shareCode);
                startActivity(Intent.createChooser(sharingIntent, "Share"));
                break;
        }
    }
}
