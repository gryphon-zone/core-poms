# core-poms

[![License][license_badge]][license_url]
[![Maven Central][central_badge]][central_link]
[![Build Status][build_badge]][build_link]

[build_badge]: https://jenkins.gryphon.zone/buildStatus/icon?job=gryphon-zone%2Fcore-poms%2Fmaster
[build_link]: https://jenkins.gryphon.zone/job/gryphon-zone/job/core-poms/job/master/

[central_badge]: https://img.shields.io/maven-central/v/zone.gryphon/base-pom?color=blue
[central_link]: https://search.maven.org/search?q=g:%22zone.gryphon%22%20AND%20a:%22base-pom%22

[license_badge]: https://img.shields.io/github/license/gryphon-zone/core-poms
[license_url]: http://www.apache.org/licenses/LICENSE-2.0

Fundamental [POMs](https://maven.apache.org/guides/introduction/introduction-to-the-pom.html) for Gryphon Zone projects:
* [base-pom](https://search.maven.org/artifact/zone.gryphon/base-pom) - Parent POM for all Gryphon Zone projects
* [base-bom](https://search.maven.org/artifact/zone.gryphon/base-bom) - Managed versions of commonly used dependencies

# Usage

### base-pom

`base-pom` is meant to be used as a parent POM:
```xml
<parent>
    <groupId>zone.gryphon</groupId>
    <artifactId>base-pom</artifactId>
    <version>VERSION</version>
</parent>
```

### base-bom
`base-bom` should be included in the `dependencyManagement` section of the POM with the `import` scope,
so that it will manage versions of project dependencies:
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
Note that the property `base-bom.version` is defined in `base-pom`, so it does **not** need to be redefined.

# Behavior

`base-pom` enables a number of default configurations based on Gryphon Zone best-practices.
In the event the defaults are not acceptable for a child project, most of the executions can be configured via properties in the POM.

## Bytecode Version

By default, Java 8 bytecode will be generated.

This can be changed using the `java.version.minor` property, e.g. to generate Java 7 bytecode
set it to `7`, for Java 8 set it to `8`, etc...

## POM Hygiene

### POM Hygiene - Style

For POM hygiene, the [sortpom-maven-plugin](https://github.com/Ekryd/sortpom) is enabled by default.

To exclude a section of the POM from being sorted, use the tags `<?SORTPOM IGNORE?>` and `<?SORTPOM RESUME?>`.

To disable POM sorting entirely, set the property `sortpom.skip` to `true`.

### POM Hygiene - Dependencies

The `analyze-only` goal of the
[maven-dependency-plugin](https://maven.apache.org/plugins/maven-dependency-plugin/analyze-only-mojo.html)
is used to detect dependency issues in the POM.

In the case of false positives, use the
[ignoredUnusedDeclaredDependencies](https://maven.apache.org/plugins/maven-dependency-plugin/analyze-only-mojo.html#ignoredUnusedDeclaredDependencies)
configuration option to mark dependencies as used.

To prevent detected issues from failing the build, set `dependency.fail.on.warnings` to `false`.

## Code Hygiene

### Code Hygiene - Style

Gryphon Zone projects follow the [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html),
with the following changes:
* Indentation is done with `4` spaces, not `2`
* Line length/column limit is not limited to 100 characters
  * Although not restricted, use best judgement as to when lines should wrap

This standard is enforced with the [maven-checkstyle-plugin](https://maven.apache.org/plugins/maven-checkstyle-plugin/).

To temporarily prevent checkstyle failures from failing the build, set the property `checkstyle.failOnViolation` to `false`.

### Code Hygiene - Bugs

[Error Prone](https://errorprone.info/) is enabled by default during compilation.

### Code Hygiene - Test Coverage

[JaCoCo](https://www.eclemma.org/jacoco/) is enabled by default for code coverage.






### Github Pages
To enable automatic publishing of a [github-pages](https://pages.github.com/) site during the execution phase `site-deploy`,
you can add one of two files depending on whether the module is the root module of the project, of a child module:
* **root module** - `src/site/.gh-pages`
* **child module** - `smoketest/src/site/.gh-pages-child-module`

Note that technically this only needs to be done for the last module to build in a project;
the downside to doing it for all modules is unnecessary commits to the `gh-pages` branch.

_note: this is necessary because the `maven-scm-publish-plugin` is intended to be run as a aggregator plugin,_
_and Maven lacks support for properly running Aggregator plugins which are defined in the POM,_
_a problem which [has been present for over ten years without a solution.](https://cwiki.apache.org/confluence/display/MAVENOLD/Aggregator+Plugins)_

The initial creation of the `gh-pages` git branch requires manual setup:
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
