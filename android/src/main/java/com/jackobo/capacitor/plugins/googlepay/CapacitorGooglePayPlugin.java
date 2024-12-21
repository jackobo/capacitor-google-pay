package com.jackobo.capacitor.plugins.googlepay;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


@CapacitorPlugin(name = "CapacitorGooglePay")
public class CapacitorGooglePayPlugin extends Plugin {
    private PaymentsClient paymentsClient;
    private GooglePaymentsClientOptions clientOptions;


    @PluginMethod
    public void initializeClient(PluginCall call) {
        this.clientOptions = new GooglePaymentsClientOptions(call);
        this.paymentsClient = this.clientOptions.buildPaymentsClient(getActivity());
        call.resolve();
    }

    @PluginMethod
    public void isReadyToPay(PluginCall call) {
        if(!isPaymentsClientInitialized(call)) {
            return;
        }

        String json = call.getData().toString();
        IsReadyToPayRequest request = IsReadyToPayRequest.fromJson(json);

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
                    Exception ex = task.getException();
                    String message = ex.getMessage();
                    call.reject("Error checking readiness to pay: " + message);
                }
            }
        });
    }

    @PluginMethod
    public void startPayment(PluginCall call) {
        if(!isPaymentsClientInitialized(call)) {
            return;
        }

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                PaymentDataRequest request = PaymentDataRequest.fromJson(call.getData().toString());
                Task<PaymentData> task = paymentsClient.loadPaymentData(request);


                task.addOnCompleteListener(new OnCompleteListener<PaymentData>() {
                    @Override
                    public void onComplete(@NonNull Task<PaymentData> task) {
                        if (task.isSuccessful()) {

                            PaymentData paymentData = task.getResult();
                            String paymentDataJson = paymentData.toJson();
                            try {
                                call.resolve(new JSObject(paymentDataJson));
                            } catch (JSONException e) {
                                call.reject("Failed to deserialize paymentDataJson into a JSObject");
                            }
                        } else {
                            Exception ex = task.getException();
                            if(ex instanceof ApiException) {
                                ApiException apiException = (ApiException)ex;
                                int statusCode = apiException.getStatusCode();
                                if (statusCode == WalletConstants.ERROR_CODE_USER_CANCELLED) {
                                    JSObject canceledResult = new JSObject();
                                    canceledResult.put("statusCode", "CANCELED");
                                    call.reject("Payment authorization failed", canceledResult);
                                } else {
                                    call.reject("Payment authorization failed", apiException);
                                }
                            } else {
                                call.reject("Payment authorization failed", ex);
                            }
                        }
                    }
                });

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
}
