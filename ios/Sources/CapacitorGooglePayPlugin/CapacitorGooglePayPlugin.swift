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
        CAPPluginMethod(name: "echo", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "init", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "isReadyToPay", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "loadPaymentData", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "completeAuthorization", returnType: CAPPluginReturnPromise)
    ]
    private let implementation = CapacitorGooglePay()

    @objc func echo(_ call: CAPPluginCall) {
        let value = call.getString("value") ?? ""
        call.resolve([
            "value": implementation.echo(value)
        ])
    }

    @objc func init(_ call: CAPPluginCall) {
        call.resolve()
    }

    @objc func isReadyToPay(_ call: CAPPluginCall) {
        call.resolve([
            "result": false
        ])
    }

    @objc func loadPaymentData(_ call: CAPPluginCall) {
        call.unavailable();
    }

    @objc func completeAuthorization(_ call: CAPPluginCall) {
        call.unavailable();
    }
}
