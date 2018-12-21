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
        attributes(Pair("Main-Class", "ru.tsystems.divider.DivideRunnerStart"))
    }
}

dependencies {

    compile(group = "org.springframework.boot", name = "spring-boot-starter", version = springBootVersion)
    compile(group = "org.springframework.boot", name = "spring-boot-starter-log4j2", version = springBootVersion)

    compile(group = "org.apache.poi", name = "poi", version = poiVersion)
    compile(group = "org.apache.poi", name = "poi-ooxml", version = poiVersion)

    compile(group = "org.springframework.data", name = "spring-data-jpa", version = springBootVersion)
    compile(group = "org.springframework", name = "spring-context", version = springVersion)
    compile(group = "org.springframework", name = "spring-context-support", version = springVersion)
    compile(group = "org.springframework", name = "spring-beans", version = springVersion)

    compile(group = "org.springframework", name = "spring-tx", version = springVersion)
    compile(group = "org.springframework", name = "spring-orm", version = springVersion)

    compile(group = "com.h2database", name = "h2", version = h2Version)
    compile(group = "org.hibernate", name = "hibernate-entitymanager", version = hibernateVersion)
    compile(group = "org.hibernate", name = "hibernate-core", version = hibernateVersion)

    testCompile(group = "org.jetbrains.kotlin", name = "kotlin-test-junit", version = ktVersion)

    compile(group = "org.jetbrains.kotlin", name = "kotlin-stdlib-jdk8", version = ktVersion)
    compile(group = "org.jetbrains.kotlin", name = "kotlin-reflect", version = ktVersion)
}