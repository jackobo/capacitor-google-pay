# @jackobo/capacitor-google-pay

Capacitor pluging for Google Pay

## Install

```bash
npm install @jackobo/capacitor-google-pay
npx cap sync
```

## API

<docgen-index>

* [`echo(...)`](#echo)
* [`init(...)`](#init)
* [`isReadyToPay(...)`](#isreadytopay)
* [`loadPaymentData(...)`](#loadpaymentdata)
* [`completeAuthorization(...)`](#completeauthorization)
* [`addListener('authorizePayment', ...)`](#addlistenerauthorizepayment-)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### echo(...)

```typescript
echo(options: { value: string; }) => Promise<{ value: string; }>
```

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ value: string; }</code> |

**Returns:** <code>Promise&lt;{ value: string; }&gt;</code>

--------------------


### init(...)

```typescript
init(googlePayClientOptions: InitPluginOptions) => Promise<void>
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


### loadPaymentData(...)

```typescript
loadPaymentData(request: google.payments.api.PaymentDataRequest) => Promise<google.payments.api.PaymentData>
```

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`request`** | <code>PaymentDataRequest</code> |

**Returns:** <code>Promise&lt;PaymentData&gt;</code>

--------------------


### completeAuthorization(...)

```typescript
completeAuthorization(result: google.payments.api.PaymentAuthorizationResult) => Promise<void>
```

| Param        | Type                                    |
| ------------ | --------------------------------------- |
| **`result`** | <code>PaymentAuthorizationResult</code> |

--------------------


### addListener('authorizePayment', ...)

```typescript
addListener(eventName: 'authorizePayment', handler: AuthorizePaymentEventHandler) => Promise<PluginListenerHandle>
```

| Param           | Type                                                                                  |
| --------------- | ------------------------------------------------------------------------------------- |
| **`eventName`** | <code>'authorizePayment'</code>                                                       |
| **`handler`**   | <code><a href="#authorizepaymenteventhandler">AuthorizePaymentEventHandler</a></code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

--------------------


### removeAllListeners()

```typescript
removeAllListeners() => Promise<void>
```

--------------------


### Interfaces


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


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


#### AuthorizePaymentEventHandler

<code>(paymentData: PaymentData): void</code>

</docgen-api>
