# core-poms [![Build status][build-icon]][build-link] [![Maven Central][mvn-central-icon]][mvn-central-link]

[build-link]: https://jenkins.gryphon.zone/job/gryphon-zone/job/core-poms/job/master/
[build-icon]: https://jenkins.gryphon.zone/buildStatus/icon?job=gryphon-zone%2Fcore-poms%2Fmaster

[mvn-central-icon]: https://maven-badges.herokuapp.com/maven-central/zone.gryphon/base-pom/badge.png
[mvn-central-link]: https://search.maven.org/artifact/zone.gryphon/base-pom/

[github-releases]: https://github.com/gryphon-zone/core-poms/releases/latest

[sortpom-maven-plugin]: https://github.com/Ekryd/sortpom

# Releases
The latest release can be found on [Maven Central][mvn-central-link], or on the [Github release page][github-releases].

# Parent POM

Coordinates for inclusion in a maven project:

```xml
<parent>
    <groupId>zone.gryphon</groupId>
    <artifactId>base-pom</artifactId>
    <version>VERSION</version>
</parent>
```

# Base BOM
The `base-bom` artifact manages the versions of several core Maven dependencies which are frequently used in projects.

To include it in your project, add the following deceleration to your POM:
```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>zone.gryphon</groupId>
            <artifactId>base-bom</artifactId>
            <version>VERSION</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

If using the [sortpom-maven-plugin][sortpom-maven-plugin] (which is enabled by default by `base-pom`),
you may wish to disable sorting of the `base-bom` entry to ensure Maven gives it the proper priority.
  
This can be done with the following:
```xml
<dependencyManagement>
    <dependencies>
        <?SORTPOM IGNORE?>
        <dependency>
            <groupId>zone.gryphon</groupId>
            <artifactId>base-bom</artifactId>
            <version>VERSION</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
        <?SORTPOM RESUME?>
    </dependencies>
</dependencyManagement>
```

If `base-bom` is included in the same POM which declares `base-pom` as its parent, you can use the expression
`project.parent.version` to lock its version to the parent POM version, like so:
```xml
<project>
    <!-- ...snip... -->
    
    <parent>
        <groupId>zone.gryphon</groupId>
        <artifactId>base-pom</artifactId>
        <version>VERSION</version>
    </parent>
    
    <!-- ...snip... -->
    
    <dependencyManagement>
        <dependencies>
            <?SORTPOM IGNORE?>
            <dependency>
                <groupId>zone.gryphon</groupId>
                <artifactId>base-bom</artifactId>
                <version>${project.parent.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <?SORTPOM RESUME?>
        </dependencies>
    </dependencyManagement>
    
    <!-- ...snip... -->
    
</project>
```

Otherwise, Maven sadly does not provide an easy way to version-lock the two dependencies.
 