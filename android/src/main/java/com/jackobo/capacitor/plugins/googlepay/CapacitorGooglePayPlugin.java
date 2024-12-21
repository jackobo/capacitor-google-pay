package com.jackobo.capacitor.plugins.googlepay;

import android.app.Activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;
import com.google.android.gms.wallet.contract.TaskResultContracts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


@CapacitorPlugin(name = "CapacitorGooglePay")
public class CapacitorGooglePayPlugin extends Plugin {
    private PaymentsClient paymentsClient;
    private GooglePaymentsClientOptions clientOptions;

    private ActivityResultLauncher<Task<PaymentData>> paymentDataLauncher;

    private PluginCall currentStartPaymentCall;

    @Override
    public void load() {
        super.load();
        paymentDataLauncher = getActivity().registerForActivityResult(new TaskResultContracts.GetPaymentDataResult(), result -> {

            if(this.currentStartPaymentCall == null) {
                return;
            }

            try {
                int statusCode = result.getStatus().getStatusCode();
                switch (statusCode) {
                    case CommonStatusCodes.SUCCESS:
                        try {
                            String paymentDataJson = result.getResult().toJson();
                            this.currentStartPaymentCall.resolve(new JSObject(paymentDataJson));
                        } catch (JSONException e) {
                            this.currentStartPaymentCall.reject("Failed to deserialize paymentDataJson into a JSObject");
                        }
                        break;
                    default:
                        this.currentStartPaymentCall.reject(result.getStatus().getStatusMessage(), CommonStatusCodes.getStatusCodeString(statusCode));
                        break;
                }
            } catch (Exception ex) {
                this.currentStartPaymentCall.reject("Payment task completion failed", ex);
            } finally {
                this.currentStartPaymentCall = null;
            }

        });
    }

    @PluginMethod
    public void initializeClient(PluginCall call) {
        this.clientOptions = new GooglePaymentsClientOptions(call);
        this.paymentsClient = this.clientOptions.buildPaymentsClient(getActivity().getApplication());
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

        currentStartPaymentCall = call;

        PaymentDataRequest request = PaymentDataRequest.fromJson(call.getData().toString());
        final Task<PaymentData> task = paymentsClient.loadPaymentData(request);
        task.addOnCompleteListener(paymentDataLauncher::launch);


    }

    private boolean isPaymentsClientInitialized(PluginCall call) {
        if(this.paymentsClient == null) {
            call.reject("You need to call first initializeClient");
            return false;
        }
        return true;
    }
}
