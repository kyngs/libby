# Libby (kyngs)

Fork of [AlessioDP/libby](https://github.com/AlessioDP/libby)
## [Changes in this fork](https://github.com/AlessioDP/libby/blob/master/CHANGELOG.md)

# Libby

A runtime dependency management library for plugins running in Java-based Minecraft
server platforms.

Libraries can be downloaded from Maven repositories (or direct URLs) into a plugin's data
folder, relocated and then loaded into the plugin's classpath at runtime.

Or you can use the automatic gradle integration with [kyngs/libby-gradle-plugin](https://github.com/kyngs/libby-gradle-plugin)

### Why use runtime dependency management?

Due to file size constraints on plugin hosting services like SpigotMC, some plugins with
bundled dependencies become too large to be uploaded.

Using runtime dependency management, dependencies are downloaded and cached by the server
and don't need to be bundled with the plugin, which significantly reduces the size of the
plugin jar.

A smaller plugin jar also means shorter download times and less network strain for authors
who self-host their plugins on servers with limited bandwidth.

### Usage

Firstly, add the maven artifact, here's an example for gradle:
```kts
// Firstly add my repo
maven { url = uri("https://repo.kyngs.xyz/public/") }

// then add the dependency
implementation("xyz.kyngs.libby:libby-bukkit:1.5.0") // Replace bukkit with the platform you are using
```

Remember to **always** relocate Libby to avoid conflicts
```kts
relocate("net.byteflux.libby", "your.package.lib.libby")
```

Then, create a new LibraryManager instance
```java
// Create a library manager for a Bukkit/Spigot plugin
BukkitLibraryManager bukkitLibraryManager = new BukkitLibraryManager(plugin);

// Create a library manager for a Bungee plugin
BungeeLibraryManager bungeeLibraryManager = new BungeeLibraryManager(plugin);

// Also Nukkit, Sponge, Paper, and Velocity are supported
```

Create a Library instance with the library builder
```java
Library lib = Library.builder()
    .groupId("your{}dependency{}groupId") // "{}" is replaced with ".", useful to avoid unwanted changes made by maven-shade-plugin
    .artifactId("artifactId")
    .version("version")
     // The following are optional

     // Sets an id for the library
    .id("my-lib")
     // Relocation is applied to the downloaded jar before loading it
    .relocate("package{}to{}relocate", "the{}relocated{}package") // "{}" is replaced with ".", useful to avoid unwanted changes made by maven-shade-plugin
     // The library is loaded into an IsolatedClassLoader, which is in common between every library with the same id
    .isolatedLoad(true)
    .classifier("customClassifier")
    .checksum("Base64-encoded SHA-256 checksum")
    .build();
```

Finally, add Maven Central (or other repositories) to the library manager and download your library. To do this,
you can use the `LibraryManager#loadLibrary(Library libraryToLoad)` method, which automatically downloads and then loads the provided library.
```java
libraryManager.addMavenCentral();
libraryManager.loadLibrary(lib);
```

<details><summary>Complete code</summary>

```java
BukkitLibraryManager libraryManager = new BukkitLibraryManager(plugin);

Library lib = Library.builder()
    .groupId("your{}dependency{}groupId") // "{}" is replaced with ".", useful to avoid unwanted changes made by maven-shade-plugin
    .artifactId("artifactId")
    .version("version")
     // The following are optional

     // Sets an id for the library
    .id("my-lib")
     // Relocation is applied to the downloaded jar before loading it
    .relocate("package{}to{}relocate", "the{}relocated{}package") // "{}" is replaced with ".", useful to avoid unwanted changes made by maven-shade-plugin
     // The library is loaded into an IsolatedClassLoader, which is in common between every library with the same id
    .isolatedLoad(true)
    .classifier("customClassifier")
    .checksum("Base64-encoded SHA-256 checksum")
    .build();

libraryManager.addMavenCentral();
libraryManager.loadLibrary(lib);
```

</details>

## Credits

Special thanks to:

* [Luck](https://github.com/lucko) for [LuckPerms](https://github.com/lucko/LuckPerms)
  and its dependency management system which was the original inspiration for this project
  and another thanks for [jar-relocator](https://github.com/lucko/jar-relocator) which is
  used by Libby to perform jar relocations.
* [Glare](https://github.com/darbyjack) for convincing me (Byteflux) that I should publish this
  library instead of letting it sit around collecting dust :)
