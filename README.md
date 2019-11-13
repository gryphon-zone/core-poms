# core-poms

[![License][license_badge]][license_url]
[![Maven Central][central_badge]][central_link]
[![Build Status][build_badge]][build_link]

[build_badge]: https://jenkins.gryphon.zone/buildStatus/icon?job=gryphon-zone%2Fcore-poms%2Fmaster
[build_link]: https://jenkins.gryphon.zone/job/gryphon-zone/job/core-poms/job/master/

[central_badge]: https://img.shields.io/maven-central/v/zone.gryphon/core-poms?color=blue
[central_link]: https://search.maven.org/search?q=g:%22zone.gryphon%22%20AND%20a:%22core-poms%22

[license_badge]: https://img.shields.io/github/license/gryphon-zone/core-poms
[license_url]: http://www.apache.org/licenses/LICENSE-2.0

The set of core Maven POMs for Gryphon Zone projects.

Currently includes two POMs meant to be consumed by other projects:
* [base-pom](https://search.maven.org/search?q=g:zone.gryphon%20AND%20a:base-pom&core=gav)
    * Parent POM for all Gryphon Zone projects
* [base-bom](https://search.maven.org/search?q=g:zone.gryphon%20AND%20a:base-bom&core=gav)
    * Dependency management for common project dependencies

# Usage

### base-pom

`base-pom` is meant to be a parent POM:
```xml
<parent>
    <groupId>zone.gryphon</groupId>
    <artifactId>base-pom</artifactId>
    <version>VERSION</version>
</parent>
```
See the link above for available versions.

### base-bom
`base-bom` is meant to be included as a `pom`, in the `import` scope:
```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>zone.gryphon</groupId>
            <artifactId>base-bom</artifactId>
            <version>${base-bom.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```
Note that `base-bom.version` is defined in `base-pom`, so as long as `base-pom` is an ancestor of your current POM,
you do **not** need to redefine `base-bom.version` in your POM
(and it will be automatically upgraded when you upgrade your `base-pom` version).

## Notes on behavior

### sortpom-maven-plugin

For POM cleanliness, the [sortpom-maven-plugin](https://github.com/Ekryd/sortpom) is enabled by default.

To exclude a section of the POM from being sorted, use the tags `<?SORTPOM IGNORE?>` and `<?SORTPOM RESUME?>`.

To disable POM sorting entirely, you can either set the `sortpom.skip` property to `true`,
or re-bind the `sortpom` execution `sort-pom` to the Maven execution phase `none`:
```xml
<build>
    <plugins>
        <plugin>
            <groupId>com.github.ekryd.sortpom</groupId>
            <artifactId>sortpom-maven-plugin</artifactId>
            <executions>
                <execution>
                    <id>sort-pom</id>
                    <phase>none</phase>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

### Github Pages
To enable automatic creation of a [github-pages](https://pages.github.com/) site,
create the file named `src/site/.gh-pages` in the root module (the file can be empty).

Note that the initial creation of the `gh-pages` git branch requires manual steps:
```shell script
git checkout --orphan gh-pages
rm .git/index
git clean -fdx
touch .gitkeep
git add .
git commit -m 'Create gh-pages branch'
git push --set-upstream origin gh-pages
```

See the tutorials on the 
[maven-scm-publish-plugin](https://maven.apache.org/plugins/maven-scm-publish-plugin/various-tips.html)
for details.