package net.byteflux.libby.transitive;

import net.byteflux.libby.Repositories;
import org.apache.maven.repository.internal.MavenRepositorySystemUtils;
import org.eclipse.aether.DefaultRepositorySystemSession;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.collection.CollectRequest;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.LocalRepository;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.resolution.ArtifactResult;
import org.eclipse.aether.resolution.DependencyRequest;
import org.eclipse.aether.resolution.DependencyResolutionException;
import org.eclipse.aether.resolution.DependencyResult;
import org.eclipse.aether.supplier.RepositorySystemSupplier;
import org.eclipse.aether.util.artifact.JavaScopes;
import org.eclipse.aether.util.filter.DependencyFilterUtils;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * A supplier-based helper class for providing compile scope transitive dependencies.
 *
 * @see <a href=https://github.com/apache/maven-resolver>maven-resolver</a>.
 */
class TransitiveDependencyCollector {

    /**
     * Maven repository system
     *
     * @see #newRepositorySystem()
     */
    private final RepositorySystem repositorySystem = newRepositorySystem();
    /**
     * Maven repository system session
     *
     * @see #newRepositorySystemSession(RepositorySystem)
     */
    private final RepositorySystemSession repositorySystemSession;
    /**
     * Local repository path
     *
     * @see LocalRepository
     */
    private final Path saveDirectory;

    public TransitiveDependencyCollector(Path saveDirectory) {
        this.saveDirectory = saveDirectory;
        this.repositorySystemSession = newRepositorySystemSession(repositorySystem);
    }

    /**
     * Creates a new instance of {@link RemoteRepository}
     *
     * @param url Maven repository url
     * @return New instance of {@link RemoteRepository}
     */
    public static RemoteRepository newDefaultRepository(String url) {
        return new RemoteRepository.Builder(url, "default", url).build();
    }

    /**
     * Resolves transitive dependencies of specific maven artifact. Dependencies with scope {@code JavaScopes.COMPILE}, {@code JavaScopes.RUNTIME} returned only
     *
     * @param groupId      Maven group ID
     * @param artifactId   Maven artifact ID
     * @param version      Maven dependency version
     * @param repositories Maven repositories that would be used for dependency resolvement
     * @return Transitive dependencies, exception otherwise
     * @throws DependencyResolutionException thrown if dependency doesn't exists on provided repositories
     */
    public Collection<Artifact> findTransitiveDependencies(String groupId, String artifactId, String version,
                                                           RemoteRepository... repositories) throws DependencyResolutionException {
        Artifact artifact = new DefaultArtifact(groupId, artifactId, null, "jar", version);
        List<RemoteRepository> repositoryList = Arrays.asList(repositories);

        CollectRequest collectRequest = new CollectRequest(new Dependency(artifact, JavaScopes.COMPILE), repositoryList);
        DependencyRequest dependencyRequest = new DependencyRequest(collectRequest, DependencyFilterUtils.classpathFilter(JavaScopes.COMPILE, JavaScopes.RUNTIME));

        DependencyResult dependencyResult = repositorySystem.resolveDependencies(repositorySystemSession, dependencyRequest);

        return dependencyResult.getArtifactResults().stream().filter(ArtifactResult::isResolved).map(ArtifactResult::getArtifact).collect(Collectors.toList());
    }

    /**
     * Resolves transitive dependencies of specific maven artifact. Dependencies with scope {@code JavaScopes.COMPILE}, {@code JavaScopes.RUNTIME} returned only. Searches provided repository urls only.
     *
     * @param groupId      Maven group ID
     * @param artifactId   Maven artifact ID
     * @param version      Maven dependency version
     * @param repositories Maven repositories for transitive dependencies search
     * @return Transitive dependencies, exception otherwise
     * @throws DependencyResolutionException thrown if dependency doesn't exists on provided repositories
     * @see #findTransitiveDependencies(String, String, String, RemoteRepository...)
     */
    public Collection<Artifact> findTransitiveDependencies(String groupId, String artifactId, String version, String... repositories) throws DependencyResolutionException {
        return findTransitiveDependencies(groupId, artifactId, version, Arrays.stream(repositories).map(TransitiveDependencyCollector::newDefaultRepository).toArray(RemoteRepository[]::new));
    }

    /**
     * Resolves transitive dependencies of specific maven artifact. Dependencies with scope {@code JavaScopes.COMPILE}, {@code JavaScopes.RUNTIME} returned only. Searches maven central
     * only.
     *
     * @param groupId    Maven group ID
     * @param artifactId Maven artifact ID
     * @param version    Maven dependency version
     * @return Transitive dependencies, exception otherwise
     * @throws DependencyResolutionException thrown if dependency doesn't exists on provided repositories
     * @see #findTransitiveDependencies(String, String, String, RemoteRepository...)
     */
    public Collection<Artifact> findTransitiveDependencies(String groupId, String artifactId, String version) throws DependencyResolutionException {
        return findTransitiveDependencies(groupId, artifactId, version, newDefaultRepository(Repositories.MAVEN_CENTRAL));
    }

    /**
     * Creates new session by provided {@link RepositorySystem}
     *
     * @return new session from {@link RepositorySystem}
     * @see MavenRepositorySystemUtils#newSession()
     */
    private RepositorySystemSession newRepositorySystemSession(RepositorySystem system) {
        DefaultRepositorySystemSession session = MavenRepositorySystemUtils.newSession();

        LocalRepository localRepo = new LocalRepository(saveDirectory.toAbsolutePath().toFile());
        session.setLocalRepositoryManager(system.newLocalRepositoryManager(session, localRepo));

        Properties properties = new Properties();
        properties.putAll(System.getProperties());

        session.setSystemProperties(properties);
        session.setConfigProperties(properties);

        return session;
    }

    /**
     * Creates a new repository system
     *
     * @return New supplier repository system
     * @see RepositorySystemSupplier
     */
    private RepositorySystem newRepositorySystem() {
        return new RepositorySystemSupplier().get();
    }

}
