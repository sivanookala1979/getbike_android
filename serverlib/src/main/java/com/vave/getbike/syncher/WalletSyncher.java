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
        return  result;
    }

    public SaveResult redeemeToWallet(RedeemWallet redeem){
        SaveResult result = new SaveResult();
        JSONObject jsonObject = redeem.getJson();
        return  result;
    }

    public SaveResult redeemtoBank(double amount){
        SaveResult result = new SaveResult();
        return  result;
    }
    public SaveResult addMoneyToWallet(double amount){
        //TODO CCAVENUE INTEGREATION
        SaveResult result = new SaveResult();
        return  result;
    }
    public Wallet getWalletDetails(){
        Wallet wallet = new Wallet();
        return  wallet;
    }
    public SaveResult updateUserBankAccountDetails(BankAccount bankDetails){
        SaveResult result = new SaveResult();
        JSONObject jsonObject = bankDetails.getJson();
        return  result;
    } 
}
