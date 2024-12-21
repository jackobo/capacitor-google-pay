import { WebPlugin } from '@capacitor/core';

import type {CapacitorGooglePayPlugin, InitPluginOptions} from './definitions';
import {GooglePaymentWebHandler} from "./google-payment-web-handler";



export class CapacitorGooglePayWeb extends WebPlugin implements CapacitorGooglePayPlugin {

  private _handler: GooglePaymentWebHandler | null = null;

  private get handler(): GooglePaymentWebHandler {
    if(!this._handler) {
      throw new Error('Call CapacitorGooglePay.init first');
    }

    return this._handler;
  }



  async initializeClient(options: InitPluginOptions): Promise<void> {
    await GooglePayScriptLoader.loadScript();

    this._handler = new GooglePaymentWebHandler(options);
  }

  async isReadyToPay(request: google.payments.api.IsReadyToPayRequest): Promise<google.payments.api.IsReadyToPayResponse> {
      return await this.handler.isReadyToPay(request);
  }

  async startPayment(request: google.payments.api.PaymentDataRequest): Promise<google.payments.api.PaymentData> {
      return await this.handler.startPayment(request);
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