package com.vave.getbike.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.vave.getbike.R;
import com.vave.getbike.helpers.GetBikeAsyncTask;
import com.vave.getbike.helpers.ToastHelper;
import com.vave.getbike.model.Wallet;
import com.vave.getbike.syncher.WalletSyncher;

/**
 * Created by adarsht on 06/12/16.
 */

public class GetBikeWalletHome extends BaseActivity implements View.OnClickListener {

    TextView promoBalance, cashBalance, yourBalance;
    ListView history;
    Button redeem, addMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_home_screen);
        addToolbarView();
        promoBalance = (TextView) findViewById(R.id.promoBalance);
        cashBalance = (TextView) findViewById(R.id.cashBalance);
        yourBalance = (TextView) findViewById(R.id.yourBalance);
        history = (ListView) findViewById(R.id.history);
        redeem = (Button) findViewById(R.id.redeem);
        addMoney = (Button) findViewById(R.id.addMoney);
        redeem.setOnClickListener(this);
        addMoney.setOnClickListener(this);
        new GetBikeAsyncTask(GetBikeWalletHome.this) {
            Wallet wallet = null;

            @Override
            public void process() {
                wallet = new WalletSyncher().getWalletDetails();
            }

            @Override
            public void afterPostExecute() {
                if (wallet != null) {
                    promoBalance.setText("" + getCurrencySymbol() + " " + wallet.getPromoBalance());
                    cashBalance.setText("" + getCurrencySymbol() + " " + wallet.getCashBalance());
                    yourBalance.setText("" + getCurrencySymbol() + " " + wallet.getUserBalance());
                } else {
                    ToastHelper.redToast(GetBikeWalletHome.this, "Failed to load the wallet details.");
                }
            }
        }.execute();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.redeem:
                startActivity(new Intent(GetBikeWalletHome.this, RedeemAmountActivity.class));
                break;
            case R.id.addMoney:
                startActivity(new Intent(GetBikeWalletHome.this, PayUPaymentActivity.class));
                break;
        }
    }
}
//GetBikeWalletHome
