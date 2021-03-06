plugins {
    kotlin("multiplatform") version "1.3.72"
    id("com.github.ben-manes.versions") version "0.28.0" // gradle dependencyUpdates -Drevision=release
    id("org.jmailen.kotlinter") version "2.4.1"
    `maven-publish`
}

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}

val kotlinVersion = "1.3.72"
val slf4jVersion = "1.7.26"
val junitVersion = "5.6.2"

kotlin {
    jvm {
        val main by compilations.getting {
            kotlinOptions {
                // Setup the Kotlin compiler options for the 'main' compilation:
                jvmTarget = "1.8"
            }
        }
        val test by compilations.getting {
            kotlinOptions {
                // Setup the Kotlin compiler options for the 'main' compilation:
                jvmTarget = "1.8"
            }
        }
    }
    js {
        nodejs {
        }
    }

    sourceSets {

        val commonMain by getting {
                dependencies {
                    implementation(kotlin("stdlib-common"))
                }
            }

        val commonTest by getting {
                dependencies {
                    implementation(kotlin("test-common"))
                    implementation(kotlin("test-annotations-common"))
                    // yay kotest does multiplatform
                    implementation("io.kotest:kotest-assertions-core:4.1.3")
                }
            }

        val jvmMain by getting {

            dependencies {
                implementation(kotlin("stdlib-jdk8"))
            }
        }
        val jvmTest by getting {
            dependencies {
                runtimeOnly("org.junit.jupiter:junit-jupiter:$junitVersion")
                implementation(kotlin("test-junit"))

                implementation("org.hamcrest:hamcrest-all:1.3")

                // kotlintest runner needs this to enable logging
                implementation("org.slf4j:slf4j-api:$slf4jVersion")
                implementation("org.slf4j:jcl-over-slf4j:$slf4jVersion")
                implementation("org.slf4j:log4j-over-slf4j:$slf4jVersion")
                implementation("org.slf4j:jul-to-slf4j:$slf4jVersion")
                implementation("ch.qos.logback:logback-classic:1.2.3")

                implementation("com.google.code.gson:gson:2.8.6")
            }
        }

        val jsMain by getting {
                dependencies {
                    implementation(kotlin("stdlib-js"))
                }
        }

        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
    }
}


publishing {
    repositories {
        maven {
            url = uri("file://$projectDir/localRepo")
        }
    }
}

kotlinter {
    ignoreFailures = true
}
