package com.vave.getbike.syncher;

import com.vave.getbike.model.BankAccount;
import com.vave.getbike.model.MobileRecharge;
import com.vave.getbike.model.RedeemWallet;
import com.vave.getbike.model.SaveResult;
import com.vave.getbike.model.Wallet;
import com.vave.getbike.utils.GsonUtils;

import org.json.JSONObject;

/**
 * Created by adarsht on 06/12/16.
 */

public class WalletSyncher extends BaseSyncher {

    //TODO NEED TO WRITE PROPER CALLS
    public SaveResult rechargeMobile(MobileRecharge recharge) {
        SaveResult result = new SaveResult();
        JSONObject jsonObject = recharge.getJson();
        result.setValid(true);
        return result;
    }

    public SaveResult redeemeToWallet(RedeemWallet redeem) {
        SaveResult result = new SaveResult();
        JSONObject jsonObject = redeem.getJson();
        result.setValid(true);
        return result;
    }

    public SaveResult redeemtoBank(double amount) {
        SaveResult result = new SaveResult();
        result.setValid(true);
        return result;
    }

    public SaveResult addMoneyToWallet(double amount) {
        //TODO CCAVENUE INTEGREATION
        SaveResult result = new SaveResult();
        result.setValid(true);

        return result;
    }

    public Wallet getWalletDetails() {
        final GetBikePointer<Wallet> result = new GetBikePointer<>(null);
        new JsonGetHandler("/wallet/getBalanceAmount") {

            @Override
            protected void processResult(JSONObject jsonResult) throws Exception {
                if (jsonResult.has("result") && jsonResult.get("result").equals("success")) {
                    Wallet wallet = new Wallet();
                    wallet.setUserBalance(jsonResult.getDouble("balanceAmount"));
                    result.setValue(wallet);
                }
            }
        }.handle();
        return result.getValue();
    }

    public SaveResult updateBankAccountDetails(final BankAccount bankDetails) {
        final SaveResult result = new SaveResult();
        new JsonPostHandler("/profile/saveAccountDetails") {

            @Override
            protected void prepareRequest() {
                put("accountHolderName", bankDetails.getAccountHolderName());
                put("accountNumber", bankDetails.getAccountNumber());
                put("ifscCode", bankDetails.getIfscCode());
                put("bankName", bankDetails.getBankName());
                put("branchName", bankDetails.getBranchName());
            }

            @Override
            protected void processResult(JSONObject jsonResult) throws Exception {
                if (jsonResult.has("result") && "success".equals(jsonResult.get("result"))) {
                    result.setValid(true);
                }
            }
        }.handle();
        return result;
    }

    public BankAccount getBankAccountDetails() {
        final GetBikePointer<BankAccount> result = new GetBikePointer<>(null);
        new JsonGetHandler("/profile/getAccountDetails") {

            @Override
            protected void processResult(JSONObject jsonResult) throws Exception {
                if (jsonResult.has("result") && jsonResult.get("result").equals("success")) {
                    BankAccount bankAccount = GsonUtils.getGson().fromJson(jsonResult.get("accountDetails").toString(), BankAccount.class);
                    result.setValue(bankAccount);
                }
            }
        }.handle();
        return result.getValue();
    }
}

