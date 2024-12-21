import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(CapacitorGooglePayPlugin)
public class CapacitorGooglePayPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "CapacitorGooglePayPlugin"
    public let jsName = "CapacitorGooglePay"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "initializeClient", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "isReadyToPay", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "startPayment", returnType: CAPPluginReturnPromise)
    ]

    @objc func initializeClient(_ call: CAPPluginCall) {
        call.resolve()
    }

    @objc func isReadyToPay(_ call: CAPPluginCall) {
        call.resolve([
            "result": false
        ])
    }

    @objc func startPayment(_ call: CAPPluginCall) {
        call.unavailable();
    }

}
