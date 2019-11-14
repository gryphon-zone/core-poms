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

The set of core Maven POMs for Gryphon Zone projects.

Currently includes two POMs meant to be consumed by other projects:
* [base-pom](https://search.maven.org/search?q=g:zone.gryphon%20AND%20a:base-pom&core=gav)
    * Parent POM for all Gryphon Zone projects
* [base-bom](https://search.maven.org/search?q=g:zone.gryphon%20AND%20a:base-bom&core=gav)
    * Managed versions of commonly used dependencies

# Usage

### base-pom

`base-pom` is intended to be used as a parent POM, like so:
```xml
<parent>
    <groupId>zone.gryphon</groupId>
    <artifactId>base-pom</artifactId>
    <version>VERSION</version>
</parent>
```
See the link above for available versions.

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
Note that the property `base-bom.version` is defined in `base-pom`,
so you do **not** need to define `base-bom.version` in your POM.

## Notes on behavior

### sortpom-maven-plugin

For POM cleanliness, the [sortpom-maven-plugin](https://github.com/Ekryd/sortpom) is enabled by default.

To exclude a section of the POM from being sorted, use the tags `<?SORTPOM IGNORE?>` and `<?SORTPOM RESUME?>`.

To disable POM sorting entirely, you can either set the property `sortpom.skip` to `true`,
or re-bind the `sortpom` plugin execution `sort-pom` to the execution phase `none`:
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