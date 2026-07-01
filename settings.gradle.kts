rootProject.name = "libby"
include(":libby-paper")
include(":libby-core")
include(":libby-bungee")
include(":libby-velocity")
project(":libby-paper").projectDir = file("paper")
project(":libby-core").projectDir = file("core")
project(":libby-bungee").projectDir = file("bungee")
project(":libby-velocity").projectDir = file("velocity")
