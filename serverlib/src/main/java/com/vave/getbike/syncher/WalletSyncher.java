package com.vave.getbike.syncher;

import com.vave.getbike.model.BankAccount;
import com.vave.getbike.model.MobileRecharge;
import com.vave.getbike.model.SaveResult;
import com.vave.getbike.model.RedeemWallet;
import com.vave.getbike.model.Wallet;

import org.json.JSONObject;

/**
 * Created by adarsht on 06/12/16.
 */

public class WalletSyncher extends BaseSyncher{
    //TODO NEED TO WRITE PROPER CALLS
    public SaveResult rechargeMobile(MobileRecharge recharge){
        SaveResult result = new SaveResult();
        JSONObject jsonObject = recharge.getJson();
        result.setValid(true);
        return  result;
    }

    public SaveResult redeemeToWallet(RedeemWallet redeem){
        SaveResult result = new SaveResult();
        JSONObject jsonObject = redeem.getJson();
        result.setValid(true);
        return  result;
    }

    public SaveResult redeemtoBank(double amount){
        SaveResult result = new SaveResult();
        result.setValid(true);
        return  result;
    }
    public SaveResult addMoneyToWallet(double amount){
        //TODO CCAVENUE INTEGREATION
        SaveResult result = new SaveResult();
        result.setValid(true);

        return  result;
    }
    public Wallet getWalletDetails(){
        Wallet wallet = new Wallet();
        wallet.setCashBalance(20.0);
        wallet.setMinimumDeposit(1000.0);
        wallet.setCashBalance(500.0);
        wallet.setPromoBalance(50.0);
        wallet.setUserBalance(500.0);
        wallet.setRedeemableAmount(60.0);
        return  wallet;
    }
    public SaveResult updateUserBankAccountDetails(BankAccount bankDetails){
        SaveResult result = new SaveResult();
        JSONObject jsonObject = bankDetails.getJson();
        result.setValid(true);
        return  result;
    }

    public BankAccount getbankAccountDetails() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountHoldername("Adarsh");
        bankAccount.setAccountNumber("126554354");
        bankAccount.setIfscCode("AXISAP100034");
        bankAccount.setBranchName("Axis");
        bankAccount.setBranchName("Kavali");
        return bankAccount;
    }
}
