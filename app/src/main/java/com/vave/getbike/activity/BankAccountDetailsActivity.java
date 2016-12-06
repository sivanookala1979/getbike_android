package com.vave.getbike.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.vave.getbike.R;
import com.vave.getbike.helpers.ToastHelper;
import com.vave.getbike.model.BankAccount;
import com.vave.getbike.model.SaveResult;

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
            BankAccount bankDetails = collectAccountDetaila();
            SaveResult result = bankDetails.isDataValid();
            if(result.isValid()){

            }else {
                ToastHelper.redToast(getApplicationContext(),""+result.getErrorMessage());
            }
        }
    }

    private BankAccount collectAccountDetaila() {
        BankAccount bankDetails = new BankAccount();
        bankDetails.setAccountHoldername(accountHoldername.getText().toString());
        bankDetails.setAccountNumber(accountNumber.getText().toString());
        bankDetails.setIfscCode(ifscCode.getText().toString());
        bankDetails.setBankName(bankName.getText().toString());
        bankDetails.setBranchName(branchName.getText().toString());
        return bankDetails;
    }
}
