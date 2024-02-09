plugins {
    id("java")
    id("application")
    java
    id("org.springframework.boot") version "3.1.3"
    id("io.spring.dependency-management") version "1.1.3"
}

group = "be.kdg.domain"
version = "1.0-SNAPSHOT"

application{
    mainClass.set("be.kdg.film_project.repository.JSonWriter")
}

repositories {
    mavenCentral()
}

dependencies {
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    implementation("org.webjars:bootstrap:5.3.2")
    implementation("org.webjars:webjars-locator-core:0.48")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation ("org.springframework.boot:spring-boot-starter-jdbc")
    runtimeOnly ("com.h2database:h2")
    runtimeOnly("org.postgresql:postgresql")
    implementation("org.hibernate.validator:hibernate-validator:8.0.0.Final")
}

tasks.test {
    useJUnitPlatform()
}