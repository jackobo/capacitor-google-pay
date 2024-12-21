package com.jackobo.capacitor.plugins.googlepay;

import androidx.annotation.NonNull;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.Wallet;

@CapacitorPlugin(name = "CapacitorGooglePay")
public class CapacitorGooglePayPlugin extends Plugin {

    private CapacitorGooglePay implementation = new CapacitorGooglePay();

    private PaymentsClient paymentsClient;
    private GooglePaymentsClientOptions clientOptions;
    @PluginMethod
    public void echo(PluginCall call) {
        String value = call.getString("value");

        JSObject ret = new JSObject();
        ret.put("value", implementation.echo(value));
        call.resolve(ret);
    }

    @PluginMethod
    public void initializeClient(PluginCall call) {
        this.paymentsClient = buildPaymentsClient(call);
        call.resolve();
    }

    @PluginMethod
    public void isReadyToPay(PluginCall call) {
        if(!isPaymentsClientInitialized(call)) {
            return;
        }

        IsReadyToPayRequest request = IsReadyToPayRequest.fromJson(call.getData().toString());

        Task<Boolean> task = paymentsClient.isReadyToPay(request);
        task.addOnCompleteListener(new OnCompleteListener<Boolean>() {
            @Override
            public void onComplete(@NonNull Task<Boolean> task) {
                if (task.isSuccessful()) {
                    boolean result = task.getResult();
                    JSObject response = new JSObject();
                    response.put("result", result);
                    //response.put("paymentMethodPresent")
                    call.resolve(response);
                } else {
                    call.reject("Error checking readiness to pay: " + task.getException().getMessage());
                }
            }
        });
    }

    @PluginMethod
    public void loadPaymentData(PluginCall call) {
        if(!isPaymentsClientInitialized(call)) {
            return;
        }

        PaymentDataRequest request = PaymentDataRequest.fromJson(call.getData().toString());
        Task<PaymentData> task = this.paymentsClient.loadPaymentData(request);
        
        task.addOnCompleteListener(new OnCompleteListener<PaymentData>() {
            @Override
            public void onComplete(@NonNull Task<PaymentData> task) {
                if (task.isSuccessful()) {
                    // Payment was authorized and successful
                    PaymentData paymentData = task.getResult();

                    // Process payment data (extract tokens, etc.)
                    String paymentDataJson = paymentData.toJson();
                    JSObject response = new JSObject();
                    response.put("paymentData", paymentDataJson);

                    // Resolve the plugin call with the payment data
                    call.resolve(response);

                    // You may want to verify the payment on your backend now
                    verifyPayment(paymentDataJson);

                } else {
                    // Payment failed, handle errors
                    Exception e = task.getException();
                    String errorMessage = (e != null) ? e.getMessage() : "Unknown error";
                    call.reject("Payment failed: " + errorMessage);
                }
            }
        });


    }

    private boolean isPaymentsClientInitialized(PluginCall call) {
        if(this.paymentsClient == null) {
            call.reject("You need to call first initializeClient");
            return false;
        }
        return true;
    }

    private PaymentsClient buildPaymentsClient(PluginCall call) {
        this.clientOptions = new GooglePaymentsClientOptions(call);

        var builder = new Wallet.WalletOptions.Builder();

        builder.setEnvironment(this.clientOptions.getEnvironment());

        return Wallet.getPaymentsClient(getActivity(), builder.build());
    }
}
