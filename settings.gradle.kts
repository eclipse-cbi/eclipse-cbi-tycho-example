/*
 * This file was generated by the Gradle 'init' task.
 */

rootProject.name = "cbi.tycho.example-parent"
include(":org.eclipse.cbi.tycho.example.feature")
include(":org.eclipse.cbi.tycho.example.plugin")
include(":org.eclipse.cbi.tycho.example.updatesite")

plugins {
    id("com.gradle.develocity") version("3.18.1")
}

develocity {
    server.set("https://develocity-staging.eclipse.org")
}