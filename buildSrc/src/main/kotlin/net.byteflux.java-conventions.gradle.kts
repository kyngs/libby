plugins {
    `java-library`
    `maven-publish`
}

repositories {
    mavenLocal()
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }

    maven {
        url = uri("https://hub.spigotmc.org/nexus/content/groups/public/")
    }

    maven {
        url = uri("https://repo.opencollab.dev/maven-releases")
    }

    maven {
        url = uri("https://repo.opencollab.dev/maven-snapshots")
    }

    maven {
        url = uri("https://repo.spongepowered.org/maven/")
    }

    maven {
        url = uri("https://repo.velocitypowered.com/snapshots/")
    }

    maven {
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}

group = "xyz.kyngs.libby"
version = "1.4.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    withSourcesJar()
}

publishing {
    repositories {
        maven {
            name = "kyngsRepo"
            url = uri(
                    "https://repo.kyngs.xyz/" + (if (project.version.toString()
                                    .contains("SNAPSHOT")
                    ) "snapshots" else "releases") + "/"
            )
            credentials(PasswordCredentials::class)
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
}

tasks.withType<Javadoc> {
    options.encoding = "UTF-8"
}


