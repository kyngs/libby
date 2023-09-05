plugins {
    id("net.byteflux.java-conventions")
    id("net.kyori.blossom").version("1.3.1")
}

dependencies {
    api("com.grack:nanojson:1.7")
}

blossom {
    replaceToken("@VERSION@", project.version.toString())
    replaceToken("@HTTP_USER_AGENT@", "libby/" + project.version.toString())
}

description = "libby-core"
