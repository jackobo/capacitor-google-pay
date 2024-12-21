# @jackobo/capacitor-google-pay

Capacitor pluging for Google Pay
Example:


## Install

```bash
npm install @jackobo/capacitor-google-pay
npm install @types/googlepay --save-dev
npx cap sync
```

## Usage

```typescript
import {CapacitorGooglePay, PaymentErrorStatusCodeEnum} from "@jackobo/capacitor-google-pay";
async function pay() {
 await CapacitorGooglePay.initializeClient({
  environment: "TEST" //or PRODUCTION
 });

 const {result} = await CapacitorGooglePay.isReadyToPay(isReadyToPayRequest);
 if (!result) { // it means Google Pay is not available
  return;
 }

 try {
  const paymentData = await CapacitorGooglePay.startPayment(startPaymentRequest)
  await callYourServer(paymentData.paymentMethodData)
 } catch (err) {
  if (err?.code !== PaymentErrorStatusCodeEnum.Canceled) {
   //show error to user here
  }
 }
}
```

## API

<docgen-index>

* [`initializeClient(...)`](#initializeclient)
* [`isReadyToPay(...)`](#isreadytopay)
* [`startPayment(...)`](#startpayment)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### initializeClient(...)

```typescript
initializeClient(options: InitializeClientOptions) => Promise<void>
```

Initialize the Google Pay PaymentsClient object. You must call this at least once before calling any other methods of the plugin.

| Param         | Type                                                                        |
| ------------- | --------------------------------------------------------------------------- |
| **`options`** | <code><a href="#initializeclientoptions">InitializeClientOptions</a></code> |

--------------------


### isReadyToPay(...)

```typescript
isReadyToPay(request: IsReadyToPayRequest) => Promise<IsReadyToPayResponse>
```

Checks if the Google Pay is available.

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`request`** | <code><a href="#isreadytopayrequest">IsReadyToPayRequest</a></code> |

**Returns:** <code>Promise&lt;<a href="#isreadytopayresponse">IsReadyToPayResponse</a>&gt;</code>

--------------------


### startPayment(...)

```typescript
startPayment(request: StartPaymentRequest) => Promise<StartPaymentResponse>
```

Starts the payment process

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`request`** | <code>PaymentDataRequest</code> |

**Returns:** <code>Promise&lt;PaymentData&gt;</code>

--------------------


### Type Aliases


#### InitializeClientOptions

It is used to initialize a PaymentClient object.
This is basically a google.payments.api.PaymentOptions object but with the paymentDataCallbacks property omitted.
Payment callbacks are not supported.
See documentation https://developers.google.com/pay/api/web/reference/request-objects#PaymentOptions

<code><a href="#omit">Omit</a>&lt;google.payments.api.PaymentOptions, 'paymentDataCallbacks'&gt;</code>


#### Omit

Construct a type with the properties of T except for those in type K.

<code><a href="#pick">Pick</a>&lt;T, <a href="#exclude">Exclude</a>&lt;keyof T, K&gt;&gt;</code>


#### Pick

From T, pick a set of properties whose keys are in the union K

<code>{
 [P in K]: T[P];
 }</code>


#### Exclude

<a href="#exclude">Exclude</a> from T those types that are assignable to U

<code>T extends U ? never : T</code>


#### IsReadyToPayRequest

This is a google.payments.api.<a href="#isreadytopayrequest">IsReadyToPayRequest</a> object.
See documentation https://developers.google.com/pay/api/web/reference/request-objects#IsReadyToPayRequest

<code>google.payments.api.<a href="#isreadytopayrequest">IsReadyToPayRequest</a></code>


#### IsReadyToPayResponse

This is a google.payments.api.<a href="#isreadytopayresponse">IsReadyToPayResponse</a>.
See documentation https://developers.google.com/pay/api/web/reference/response-objects#IsReadyToPayResponse

<code>google.payments.api.<a href="#isreadytopayresponse">IsReadyToPayResponse</a></code>


#### StartPaymentRequest

This is a google.payments.api.PaymentDataRequest object.
See documentation https://developers.google.com/pay/api/web/reference/request-objects#PaymentDataRequest

<code>google.payments.api.PaymentDataRequest</code>


#### StartPaymentResponse

This is a google.payments.api.PaymentData object.
See documentation https://developers.google.com/pay/api/web/reference/response-objects#PaymentData

<code>google.payments.api.PaymentData</code>

</docgen-api>
