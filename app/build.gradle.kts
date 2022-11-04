plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application
    id("org.openjfx.javafxplugin") version ("0.0.13")
}

group = "com.stulsoft"
version = "2.0.0"

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // This dependency is used by the application.
    implementation("org.apache.commons:commons-imaging:1.0-alpha3")
}

application {
    mainModule.set("com.stulsoft.photo.tools")
    // Define the main class for the application.
    mainClass.set("com.stulsoft.photo.tools.App")
}

javafx {
    version = "19"
    modules("javafx.controls", "javafx.fxml")
}