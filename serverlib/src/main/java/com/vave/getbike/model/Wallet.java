package com.vave.getbike.model;

/**
 * Created by adarsht on 06/12/16.
 */

public class Wallet {
    double promoBalance;
    double cashBalance;
    double userBalance;
    double minimumDeposit;
    double redeemableAmount;

    public double getPromoBalance() {
        return promoBalance;
    }

    public void setPromoBalance(double promoBalance) {
        this.promoBalance = promoBalance;
    }

    public double getCashBalance() {
        return cashBalance;
    }

    public void setCashBalance(double cashBalance) {
        this.cashBalance = cashBalance;
    }

    public double getUserBalance() {
        return userBalance;
    }

    public void setUserBalance(double userBalance) {
        this.userBalance = userBalance;
    }

    public double getMinimumDeposit() {
        return minimumDeposit;
    }

    public void setMinimumDeposit(double minimumDeposit) {
        this.minimumDeposit = minimumDeposit;
    }

    public double getRedeemableAmount() {
        return redeemableAmount;
    }

    public void setRedeemableAmount(double redeemableAmount) {
        this.redeemableAmount = redeemableAmount;
    }

}
