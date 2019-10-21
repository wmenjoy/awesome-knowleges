
# 一、Maven 思想
1、有效的管理软件项目的生命周期，（Snapshot，非Snapshot)，管理Java的package依赖

2、依赖传递思想

待补充
# 二、如何开发Maven 插件
# 三、常用技巧
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
## 2.6 Maven 包冲突检测
``` bash
mvn -U dependency:tree -Dverbose
```
## 2.7 Maven 如何和Sonar 集成
在settings.xml 文件profile下增加
``` xml
        <properties>
                <!-- Optional URL to server. Default value is http://localhost:9000 -->
                <sonar.host.url>
                  https://${sonar.uri}
                </sonar.host.url>
                <sonar.login>${loging_acess}</sonar.login>
                <sonar.java.source>1.8</sonar.java.source>
                <sonar.scm.provider>git</sonar.scm.provider>
                <sonar.sourceEncoding>UTF-8</sonar.sourceEncoding>
            </properties>
```
然后，使用
```bash
mvn sonar:sonar
```
参考：[老黄的sonarqube分享](https://github.com/bingoohuang/blog/issues/67)
## 2.8 Maven 上传本地文件到Maven 仓库
``` bash
mvn deploy:deploy-file -Dfile=./${jarFile} -DgroupId=${groupId} -DartifactId=${artifactId} -Dversion=${version} -Dpackaging=jar -Durl=${nexus.url} -DrepositoryId=${repositoryId}'
```
## 2.9 如何检测类冲突
### 2.9.1 enforcer 插件配置
``` xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-enforcer-plugin</artifactId>
    <version>1.4.1-dp-SNAPSHOT</version>
    <executions>
        <execution>
            <id>default-cli</id>
            <phase>validate</phase>
            <goals>
                <goal>enforce</goal>
            </goals>
        </execution>
    </executions>
    <dependencies>
        <dependency>
            <groupId>org.codehaus.mojo</groupId>
            <artifactId>extra-enforcer-rules</artifactId>
            <version>1.0-beta-4</version>
        </dependency>
    </dependencies>
    <configuration>
        <!--规则检查不通过就构建失败;Default:false. -->
        <!--<failFast>true</failFast>-->
        <rules>
            <!--<requireMavenVersion>
                <version>3.0.4</version>
            </requireMavenVersion>-->
           <dependencyConvergence/>
            <requireJavaVersion>
                <version>1.6.0</version>
            </requireJavaVersion>
            <banDuplicateClasses>
                <ignoreClasses>
                    <ignoreClass>javax.*</ignoreClass>
                    <ignoreClass>org.junit.*</ignoreClass>
                    <ignoreClass>junit.*</ignoreClass>
                    <ignoreClass>org.apache.xbean.recipe.*</ignoreClass>
                    <ignoreClass>org.hamcrest.*</ignoreClass>
                    <ignoreClass>org.slf4j.*</ignoreClass>
                    <ignoreClass>org.antlr.runtime.*</ignoreClass>
                    <ignoreClass>org.apache.commons.lang.*</ignoreClass>
                    <ignoreClass>org.apache.commons.codec.*</ignoreClass>
                    <ignoreClass>org.apache.commons.logging.*</ignoreClass>
                    <ignoreClass>org.codehaus.plexus.component.builder.*</ignoreClass>
                </ignoreClasses>
                <findAllDuplicates>true</findAllDuplicates>
            </banDuplicateClasses>
            <bannedDependencies>
                <!--是否检查传递性依赖(间接依赖)-->
                <searchTransitive>true</searchTransitive>
                <excludes>
                    <exclude>org.apache:libthrift</exclude>
                    <exclude>org.jboss.netty:netty</exclude>
                    <exclude>org.apache.thrift:libthrift:(,0.8.0),(0.8.0,)</exclude>
                    <exclude>io.netty:netty:(,3.9.2.Final)</exclude>
                    <exclude>com.sankuai.octo:mns-invoker:(,1.7.5)</exclude>
                    <exclude>com.fasterxml.jackson.core:*:(,2.6.0)</exclude>
                    <exclude>com.google.guava:guava:(,15.0)</exclude>
                </excludes>
                <message>some dependency must exclude</message>
            </bannedDependencies>
        </rules>
    </configuration>
</plugin>
```
### 2.9.2 检测冲突命令
 ``` bas
 mvn -U clean -Dmaven.test.skip=true enforcer:enforce -DcheckDeployRelease_skip=true
 ```
### 2.9.3 结果例子

* 1、存在重复的类的情况
``` bash
Found in:
    org.jboss.netty:netty:jar:3.2.19:compile
    io.netty:netty:jar:3.9.2.Final:compile
  Duplicate classes:
    org/jboss/netty/handler/codec/base64/Base64Decoder.class
    org/jboss/netty/util/VirtualExecutorService.class
以下省略
```
* 2、不允许的依赖jar包或不允许的依赖版本号：
``` bash
[WARNING] Rule 2: org.apache.maven.plugins.enforcer.BannedDependencies failed with message:
some dependency must exclude
Found Banned Dependency: org.apache:libthrift:jar:0.6.0
Found Banned Dependency: org.jboss.netty:netty:jar:3.2.19
Found Banned Dependency: org.apache.thrift:libthrift:jar:0.9.3
Use 'mvn dependency:tree' to locate the source of the banned dependencies.
```
### 2.9.3 解决办法
最后根据插件排查下来的信息，一个个排除掉存在冲突的jar包即可。注意如果是parent pom当中的依赖冲突，要在parent pom中修改，否则无法排除。

福利：使用idea的同学，推荐使用maven helper插件排除冲突，一目了然，很好用。
### 2.9.4 参考
https://www.cnblogs.com/f-zhao/p/6961058.html

http://www.tuicool.com/articles/RfIBfa

https://github.com/dimitri-koussa/maven-enforcer-duplicate-class-check

http://www.mojohaus.org/extra-enforcer-rules/banDuplicateClasses.html

## 2.10 快速修改maven 各个模块的版本号
maven 有多个子项目的时候， 修改每一个子项目的版本号会个别麻烦

为了简化这个过程，可以使用以下约束
* 1、各个子模块尽量使用公用的父类
* 2、项目中各个子模块之间的依赖使用${project.version}
* 3、子模块除了parent指定父的依赖pom版本，本身不要使用自定义版本

原因：这样只需要修改父目录的版本号，以及各个子目录的依赖的parent的版本，就不需要再做其他的修改，

同时为了快速修改版本，可以使用maven的 Versions maven plugin 插件
## 2.10.1 修改版本
```bash
mvn versions:set -DoldVersion=* -DnewVersion=1.0.1-SNAPSHOT -DprocessAllModules=true -DallowSnapshots=true
```
可以直接简化为
```bash
mvn versions:set -DnewVersion=1.0.1-SNAPSHOT
```

## 2.10.2 提交修改
```bash
mvn versions:commit
```
## 2.10.3 回退修改
```bash
mvn versions:revert
```








