plugins {
    id("net.byteflux.java-conventions")
}

dependencies {
    api(project(":libby-core"))
    compileOnly("org.spigotmc:spigot-api:1.19.4-R0.1-SNAPSHOT")
}

description = "libby-bukkit"
