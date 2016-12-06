package com.vave.getbike.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.vave.getbike.R;

/**
 * Created by adarsht on 06/12/16.
 */

public class BankAccountDetailsActivity extends BaseActivity implements View.OnClickListener{
    EditText accountHoldername,accountNumber,ifscCode,bankName,branchName;
    Button update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bank_account_details);
        accountHoldername = (EditText)findViewById(R.id.accountHolderName);
        accountNumber = (EditText)findViewById(R.id.accountHolderName);
        ifscCode = (EditText)findViewById(R.id.ifscCode);
        bankName = (EditText)findViewById(R.id.bankName);
        branchName = (EditText)findViewById(R.id.branchName);
        update = (Button)findViewById(R.id.updateBankDetails);
        update.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.updateBankDetails){

        }
    }
}
