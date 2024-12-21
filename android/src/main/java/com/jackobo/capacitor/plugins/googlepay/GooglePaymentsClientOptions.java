package com.jackobo.capacitor.plugins.googlepay;

import com.getcapacitor.PluginCall;
import com.google.android.gms.wallet.WalletConstants;

public class GooglePaymentsClientOptions {
    GooglePaymentsClientOptions(PluginCall call) {
        this.environment = "TEST".equals(call.getString("environment")) ? WalletConstants.ENVIRONMENT_TEST : WalletConstants.ENVIRONMENT_PRODUCTION;
        var merchantInfo = call.getObject("merchantInfo");
        this.merchantId = merchantInfo.getString("merchantId");
        this.merchantName = merchantInfo.getString("merchantName");
        var software = merchantInfo.getJSObject("softwareInfo");
        if(software != null) {
            this.merchantSoftwareId = software.getString("id");
            this.merchantSoftwareVersion = software.getString("version");
        }

    }

    private int environment;
    public int getEnvironment() {
        return this.environment;
    }

    private String merchantId;
    public  String getMerchantId() {
        return this.merchantId;
    }
    private String merchantName;
    public  String getMerchantName() {
        return this.merchantName;
    }
    private String merchantSoftwareId;
    public  String getMerchantSoftwareId() {
        return this.merchantSoftwareId;
    }
    private String merchantSoftwareVersion;
    public  String getMerchantSoftwareVersion() {
        return this.merchantSoftwareVersion;
    }
}
