/**
 * It is used to initialize a PaymentClient object.
 * This is basically a google.payments.api.PaymentOptions object but with the paymentDataCallbacks property omitted.
 * Payment callbacks are not supported.
 * See documentation https://developers.google.com/pay/api/web/reference/request-objects#PaymentOptions
 */
export type InitializeClientOptions = Omit<google.payments.api.PaymentOptions, 'paymentDataCallbacks'>;

/**
 * This is a google.payments.api.IsReadyToPayRequest object.
 * See documentation https://developers.google.com/pay/api/web/reference/request-objects#IsReadyToPayRequest
 */
export type IsReadyToPayRequest = google.payments.api.IsReadyToPayRequest;

/**
 * This is a google.payments.api.IsReadyToPayResponse.
 * See documentation https://developers.google.com/pay/api/web/reference/response-objects#IsReadyToPayResponse
 */
export type IsReadyToPayResponse = google.payments.api.IsReadyToPayResponse

/**
 * This is a google.payments.api.PaymentDataRequest object.
 * See documentation https://developers.google.com/pay/api/web/reference/request-objects#PaymentDataRequest
 */
export type StartPaymentRequest = google.payments.api.PaymentDataRequest;

/**
 * This is a google.payments.api.PaymentData object.
 * See documentation https://developers.google.com/pay/api/web/reference/response-objects#PaymentData
 */
export type StartPaymentResponse = google.payments.api.PaymentData;

/**
 * The status code in the error thrown by @see startPayment
 */
export enum PaymentErrorStatusCodeEnum {
  Canceled = "CANCELED",
  DeveloperError = "DEVELOPER_ERROR"
}

export interface CapacitorGooglePayPlugin {
  /**
   * Initialize the Google Pay PaymentsClient object. You must call this at least once before calling any other methods of the plugin.
   * @param {InitializeClientOptions} options
   */
  initializeClient(options: InitializeClientOptions): Promise<void>;

  /**
   * Checks if the Google Pay is available.
   * @param {IsReadyToPayRequest} request
   * @returns Promise<IsReadyToPayResponse>
   */
  isReadyToPay(request: IsReadyToPayRequest): Promise<IsReadyToPayResponse>;

  /**
   * Starts the payment process
   * @param {StartPaymentRequest} request
   * @returns {Promise<StartPaymentResponse>} See documentation here https://developers.google.com/pay/api/web/reference/response-objects#PaymentData
   * @example
   * @see PaymentErrorStatusCodeEnum
   */
  startPayment(request: StartPaymentRequest): Promise<StartPaymentResponse>;
}
