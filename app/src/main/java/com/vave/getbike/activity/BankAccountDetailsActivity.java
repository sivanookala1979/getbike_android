package com.vave.getbike.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.vave.getbike.R;
import com.vave.getbike.helpers.GetBikeAsyncTask;
import com.vave.getbike.helpers.ToastHelper;
import com.vave.getbike.model.BankAccount;
import com.vave.getbike.model.SaveResult;
import com.vave.getbike.syncher.WalletSyncher;

/**
 * Created by adarsht on 06/12/16.
 */

public class BankAccountDetailsActivity extends BaseActivity implements View.OnClickListener {
    EditText accountHoldername, accountNumber, ifscCode, bankName, branchName;
    Button update;
    WalletSyncher walletSyncher = new WalletSyncher();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bank_account_details);
        addToolbarView();
        accountHoldername = (EditText) findViewById(R.id.accountHolderName);
        accountNumber = (EditText) findViewById(R.id.accountNumber);
        ifscCode = (EditText) findViewById(R.id.ifscCode);
        bankName = (EditText) findViewById(R.id.bankName);
        branchName = (EditText) findViewById(R.id.branchName);
        update = (Button) findViewById(R.id.updateBankDetails);
        update.setOnClickListener(this); 
        //getBankDetails();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.updateBankDetails) {
            BankAccount bankDetails = collectAccountDetaila();
            SaveResult result = bankDetails.isDataValid();
            if (result.isValid()) {
                updateBankDetails(bankDetails);
            } else {
                ToastHelper.redToast(getApplicationContext(), "Please enter valid " + result.getErrorMessage());
            }
        }
    }

    private void updateBankDetails(final BankAccount bankDetails) {
        new GetBikeAsyncTask(BankAccountDetailsActivity.this) {
            SaveResult saveResult;

            @Override
            public void process() {
                saveResult = walletSyncher.updateUserBankAccountDetails(bankDetails);
            }

            @Override
            public void afterPostExecute() {
                if (saveResult.isValid()) {
                    ToastHelper.redToast(getApplicationContext(), "Account details successfully updated.");
                } else {
                    ToastHelper.redToast(getApplicationContext(), saveResult.getErrorMessage());
                }
            }
        }.execute();
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
