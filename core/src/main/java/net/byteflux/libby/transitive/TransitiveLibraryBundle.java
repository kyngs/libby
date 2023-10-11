package net.byteflux.libby.transitive;

import net.byteflux.libby.Library;
import net.byteflux.libby.Repositories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Represents a bundle of libraries required for Maven transitive dependency resolution and related operations.
 * <p>
 * This class bundles Maven Resolver Supplier, Maven Resolver Provider and their transitive dependencies.
 * The libraries are statically defined and stored in the DEPENDENCY_BUNDLE collection for easy access
 * and use in Maven-related operations.
 */
class TransitiveLibraryBundle {
    private static final Library MAVEN_RESOLVER_SUPPLIER = Library.builder()
                                                                  .groupId("org.apache.maven.resolver")
                                                                  .artifactId("maven-resolver-supplier")
                                                                  .version("1.9.15")
                                                                  .checksum("BNncFRDRqBBHWixE0DYfFydh7h9bc3mhC/MBr5WD448")
                                                                  .repository(Repositories.MAVEN_CENTRAL)
                                                                  .build();
    private static final Library MAVEN_RESOLVER_API = Library.builder()
                                                             .groupId("org.apache.maven.resolver")
                                                             .artifactId("maven-resolver-api")
                                                             .version("1.9.15")
                                                             .checksum("1Ugp1gooJ7uXYxK9u/YnlR5hMpJ2LuFGFAxRqn3OYL4")
                                                             .repository(Repositories.MAVEN_CENTRAL)
                                                             .build();
    private static final Library MAVEN_RESOLVER_UTIL = Library.builder()
                                                              .groupId("org.apache.maven.resolver")
                                                              .artifactId("maven-resolver-util")
                                                              .version("1.9.15")
                                                              .checksum("z9Gnzg0gpWHtZrI18lpvwa5A/f8Zr6k1GrCpZNTlVm8")
                                                              .repository(Repositories.MAVEN_CENTRAL)
                                                              .build();
    private static final Library MAVEN_RESOLVER_SPI = Library.builder()
                                                             .groupId("org.apache.maven.resolver")
                                                             .artifactId("maven-resolver-spi")
                                                             .version("1.9.15")
                                                             .checksum("V2xyiu6a+awHPayBvrdsgZ4zGMIzeRL0Xr7xmD5pv6U")
                                                             .repository(Repositories.MAVEN_CENTRAL)
                                                             .build();
    private static final Library MAVEN_RESOLVER_NAMED_LOCKS = Library.builder()
                                                                     .groupId("org.apache.maven.resolver")
                                                                     .artifactId("maven-resolver-named-locks")
                                                                     .version("1.9.15")
                                                                     .checksum("/8bnO65ieplawVG0wI2v/K2cOltLHGo/8wL53dpMGB4")
                                                                     .repository(Repositories.MAVEN_CENTRAL)
                                                                     .build();
    private static final Library MAVEN_RESOLVER_IMPL = Library.builder()
                                                              .groupId("org.apache.maven.resolver")
                                                              .artifactId("maven-resolver-impl")
                                                              .version("1.9.15")
                                                              .checksum("APYerOvm4uPbNrGQ/72JvfZJXTugrtmCI2nYYx0Ctr4")
                                                              .repository(Repositories.MAVEN_CENTRAL)
                                                              .build();
    private static final Library MAVEN_RESOLVER_CONNECTOR_BASIC = Library.builder()
                                                                         .groupId("org.apache.maven.resolver")
                                                                         .artifactId("maven-resolver-connector-basic")
                                                                         .version("1.9.15")
                                                                         .checksum("NrdEncaUGNjstCH8BqckU5gNzFbVfbPDQ7FvRMeXcmw")
                                                                         .repository(Repositories.MAVEN_CENTRAL)
                                                                         .build();
    private static final Library MAVEN_RESOLVER_TRANSPORT_FILE = Library.builder()
                                                                        .groupId("org.apache.maven.resolver")
                                                                        .artifactId("maven-resolver-transport-file")
                                                                        .version("1.9.15")
                                                                        .checksum("JAJAXaKiQyTVsVAJyc9l/CKjsNZirxUzgxw7ix7Fzus")
                                                                        .repository(Repositories.MAVEN_CENTRAL)
                                                                        .build();
    private static final Library MAVEN_RESOLVER_TRANSPORT_HTTP = Library.builder()
                                                                        .groupId("org.apache.maven.resolver")
                                                                        .artifactId("maven-resolver-transport-http")
                                                                        .version("1.9.15")
                                                                        .checksum("kDEw+zmNvtsTZM3tdxe6OqRAhKHkAxYM+xfn7L3E5Fk")
                                                                        .repository(Repositories.MAVEN_CENTRAL)
                                                                        .build();
    private static final Library HTTPCLIENT = Library.builder()
                                                     .groupId("org.apache.httpcomponents")
                                                     .artifactId("httpclient")
                                                     .version("4.5.14")
                                                     .checksum("yLx+HFGm1M5y9A0uu6vxxLaL/nbnMhBLBDgbSTR46dY")
                                                     .repository(Repositories.MAVEN_CENTRAL)
                                                     .build();
    private static final Library HTTPCORE = Library.builder()
                                                   .groupId("org.apache.httpcomponents")
                                                   .artifactId("httpcore")
                                                   .version("4.4.16")
                                                   .checksum("bJs90UKgncRo4jrTmq1vdaDyuFElEERp8CblKkdORk8")
                                                   .repository(Repositories.MAVEN_CENTRAL)
                                                   .build();
    private static final Library COMMONS_CODEC = Library.builder()
                                                        .groupId("commons-codec")
                                                        .artifactId("commons-codec")
                                                        .version("1.16.0")
                                                        .checksum("VllfsgsLhbyR0NUD2tULt/G5r8Du1d/6bLslkpAASE0")
                                                        .repository(Repositories.MAVEN_CENTRAL)
                                                        .build();
    private static final Library JCL_OVER_SLF4J = Library.builder()
                                                         .groupId("org.slf4j")
                                                         .artifactId("jcl-over-slf4j")
                                                         .version("1.7.36")
                                                         .checksum("q1fKj9IjdywXNl0SH1npTsvwrlnQjAOjy1uBBxwBkZU")
                                                         .repository(Repositories.MAVEN_CENTRAL)
                                                         .build();
    private static final Library MAVEN_MODEL_BUILDER = Library.builder()
                                                              .groupId("org.apache.maven")
                                                              .artifactId("maven-model-builder")
                                                              .version("3.9.4")
                                                              .checksum("gVsYhtHsjh2LV7YeasgyNb4/amAVnI7lpvTpH1E/SX8")
                                                              .repository(Repositories.MAVEN_CENTRAL)
                                                              .build();
    private static final Library PLEXUS_INTERPOLATION = Library.builder()
                                                               .groupId("org.codehaus.plexus")
                                                               .artifactId("plexus-interpolation")
                                                               .version("1.26")
                                                               .checksum("s7VBLOF4iRA+pWS838+fs9+lQDRP/qxrU4pzydcYJmI")
                                                               .repository(Repositories.MAVEN_CENTRAL)
                                                               .build();
    private static final Library MAVEN_ARTIFACT = Library.builder()
                                                         .groupId("org.apache.maven")
                                                         .artifactId("maven-artifact")
                                                         .version("3.9.4")
                                                         .checksum("fdNS/Z+P+GodCn2J5iidjTzTRqybIU7YWGjVhb4Fq3g")
                                                         .repository(Repositories.MAVEN_CENTRAL)
                                                         .build();
    private static final Library COMMONS_LANG3 = Library.builder()
                                                        .groupId("org.apache.commons")
                                                        .artifactId("commons-lang3")
                                                        .version("3.12.0")
                                                        .checksum("2RnZBEhsA3+NGTQS2gyS4iqfokIwudZ6V4VcXDHH6U4")
                                                        .repository(Repositories.MAVEN_CENTRAL)
                                                        .build();
    private static final Library MAVEN_BUILDER_SUPPORT = Library.builder()
                                                                .groupId("org.apache.maven")
                                                                .artifactId("maven-builder-support")
                                                                .version("3.9.4")
                                                                .checksum("hpRvsGyyBVFVObk7AFw7uSi6PQ0UCvaAecxMyBUSUJY")
                                                                .repository(Repositories.MAVEN_CENTRAL)
                                                                .build();
    private static final Library SLF4J_API = Library.builder()
                                                    .groupId("org.slf4j")
                                                    .artifactId("slf4j-api")
                                                    .version("1.7.36")
                                                    .checksum("0+9XXj5JeWeNwBvx3M5RAhSTtNEft/G+itmCh3wWocA")
                                                    .repository(Repositories.MAVEN_CENTRAL)
                                                    .build();
    private static final Library MAVEN_RESOLVER_PROVIDER = Library.builder()
                                                                  .groupId("org.apache.maven")
                                                                  .artifactId("maven-resolver-provider")
                                                                  .version("3.9.4")
                                                                  .checksum("upvxL9L6RXgCNbthu/fkWTxg10HwCOCJuZkp6Ma1iqk")
                                                                  .repository(Repositories.MAVEN_CENTRAL)
                                                                  .build();
    private static final Library MAVEN_MODEL = Library.builder()
                                                      .groupId("org.apache.maven")
                                                      .artifactId("maven-model")
                                                      .version("3.9.4")
                                                      .checksum("eTHcjdqHju9GmI2f0HxK3jyRiS1/syCMdCy5RXE1uxU")
                                                      .repository(Repositories.MAVEN_CENTRAL)
                                                      .build();
    private static final Library MAVEN_REPOSITORY_METADATA = Library.builder()
                                                                    .groupId("org.apache.maven")
                                                                    .artifactId("maven-repository-metadata")
                                                                    .version("3.9.4")
                                                                    .checksum("HVwzk6o5+AeDf7BY14rI0KKHrfGfS6atUHA1Os9hsIE")
                                                                    .repository(Repositories.MAVEN_CENTRAL)
                                                                    .build();
    private static final Library PLEXUS_UTILS = Library.builder()
                                                       .groupId("org.codehaus.plexus")
                                                       .artifactId("plexus-utils")
                                                       .version("3.5.1")
                                                       .checksum("huAlXUyHnGG0gz7X8TEk6LtnnfR967EnMm59t91JoHs")
                                                       .repository(Repositories.MAVEN_CENTRAL)
                                                       .build();
    private static final Library JAVAX_INJECT = Library.builder()
                                                       .groupId("javax.inject")
                                                       .artifactId("javax.inject")
                                                       .version("1")
                                                       .checksum("kcdwRKUMSBY2wy2Rb9ickRinIZU5BFLIEGUID5V95/8")
                                                       .repository(Repositories.MAVEN_CENTRAL)
                                                       .build();
    static final Collection<Library> DEPENDENCY_BUNDLE = new ArrayList<>(Arrays.asList(
        MAVEN_RESOLVER_SUPPLIER, MAVEN_RESOLVER_CONNECTOR_BASIC, MAVEN_RESOLVER_TRANSPORT_FILE,
        MAVEN_RESOLVER_TRANSPORT_HTTP, HTTPCLIENT, HTTPCORE, COMMONS_CODEC, JCL_OVER_SLF4J,
        MAVEN_RESOLVER_PROVIDER, MAVEN_MODEL, MAVEN_MODEL_BUILDER, PLEXUS_INTERPOLATION, MAVEN_ARTIFACT, COMMONS_LANG3,
        MAVEN_BUILDER_SUPPORT, MAVEN_REPOSITORY_METADATA, MAVEN_RESOLVER_API, MAVEN_RESOLVER_SPI, MAVEN_RESOLVER_UTIL,
        MAVEN_RESOLVER_IMPL, MAVEN_RESOLVER_NAMED_LOCKS, SLF4J_API, PLEXUS_UTILS, JAVAX_INJECT
    ));
}
