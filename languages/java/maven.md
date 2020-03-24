
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

### 2.1.1 如何将依赖包的路径修改后，打成一个包
为了避免包冲突，有时候，我们需要对包进行改路径，重新编译，这时候maven-shade-plugin就可以帮你省好多事情,官方说明如下
```
package the artifact in an uber-jar, including its dependencies and to shade - i.e. rename - the packages of some of the dependencies.
```
使用例子, 比如代码中需要将grizzly的包修改，我在maven中添加如下代码， 其中includes节点，告诉代码需要调整哪些jar包，filter过滤掉哪些不处理的文件， relocation 进行替换操作， 完成以后，grizzly中的org.glassfish.grizzly 被添加了cn.my.前缀
``` xml
 <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-shade-plugin</artifactId>
                <version>3.1.1</version>
                <configuration>
                    <artifactSet>
                        <includes>
                            <include>org.glassfish.grizzly:grizzly-framework</include> <!-- 需要修改的jar包-->
                            <include>org.glassfish.grizzly:grizzly-portunif</include>
                            <include>org.glassfish.grizzly:grizzly-http-server-jaxws</include>
                            <include>org.glassfish.grizzly:grizzly-http-server</include>
                          <!--  <include>ch.qos.logback:logback-classic</include>
                            <include>ch.qos.logback:logback-core</include> -->
                        </includes>
                    </artifactSet>
                    <filters>
                        <filter>
                            <artifact>*:*</artifact>
                            <excludes>
                                <exclude>META-INF/*.SF</exclude>
                                <exclude>META-INF/*.DSA</exclude>
                                <exclude>META-INF/*.RSA</exclude>
                            </excludes>
                        </filter>
                    </filters>
			 <!-- 需要修改的jar包-->
                    <relocations>
                        <relocation>
                            <pattern>org.glassfish.grizzly</pattern>
                            <shadedPattern>cn.my.org.glassfish.grizzly</shadedPattern>
                        </relocation>
                    <!--    <relocation>
                            <pattern>ch.qos.logback</pattern>
                            <shadedPattern>cn.my.ch.qos.logback.</shadedPattern>
                        </relocation> -->
                    </relocations>
                </configuration>
                <executions>
                    <execution>
                        <phase>package</phase>
                        <goals>
                            <goal>shade</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
```

