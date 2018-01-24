package com.rootcalculator.hw1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends Activity implements View.OnClickListener {

    EditText aNum1;
    EditText bNum2;
    EditText cNum3;

    Button calButton;
    Button resButton;
    Button quitButton;

    TextView tvResult;
    TextView tvResult2;

    private AdView mAdView;
    private InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // find the elements
        aNum1 = (EditText) findViewById(R.id.editText2);
        bNum2 = (EditText) findViewById(R.id.editText);
        cNum3 = (EditText) findViewById(R.id.editText3);

        calButton = (Button) findViewById(R.id.button);
        resButton = (Button) findViewById(R.id.button3);
        quitButton = (Button) findViewById(R.id.button5);

        tvResult = (TextView) findViewById(R.id.textView4);
        tvResult2 = (TextView) findViewById(R.id.textView6);

        calButton.setOnClickListener(this);
        resButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                aNum1.setText("");
                bNum2.setText("");
                cNum3.setText("");

                tvResult.setText("root1 ");
                tvResult2.setText("root2 ");
            }
        });
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showInterstitial();
                finish();
                System.exit(0);
            }
        });
        MobileAds.initialize(this, "ca-app-pub-7816554778639642~2263961642");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        mAdView.loadAd(adRequest);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-7816554778639642/2721759669");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed(){
                super.onAdClosed();
                finish();
            }
        });

    }
    public void showInterstitial(){
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
    }

    @Override
    public void onBackPressed() {
        showInterstitial();
    }

    @Override
    public void onClick(View v) {
        double num1;
        double num2;
        double num3;
        // check if the fields are empty
        if (TextUtils.isEmpty(aNum1.getText().toString()) || TextUtils.isEmpty(bNum2.getText().toString())
                || TextUtils.isEmpty(cNum3.getText().toString())) {
            Toast.makeText(getApplicationContext(),"Please Fill all field",Toast.LENGTH_LONG).show();
            return;
        }
        // read EditText and fill variables with numbers and check if it is a number or not
        String t = aNum1.getText().toString();
        String t2 = bNum2.getText().toString();
        String t3 = cNum3.getText().toString();
        try{
            num1 = Double.parseDouble(t);
            num2 = Double.parseDouble(t2);
            num3 = Double.parseDouble(t3);

            double disk = (num2*num2-4*num1*num3);
            if (disk<0){
//            Log.d("NaN","Not a number");
                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
                dlgAlert.setMessage("NaN (Not a Number)");
                dlgAlert.setTitle("Warning Message...");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();

                dlgAlert.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
            }
            else{
                double result = (-num2 + Math.sqrt(num2*num2-4*num1*num3))/(2*num1);
                double result2 = (-num2 - Math.sqrt(num2*num2-4*num1*num3))/(2*num1);

                tvResult.setText("root1 is "+ String.format("%.2f",result));
                tvResult2.setText("root2 is "+String.format("%.2f",result2));
            }

        }catch (NumberFormatException e){
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
            dlgAlert.setMessage("Please enter number");
            dlgAlert.setTitle("Error Message...");
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();

            dlgAlert.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

        }
    }

}
