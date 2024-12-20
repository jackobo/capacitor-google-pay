import type {PluginListenerHandle} from "@capacitor/core";

export type InitPluginOptions = Omit<google.payments.api.PaymentOptions, 'paymentDataCallbacks'>

export type AuthorizePaymentEventHandler = (paymentData: google.payments.api.PaymentData) => void;

export interface CapacitorGooglePayPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
  initializeClient(googlePayClientOptions: InitPluginOptions): Promise<void>;
  isReadyToPay(request: google.payments.api.IsReadyToPayRequest): Promise<google.payments.api.IsReadyToPayResponse>;
  loadPaymentData(request: google.payments.api.PaymentDataRequest): Promise<google.payments.api.PaymentData>;
  completeAuthorization(result: google.payments.api.PaymentAuthorizationResult): Promise<void>;
  addListener(eventName: 'authorizePayment', handler: AuthorizePaymentEventHandler): Promise<PluginListenerHandle>;
  removeAllListeners(): Promise<void>;
}
