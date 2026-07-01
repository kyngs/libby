# Libby

A runtime dependency management library for Java projects, primarily designed for Java-based Minecraft server plugins.

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

### Maven Central and other public repositories note

Libby downloads dependencies directly from remote repositories at runtime, on every server that
runs your plugin. When you point it at Maven Central (or other public repositories such as Sonatype),
you are effectively using them as a CDN to serve your dependencies to end users. This is explicitly
something these repositories do not want: their infrastructure is meant for building projects, not for
distributing artifacts at scale to production servers.

To avoid putting this load on public repositories, it is strongly recommended that you host your own
mirror of the repositories you depend on and configure Libby to download from it instead. This keeps
the traffic on infrastructure you control, and protects you from upstream availability or rate-limiting issues.

### Usage

Firstly, add the maven artifact, here's an example for gradle:
```kts
// Firstly add my repo
maven { url = uri("https://repo.kyngs.xyz/public/") }

// then add the dependency
implementation("xyz.kyngs.libby:libby-paper:2.0.0-SNAPSHOT") // Replace paper with the platform you are using
```

Remember to **always** relocate Libby to avoid conflicts
```kts
relocate("net.byteflux.libby", "your.package.lib.libby")
```

Then, create a new LibraryManager instance
```java
// Create a library manager for a Paper plugin
PaperLibraryManager bukkitLibraryManager = new PaperLibraryManager(plugin);

// Create a library manager for a Bungee plugin
BungeeLibraryManager bungeeLibraryManager = new BungeeLibraryManager(plugin);
```

Create a Library in[build.gradle.kts](../LibrePremium/Plugin/build.gradle.kts)stance with the library builder
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
PaperLibraryManager libraryManager = new PaperLibraryManager(plugin);

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

* [AlessioDP](https://github.com/AlessioDP/libby) and [Byteflux](https://github.com/Byteflux/libby) for creating the base of this library
