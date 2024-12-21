export type InitPluginOptions = Omit<google.payments.api.PaymentOptions, 'paymentDataCallbacks'>
export enum PaymentErrorStatusCodeEnum {
  Canceled = "CANCELED"
}

export interface CapacitorGooglePayPlugin {
  initializeClient(googlePayClientOptions: InitPluginOptions): Promise<void>;
  isReadyToPay(request: google.payments.api.IsReadyToPayRequest): Promise<google.payments.api.IsReadyToPayResponse>;
  startPayment(request: google.payments.api.PaymentDataRequest): Promise<google.payments.api.PaymentData>;
}
