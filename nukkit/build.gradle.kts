/*
 * This file was generated by the Gradle 'init' task.
 */

plugins {
    id("net.byteflux.java-conventions")
}

dependencies {
    api(project(":libby-core"))
    compileOnly("cn.nukkit:nukkit:1.0-SNAPSHOT")
}

description = "libby-nukkit"