package xyz.kyngs.libby;

import xyz.kyngs.libby.classloader.URLClassLoaderHelper;
import xyz.kyngs.libby.logging.adapters.JDKLogAdapter;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.InputStream;
import java.net.URLClassLoader;
import java.nio.file.Path;

import static java.util.Objects.requireNonNull;

/**
 * A runtime dependency manager for Bungee plugins.
 */
public class BungeeLibraryManager extends LibraryManager {
    /**
     * Plugin classpath helper
     */
    private final URLClassLoaderHelper classLoader;

    private final Plugin plugin;

    /**
     * Creates a new Bungee library manager.
     *
     * @param plugin the plugin to manage
     */
    public BungeeLibraryManager(Plugin plugin) {
        this(plugin, "lib");
    }

    /**
     * Creates a new Bungee library manager.
     *
     * @param plugin the plugin to manage
     * @param directoryName download directory name
     */
    public BungeeLibraryManager(Plugin plugin, String directoryName) {
        super(new JDKLogAdapter(requireNonNull(plugin, "plugin").getLogger()), plugin.getDataFolder().toPath(), directoryName);
        classLoader = new URLClassLoaderHelper((URLClassLoader) plugin.getClass().getClassLoader(), this);
        this.plugin = plugin;
    }

    @Override
    protected InputStream getPluginResourceAsInputStream(String path) throws UnsupportedOperationException {
        return plugin.getResourceAsStream(path);
    }

    /**
     * Adds a file to the Bungee plugin's classpath.
     *
     * @param file the file to add
     */
    @Override
    protected void addToClasspath(Path file) {
        classLoader.addToClasspath(file);
    }
}
