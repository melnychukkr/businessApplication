plugins {
    java
    id("org.springframework.boot") version "2.1.5.RELEASE"
    id("io.spring.dependency-management") version "1.0.7.RELEASE"
    id("com.avast.gradle.docker-compose") version "0.9.4"
}

group = "com.karolina"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
}

tasks.withType<Test> {
    useJUnitPlatform()
}

dockerCompose {
    useComposeFiles = listOf("docker-compose.yml")
    forceRecreate = true //  Recreate containers even if their configuration and image haven't changed.
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux")
//    implementation("org.springframework.data:spring-data-commons")
    // https://mvnrepository.com/artifact/org.springframework.data/spring-data-commons
//    compile group: 'org.springframework.data', name: 'spring-data-commons', version: '2.1.8.RELEASE'


    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-mongodb-reactive
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")



    testImplementation("org.mockito:mockito-core:2.21.0")
    testImplementation("org.mockito:mockito-junit-jupiter:2.23.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test"){
        exclude(group ="junit", module = "junit")
    }
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
