val ktVersion = "1.3.11"
val hibernateVersion = "5.2.15.Final"
val h2Version = "1.4.197"
val springVersion = "5.1.3.RELEASE"
val springBootVersion = "2.1.1.RELEASE"
val poiVersion = "3.9"

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:2.1.1.RELEASE")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.11")
        classpath("org.jetbrains.kotlin:kotlin-allopen:1.3.11")
    }
}

plugins {
    application
    idea
    kotlin("jvm") version "1.3.11"
}
apply(plugin = "org.springframework.boot")
apply(plugin = "io.spring.dependency-management")
apply(plugin = "kotlin-spring")


configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
repositories {
    mavenCentral()
}

application {
    applicationName = "ExcelDivider"
    mainClassName = "ru.tsystems.divider.DivideRunnerStart"
}

tasks.jar {
    baseName = "ExcelDivider"
    version = "2.0"
    manifest {
        attributes (Pair("Main-Class", "ru.tsystems.divider.DivideRunnerStart"))
    }
}

dependencies {

    compile("org.springframework.boot:spring-boot-starter:$springBootVersion") {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
    }
    compile ("org.springframework.boot:spring-boot-starter-log4j2")

    compile ("org.apache.poi:poi:$poiVersion")
    compile ("org.apache.poi:poi-ooxml:$poiVersion")

    compile ("org.springframework.data:spring-data-jpa:$springBootVersion")
    compile ("org.springframework:spring-context:$springVersion")
    compile ("org.springframework:spring-context-support:$springVersion")
    compile ("org.springframework:spring-beans:$springVersion")

    compile ("org.slf4j:slf4j-log4j12:1.7.25")

    compile ("org.springframework:spring-tx:$springVersion")
    compile ("org.springframework:spring-orm:$springVersion")

    compile ("com.h2database:h2:$h2Version")
    compile ("org.hibernate:hibernate-entitymanager:$hibernateVersion")
    compile ("org.hibernate:hibernate-core:$hibernateVersion")

    testCompile (group = "org.jetbrains.kotlin", name = "kotlin-test-junit", version = ktVersion)

    compile ("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$ktVersion")
    compile ("org.jetbrains.kotlin:kotlin-reflect:$ktVersion")
}

//
//tasks.compileKotlin {
//    kotlinOptions {
//        jvmTarget = JavaVersion.VERSION_1_8
//    }
//}
//
//tasks.compileTestKotlin {
//    kotlinOptions {
//        jvmTarget = JavaVersion.VERSION_1_8
//    }
//}
