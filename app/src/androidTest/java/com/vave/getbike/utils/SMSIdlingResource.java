package com.vave.getbike.utils;

import android.support.test.espresso.IdlingResource;

import com.vave.getbike.activity.LoginActivity;
import com.vave.getbike.helpers.SMSListener;

/**
 * Created by sivanookala on 03/11/16.
 */

public class SMSIdlingResource implements IdlingResource, SMSListener {

    LoginActivity loginActivity;
    boolean waiting = false;
    private ResourceCallback resourceCallback;

    public SMSIdlingResource(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
        this.loginActivity.setSmsListener(this);
    }

    @Override
    public String getName() {
        return SMSIdlingResource.class.getName();
    }

    public void waitForSms() {
        waiting = true;
    }

    @Override
    public boolean isIdleNow() {
        boolean idle = !waiting;
        if (idle) {
            resourceCallback.onTransitionToIdle();
        }
        return idle;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback resourceCallback) {
        this.resourceCallback = resourceCallback;
    }

    @Override
    public void smsReceived() {
        waiting = false;
    }
}
