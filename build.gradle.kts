
develocity {
    buildScan {        
        obfuscation {
            username { _ -> "eclipse-cbi-bot" }
            ipAddresses { addresses -> addresses.map { _ -> "0.0.0.0" } }
        }
    }
}