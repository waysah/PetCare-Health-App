buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.2.1") // Use the latest stable version
        classpath(kotlin("gradle-plugin", version = "1.6.21")) // Match Kotlin version with your project
    }
}
