package com.vave.getbike.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.vave.getbike.R;

/**
 * Created by adarsht on 06/12/16.
 */

public class GetbikeWalletHome extends BaseActivity implements View.OnClickListener{
    TextView promoBalance,cashBalance,yourBalance;
    ListView history;
    Button redeem,addMoney;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_home_screen);
        addToolbarView();
        promoBalance = (TextView)findViewById(R.id.promoBalance);
        cashBalance = (TextView)findViewById(R.id.cashBalance);
        yourBalance = (TextView)findViewById(R.id.yourBalance);
        history = (ListView)findViewById(R.id.history);
        redeem = (Button)findViewById(R.id.redeem);
        addMoney = (Button)findViewById(R.id.addMoney);
        redeem.setOnClickListener(this);
        addMoney.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case  R.id.redeem:
                startActivity(new Intent(GetbikeWalletHome.this,RedeemAmountActivity.class));
                break;
            case R.id.addMoney:
                break;
        }
    }
}
//GetbikeWalletHome
