package net.byteflux.libby;

import net.byteflux.libby.classloader.URLClassLoaderHelper;
import net.byteflux.libby.logging.adapters.JDKLogAdapter;
import org.bukkit.plugin.Plugin;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.Set;

import static java.util.Objects.requireNonNull;

/**
 * A runtime dependency manager for Paper Plugins. (Not to be confused with bukkit plugins loaded on paper)
 * See: <a href="https://docs.papermc.io/paper/dev/getting-started/paper-plugins">Paper docs</a>
 */
public class PaperLibraryManager extends LibraryManager {
    /**
     * Plugin classpath helper
     */
    private final URLClassLoaderHelper classLoader;
    private final URLClassLoaderHelper globalClassLoader;

    private final Plugin plugin;

    /**
     * Creates a new Paper library manager.
     *
     * @param plugin the plugin to manage
     */
    public PaperLibraryManager(Plugin plugin) {
        this(plugin, "lib");
    }

    /**
     * Creates a new Paper library manager.
     *
     * @param plugin the plugin to manage
     * @param directoryName download directory name
     */
    public PaperLibraryManager(Plugin plugin, String directoryName) {
        super(new JDKLogAdapter(requireNonNull(plugin, "plugin").getLogger()), plugin.getDataFolder().toPath(), directoryName);

        ClassLoader cl = plugin.getClass().getClassLoader();
        Class<?> paperClClazz;

        try {
             paperClClazz = Class.forName("io.papermc.paper.plugin.entrypoint.classloader.PaperPluginClassLoader");
        } catch (ClassNotFoundException e) {
            System.err.println("PaperPluginClassLoader not found, are you using Paper 1.19.3+?");
            throw new RuntimeException(e);
        }

        if (!paperClClazz.isAssignableFrom(cl.getClass())) {
            throw new RuntimeException("Plugin classloader is not a PaperPluginClassLoader, are you using paper-plugin.yml?");
        }

        Field libraryLoaderField;

        try {
            libraryLoaderField = paperClClazz.getDeclaredField("libraryLoader");
        } catch (NoSuchFieldException e) {
            System.err.println("Cannot find libraryLoader field in PaperPluginClassLoader, please open a bug report.");
            throw new RuntimeException(e);
        }

        libraryLoaderField.setAccessible(true);

        URLClassLoader libraryLoader;
        try {
            libraryLoader = (URLClassLoader) libraryLoaderField.get(cl);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e); // Should never happen
        }

        classLoader = new URLClassLoaderHelper(libraryLoader, this);
        globalClassLoader = new URLClassLoaderHelper((URLClassLoader) cl, this);
        this.plugin = plugin;
    }

    /**
     * Adds a file to the Paper plugin's library classpath.
     *
     * @param file the file to add
     */
    @Override
    protected void addToClasspath(Path file) {
        classLoader.addToClasspath(file);
    }

    /**
     * Loads a library jar into the global classpath. If the library jar
     * doesn't exist locally, it will be downloaded.
     * <p>
     * If the provided library has any relocations, they will be applied to
     * create a relocated jar and the relocated jar will be loaded instead.
     *
     * @param library the library to load
     * @see #downloadLibrary(Library)
     */
    public void loadGlobalLibrary(Library library) {
        globalClassLoader.addToClasspath(preLoadLibrary(library));
    }
    
    @Override
    protected InputStream getPluginResourceAsInputStream(String path) throws UnsupportedOperationException {
        return plugin.getResource(path);
    }
}
