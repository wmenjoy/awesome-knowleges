# 一、Maven 思想
# 二、常用技巧
## 2.1 maven 如何把所有的依赖打成一个jar包
With Maven 2, the right way to do this is to use the Maven2 Assembly Plugin which has a pre-defined descriptor file for this purpose and that you could just use on the command line:
``` bash
mvn assembly:assembly -DdescriptorId=jar-with-dependencies
```
If you want to make this jar executable, just add the main class to be run to the plugin configuration:

``` xml
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-assembly-plugin</artifactId>
  <configuration>
    <archive>
      <manifest>
        <mainClass>my.package.to.my.MainClass</mainClass>
      </manifest>
    </archive>
  </configuration>
</plugin>
```
f you want to create that assembly as part of the normal build process, you should bind the single or directory-single goal (the assembly goal should ONLY be run from the command line) to a lifecycle phase (package makes sense), something like this:
``` xml
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-assembly-plugin</artifactId>
  <executions>
    <execution>
      <id>create-my-bundle</id>
      <phase>package</phase>
      <goals>
        <goal>single</goal>
      </goals>
      <configuration>
        <descriptorRefs>
          <descriptorRef>jar-with-dependencies</descriptorRef>
        </descriptorRefs>
        ...
      </configuration>
    </execution>
  </executions>
</plugin>
```
Adapt the configuration element to suit your needs (for example with the manifest stuff as spoken).
