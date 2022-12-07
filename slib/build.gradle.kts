/*
 * Copyright (c) 2022. StulSoft
 */

plugins{
    scala
    id("com.stulsoft.photo.tools.java-library-conventions")
}
repositories {
    mavenCentral()
}

dependencies {
    implementation("org.scala-lang:scala-library:2.13.10")
    implementation("org.apache.commons:commons-imaging:1.0-alpha3")
}