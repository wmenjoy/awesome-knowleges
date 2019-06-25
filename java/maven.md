
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

## 2.2 Maven 如何做覆盖率测试(使用jacoco 插件）
``` bash
mvn org.jacoco:jacoco-maven-plugin:prepare-agent clean package sonar:sonar -Dspring.profiles.active=test -Dmaven.test.failure.ignore=true
```
## 2.3 Maven 如何做强制规则校验
使用 maven-enforcer-plugin  插件
在代码pom文件，或者父pom文件中加入
``` xml
<plugin>
                    <artifactId>maven-enforcer-plugin</artifactId>
                    <version>1.4.1</version>
                    <executions>
                        <execution>
                            <id>default-cli</id>
                            <goals>
                                <goal>display-info</goal>
                                <goal>enforce</goal>
                            </goals>
                            <phase>validate</phase>

                            <configuration>
                                <rules>
                                    <requireJavaVersion>
                                        <message>
                                            <![CDATA[You are running an older version of Java. This application requires at least JDK ${java.version}.]]>
                                        </message>
                                        <version>[${java.version}.0,)</version>
                                    </requireJavaVersion>
                                    <dependencyConvergence/>
                                    <requireReleaseDeps>
                                        <onlyWhenRelease>true</onlyWhenRelease>
                                    </requireReleaseDeps>
                                </rules>

                            </configuration>
                        </execution>
                    </executions>
                </plugin>
```
然后执行
``` bash
maven enforcer:enforce
```
## 2.4 如何自动去除snapshot
使用versions-maven-plugins
``` bash
   mvn -U org.codehaus.mojo:versions-maven-plugin:2.1:set -DremoveSnapshot=true -DprocessAllModules=true -DnewVersion=$noSnapshotVersion versions:use-releases

```
其中 noSnapshotVersion 为自定义的version字段, 可参考[如何获取mavne版本号]()，自动获取当前的版本号
## 2.5 如何获取maven的版本号
``` bash
#!/bin/sh
dir=$1

function getPomVersion(){
 pomfile=$1;
 
 [ -f  $pomfile ] && echo `cat $pomfile |tr '\r\n' ' '|sed 's/<modules>.*<\/modules>//;s/<exclusions>.*<\/exclusions>//;s/<dependencies>.*<\/dependencies>//;s/<parent>.*<\/parent>//;s/<build>.*<\/build>//; s/<properties>.*<\/properties>//'|grep -o '<version>.*<\/version>'|sed 's/<version>//;s/<\/version>//;'`;
}


function getSubModuleVersion(){
  pomfile=$1
  currentVersion=`getPomVersion $pomfile`
  if [ -z "$currentVersion" ]; then 
       
       parentPomfile=`dirname $pomfile`"/../pom.xml"
       [ -f $parentPomfile ] && echo `getPomVersion $parentPomfile`
  else 
     echo $currentVersion;
  fi

   echo "";
}

if [ -f "$dir" ]; then
  echo `getSubModuleVersion $dir`
  exit 0
fi

echo `getSubModuleVersion $dir/pom.xml`
```



