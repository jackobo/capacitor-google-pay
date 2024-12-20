import type {InitPluginOptions} from "./definitions";

export class GooglePaymentWebHandler {

    constructor(private readonly paymentOptions: InitPluginOptions,
                private readonly notifyPaymentAuthorization: (paymentData: google.payments.api.PaymentData) => void) {
        this._googlePayClient = this._createGooglePayClient();
    }

    private readonly _googlePayClient: google.payments.api.PaymentsClient | null;

    private get googlePayClient(): google.payments.api.PaymentsClient {
        if(this._googlePayClient) {
            return this._googlePayClient;
        }

        throw new Error('Google Pay not supported');
    }

    private _paymentAuthorizationResolve: null | ((value: google.payments.api.PaymentAuthorizationResult | PromiseLike<google.payments.api.PaymentAuthorizationResult>) => void) = null;

    private _onPaymentAuthorizedHandler(paymentData: google.payments.api.PaymentData): Promise<google.payments.api.PaymentAuthorizationResult> {
        return new Promise<google.payments.api.PaymentAuthorizationResult>(resolve => {
            this._paymentAuthorizationResolve = resolve;
            this.notifyPaymentAuthorization(paymentData);
        })
    }

    private _createGooglePayClient(): google.payments.api.PaymentsClient | null{
        try {

            if(!google?.payments?.api?.PaymentsClient) {
                return null;
            }

            const paymentOptions: google.payments.api.PaymentOptions = {
                ...this.paymentOptions,
                paymentDataCallbacks: {
                    onPaymentAuthorized: paymentData => this._onPaymentAuthorizedHandler(paymentData)
                }

            }

            return new google.payments.api.PaymentsClient(paymentOptions);

        } catch (err) {
            console.error('Failed to load Google Pay client', err);
            return null;
        }
    }

    public async isReadyToPay(request: google.payments.api.IsReadyToPayRequest): Promise<google.payments.api.IsReadyToPayResponse> {

        if(!this._googlePayClient) {
            return {
                result: false
            }
        }

        return await this.googlePayClient.isReadyToPay(request);
    }

    async loadPaymentData(request: google.payments.api.PaymentDataRequest): Promise<google.payments.api.PaymentData> {
        return await this.googlePayClient.loadPaymentData(request);
    }

    async completeAuthorization(result: google.payments.api.PaymentAuthorizationResult): Promise<void> {
        if(this._paymentAuthorizationResolve) {
            this._paymentAuthorizationResolve(result);
        }
    }
}

