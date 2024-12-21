# @jackobo/capacitor-google-pay

Capacitor pluging for Google Pay

## Install

```bash
npm install @jackobo/capacitor-google-pay
npx cap sync
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
initializeClient(googlePayClientOptions: InitPluginOptions) => Promise<void>
```

| Param                        | Type                                                            |
| ---------------------------- | --------------------------------------------------------------- |
| **`googlePayClientOptions`** | <code><a href="#initpluginoptions">InitPluginOptions</a></code> |

--------------------


### isReadyToPay(...)

```typescript
isReadyToPay(request: google.payments.api.IsReadyToPayRequest) => Promise<google.payments.api.IsReadyToPayResponse>
```

| Param         | Type                             |
| ------------- | -------------------------------- |
| **`request`** | <code>IsReadyToPayRequest</code> |

**Returns:** <code>Promise&lt;IsReadyToPayResponse&gt;</code>

--------------------


### startPayment(...)

```typescript
startPayment(request: google.payments.api.PaymentDataRequest) => Promise<google.payments.api.PaymentData>
```

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`request`** | <code>PaymentDataRequest</code> |

**Returns:** <code>Promise&lt;PaymentData&gt;</code>

--------------------


### Type Aliases


#### InitPluginOptions

<code><a href="#omit">Omit</a>&lt;google.payments.api.PaymentOptions, 'paymentDataCallbacks'&gt;</code>


#### Omit

Construct a type with the properties of T except for those in type K.

<code><a href="#pick">Pick</a>&lt;T, <a href="#exclude">Exclude</a>&lt;keyof T, K&gt;&gt;</code>


#### Pick

From T, pick a set of properties whose keys are in the union K

<code>{ [P in K]: T[P]; }</code>


#### Exclude

<a href="#exclude">Exclude</a> from T those types that are assignable to U

<code>T extends U ? never : T</code>

</docgen-api>
