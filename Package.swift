// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "JackoboCapacitorGooglePay",
    platforms: [.iOS(.v15)],
    products: [
        .library(
            name: "JackoboCapacitorGooglePay",
            targets: ["CapacitorGooglePayPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "8.0.0")
    ],
    targets: [
        .target(
            name: "CapacitorGooglePayPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/CapacitorGooglePayPlugin"),
        .testTarget(
            name: "CapacitorGooglePayPluginTests",
            dependencies: ["CapacitorGooglePayPlugin"],
            path: "ios/Tests/CapacitorGooglePayPluginTests")
    ]
)