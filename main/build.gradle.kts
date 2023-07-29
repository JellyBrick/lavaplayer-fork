plugins {
  `java-library`
  groovy
  `maven-publish`
}

val moduleName = "lavaplayer"
version = "1.4.2"

dependencies {
  api("com.sedmelluq:lava-common:1.1.2")
  implementation("com.github.walkyst:lavaplayer-natives-fork:1.0.2")
  implementation("com.github.walkyst.JAADec-fork:jaadec-ext-aac:0.1.3")
  implementation("org.graalvm.js:js:22.3.1")
  implementation("org.graalvm.js:js-scriptengine:22.3.1")
  implementation("org.slf4j:slf4j-api:1.7.36")

  api("org.apache.httpcomponents:httpclient:4.5.14")
  implementation("commons-io:commons-io:2.11.0")

  api("com.fasterxml.jackson.core:jackson-core:2.14.2")
  api("com.fasterxml.jackson.core:jackson-databind:2.14.2")

  implementation("org.jsoup:jsoup:1.15.4")
  implementation("net.iharder:base64:2.3.9")
  implementation("org.json:json:20230227")

  testImplementation("org.codehaus.groovy:groovy:2.5.15")
  testImplementation("org.spockframework:spock-core:1.3-groovy-2.5")
  testImplementation("ch.qos.logback:logback-classic:1.2.10")
  testImplementation("com.sedmelluq:lavaplayer-test-samples:1.3.11")
}

tasks.jar {
  exclude("natives")
}

val updateVersion by tasks.registering {
  File("$projectDir/src/main/resources/com/sedmelluq/discord/lavaplayer/tools/version.txt").let {
    it.parentFile.mkdirs()
    it.writeText(version.toString())
  }
}

tasks.classes.configure {
  finalizedBy(updateVersion)
}

val sourcesJar by tasks.registering(Jar::class) {
  archiveClassifier.set("sources")
  from(sourceSets["main"].allSource)
}

publishing {
  publications {
    create<MavenPublication>("mavenJava") {
      from(components["java"])
      artifactId = moduleName
      artifact(sourcesJar)
    }
  }
}
