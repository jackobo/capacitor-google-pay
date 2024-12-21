import type {InitializeClientOptions, StartPaymentRequest, StartPaymentResponse} from "./definitions";

export class GooglePaymentWebHandler {

    constructor(paymentsClientOptions: InitializeClientOptions) {
        this._googlePayClient = this._createGooglePayClient(paymentsClientOptions);
    }

    private readonly _googlePayClient: google.payments.api.PaymentsClient | null;
    private _createGooglePayClient(paymentsClientOptions: InitializeClientOptions): google.payments.api.PaymentsClient | null{
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

    async startPayment(request: StartPaymentRequest): Promise<StartPaymentResponse> {
        try {
            return await this.googlePayClient.loadPaymentData(request);
        }catch (err: any) {
            throw new CapacitorGooglePayError(err);
        }

    }

}

class CapacitorGooglePayError extends Error {
    constructor(public readonly innerError: any) {
        super(innerError?.message ?? innerError?.statusMessage);
    }

    get code(): string | undefined {
        return this.innerError?.statusCode;
    }

}
