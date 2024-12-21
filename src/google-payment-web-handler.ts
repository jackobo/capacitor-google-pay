import type {InitPluginOptions} from "./definitions";

export class GooglePaymentWebHandler {

    constructor(paymentsClientOptions: InitPluginOptions) {
        this._googlePayClient = this._createGooglePayClient(paymentsClientOptions);
    }

    private readonly _googlePayClient: google.payments.api.PaymentsClient | null;
    private _createGooglePayClient(paymentsClientOptions: InitPluginOptions): google.payments.api.PaymentsClient | null{
        try {

            if(!google?.payments?.api?.PaymentsClient) {
                return null;
            }

            return new google.payments.api.PaymentsClient(paymentsClientOptions);

        } catch (err) {
            console.error('Failed to load Google Pay client', err);
            return null;
        }
    }

    private get googlePayClient(): google.payments.api.PaymentsClient {
        if(this._googlePayClient) {
            return this._googlePayClient;
        }

        throw new Error('Google Pay not supported');
    }

    public async isReadyToPay(request: google.payments.api.IsReadyToPayRequest): Promise<google.payments.api.IsReadyToPayResponse> {

        if(!this._googlePayClient) {
            return {
                result: false
            }
        }

        return await this.googlePayClient.isReadyToPay(request);
    }

    async startPayment(request: google.payments.api.PaymentDataRequest): Promise<google.payments.api.PaymentData> {
        return await this.googlePayClient.loadPaymentData(request);
    }

}

