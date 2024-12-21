package com.jackobo.capacitor.plugins.googlepay;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.getcapacitor.PluginCall;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;

public class GooglePaymentsClientOptions {
    GooglePaymentsClientOptions(PluginCall call) {
        String env = call.getString("environment");
        this.environment = "TEST".equals(env) ? WalletConstants.ENVIRONMENT_TEST : WalletConstants.ENVIRONMENT_PRODUCTION;
        /*
        var merchantInfo = call.getObject("merchantInfo");
        this.merchantId = merchantInfo.getString("merchantId");
        this.merchantName = merchantInfo.getString("merchantName");
        var software = merchantInfo.getJSObject("softwareInfo");
        if(software != null) {
            this.merchantSoftwareId = software.getString("id");
            this.merchantSoftwareVersion = software.getString("version");
        }

         */

    }
    private int environment;

    public  PaymentsClient buildPaymentsClient(@NonNull Application application) {
        var builder = new Wallet.WalletOptions.Builder();

        builder.setEnvironment(this.environment);


        return Wallet.getPaymentsClient(application, builder.build());
    }
}
