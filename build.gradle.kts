import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    // Note, if you develop a library, you should use compose.desktop.common.
    // compose.desktop.currentOs should be used in launcher-sourceSet
    // (in a separate module for demo project and in testMain).
    // With compose.desktop.common you will also lose @Preview functionality
    implementation(compose.desktop.currentOs)
    implementation("mysql:mysql-connector-java:8.0.13")
    //navigation
    implementation("com.arkivanov.decompose:extensions-compose-jetbrains:1.0.0")
    implementation("com.arkivanov.decompose:decompose:1.0.0")
    //serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
    //loading image
    implementation("media.kamel:kamel-image:0.9.1")
    implementation("io.ktor:ktor-client-okhttp:2.3.7")
    //date and time
    implementation ("ca.gosyer:compose-material-dialogs-datetime:0.9.4")
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "OnlineSales"
            packageVersion = "1.0.0"
        }
    }
}
