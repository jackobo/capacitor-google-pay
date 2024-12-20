import { WebPlugin } from '@capacitor/core';

import type {CapacitorGooglePayPlugin, InitPluginOptions} from './definitions';
import {GooglePaymentWebHandler} from "./google-payment-web-handler";



export class CapacitorGooglePayWeb extends WebPlugin implements CapacitorGooglePayPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }

  private _handler: GooglePaymentWebHandler | null = null;

  private get handler(): GooglePaymentWebHandler {
    if(!this._handler) {
      throw new Error('Call CapacitorGooglePay.init first');
    }

    return this._handler;
  }



  async init(options: InitPluginOptions): Promise<void> {
    await GooglePayScriptLoader.loadScript();

    this._handler = new GooglePaymentWebHandler(options,
        (paymentData) => this.notifyListeners('authorizePayment', paymentData));
  }

  async isReadyToPay(request: google.payments.api.IsReadyToPayRequest): Promise<google.payments.api.IsReadyToPayResponse> {
      return await this.handler.isReadyToPay(request);
  }

  async loadPaymentData(request: google.payments.api.PaymentDataRequest): Promise<google.payments.api.PaymentData> {
      return await this.handler.loadPaymentData(request);
  }

  async completeAuthorization(result: google.payments.api.PaymentAuthorizationResult): Promise<void> {
    await this.handler.completeAuthorization(result);
  }

}

class GooglePayScriptLoader {

  private static _loadScriptPromise: Promise<void> | null = null;
  static loadScript(): Promise<void> {
    if(!this._loadScriptPromise) {
      this._loadScriptPromise = GooglePayScriptLoader._loadScript().catch((err: any) => {
        console.error('Failed to load Google Pay script', err);
        this._loadScriptPromise = null;
      });
    }
    return this._loadScriptPromise;
  }

  private static _loadScript(): Promise<void> {
    return new Promise<void>((resolve, reject) => {
      const scriptElement = document.createElement('script');
      scriptElement.src = "https://pay.google.com/gp/p/js/pay.js";
      scriptElement.onload = () => {
        resolve();
      }

      scriptElement.onerror = (err: any) => {
        reject(err);
      }

      document.head.appendChild(scriptElement);
    })

  }
}