### 2.1.2 参考
[Apache Maven Shade Plugin](https://maven.apache.org/plugins/maven-shade-plugin/index.html)
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
### 2.10.1 修改版本
```bash
mvn versions:set -DoldVersion=* -DnewVersion=1.0.1-SNAPSHOT -DprocessAllModules=true -DallowSnapshots=true
```
可以直接简化为
```bash
mvn versions:set -DnewVersion=1.0.1-SNAPSHOT
```

### 2.10.2 提交修改
```bash
mvn versions:commit
```
### 2.10.3 回退修改
```bash
mvn versions:revert
```

项目最重要的三件事，1.如何快速生成一个代码，2、如何根据生成的代码快速开发，3、如何根据编写后的代码，生成符合公司规范的jar包
## 2.11 如何生成项目脚手架？
Maven的插件 archetype 系列，可以帮助我们从一个已有的项目生成模板工程。从模板工程生成我们自己的项目
### 2.11.1 使用archetype:create-from-project 生成项目
1.通过cmd到项目的所在路径，执行 mvn archetype:create-from-project

2.执行第一步后，项目中会产生target目录，cd到target\generated-sources\archetype下，执行mvn install

执行这个后，这个项目的archetype就会被保存到你maven的本地仓库；

问题：
* 1、项目的子项目包需要修改 适应 __rootArtifactId__代替

* 2、内部子项目之间的依赖需要调整为， ${groupId} ${rootArtifactId}+对应报名


### 2.11.2 使用archetype:generator
```shell
mvn archetype:generate -Dpackage=cn.org.my.package -DgroupId=cn.org.my.group -DartifactId=bjca-demo-jar -Dversion=1.0.0-SNAPSHOT -DarchetypeGroupId=cn.org.my.groupId -DarchetypeArtifactId=cn-my-archetype -DarchetypeVersion=my-version-jar.RELEASE -X -DarchetypeCatalog=local
```

### 2.11.3 参考
[archetype:create-from-project manual](http://maven.apache.org/archetype/maven-archetype-plugin/create-from-project-mojo.html)


## 2.22 如何自定义打包软件


# 四、Maven 实际案例解析
## 4.1、包冲突导致的循环依赖
### 4.1.1 现场
在一次服务升级部署到tomcat 8的时候，tomcat 报以下错误：
```
Caused by: java.lang.IllegalStateException: Unable to complete the scan for annotations for web application [/ra] due to a 
StackOverflowError. Possible root causes include a too low setting for -Xss and illegal cyclic inheritance dependencies. The
class hierarchy being processed was [org.bouncycastle.asn1.ASN1EncodableVector->org.bouncycastle.asn1.DEREncodableVector-
>org.bouncycastle.asn1.ASN1EncodableVector]
		at org.apache.catalina.startup.ContextConfig.checkHandlesTypes(ContextConfig.java:2116)
		at org.apache.catalina.startup.ContextConfig.processAnnotationsStream(ContextConfig.java:2054)
		at org.apache.catalina.startup.ContextConfig.processAnnotationsJar(ContextConfig.java:2000)
		at org.apache.catalina.startup.ContextConfig.processAnnotationsUrl(ContextConfig.java:1970)
		at org.apache.catalina.startup.ContextConfig.processAnnotations(ContextConfig.java:1923)
		at org.apache.catalina.startup.ContextConfig.processClasses(ContextConfig.java:1230)
		at org.apache.catalina.startup.ContextConfig.webConfig(ContextConfig.java:1134)
		at org.apache.catalina.startup.ContextConfig.configureStart(ContextConfig.java:769)
		at org.apache.catalina.startup.ContextConfig.lifecycleEvent(ContextConfig.java:299)
		at org.apache.catalina.util.LifecycleBase.fireLifecycleEvent(LifecycleBase.java:94)
		at org.apache.catalina.core.StandardContext.startInternal(StandardContext.java:5134)
		at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:150)
		... 10 more
```

### 4.1.2 分析
根据以上初步分析，是因为循环依赖，导致的栈溢出
```
[org.bouncycastle.asn1.ASN1EncodableVector->org.bouncycastle.asn1.DEREncodableVector-
>org.bouncycastle.asn1.ASN1EncodableVector]
```
但正常情况同一个项目下，如果包出现包依赖，编译器都会直接报错，根本打包不了。怀疑有可能是包冲突，
通过对类org.bouncycastle.asn1.ASN1EncodableVector，发现，这个类包含在两个包中bcprov-ext-jdk15on:1.48和bcprov-jdk15on:1.46， 分析两个jar包的依赖结构发现两个包的java package基本相同，单两个包的版本不一致，应该是bouncycastle在1.48或者1.47进行了拆包，然后依赖的时候，有部分版本没有升级，导致的这一高，一低两个版本的出现。两个类冲突冲突。

### 4.1.3 解决
跟业务同学沟通，删除掉bcprov-jdk15on:1.46，测试，tomcat 正常启动，需要进行功能的全面回归测试
### 4.1.4 后续改进
* 1、 使用Maven插件，加入类冲突，包冲突的强制校验，辅助业务开发同学及时发现问题
* 2、对于类冲突，包冲突的解决方案，参考maven常用技巧



# 参考
1. [Versions Maven Plugin](http://www.mojohaus.org/versions-maven-plugin/)
2. [How do I tell Maven to use the latest version of a dependency?](https://stackoverflow.com/questions/30571/how-do-i-tell-maven-to-use-the-latest-version-of-a-dependency)
3. [maven升级、切换](https://blog.csdn.net/weixin_33676492/article/details/91589647)
4. [Run Maven Enforcer Plugin rule on command line](https://stackoverflow.com/questions/22157668/run-maven-enforcer-plugin-rule-on-command-line)
5. [Generic Plugin configuration information](https://maven.apache.org/enforcer/maven-enforcer-plugin/usage.html)
