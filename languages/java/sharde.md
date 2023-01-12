适当的耍点流氓手段（Java版）
引言
恒无欲也，以观其眇；恒有欲也，以观其徼。两者同出，异名同谓。玄之又玄，众妙之门。
                                                    ---=老子 ：《道德经》马王堆帛书版
[图片]
结论
1. 必要的时候，耍点流氓手段，进行java类覆盖和重写，可以达到绝处逢生、柳暗花明的效果
2. 不比过于笃信别人框架、产品、技术高大上的宣传，他们也很low。
思考问题导引
1. 我们什么情况下会去修改一个jar包中的类？
2. 我们如何去修改一个jar包中的类
3. 修改后，我们会引入哪些问题？
4. 我们该如何去处理这些问题。
场景分析
核心问题汇总与解析
1. 自己的程序莫名其妙的启动不了，或者失败了。
自己写的一个lib库，测试什么的好好的，别人一集成就报错。 自己的项目没有修改过，之前部署的还好好的，现在一部署，要不启动不了，要不调用报错。
这个其实最核心的原因，就是你的程序的依赖（包括，环境（配置）, 第三方依赖jar包、第三方依赖服务）出现了问题。如下图所示的包依赖冲突。
[图片]
       包依赖冲突的本质，与java的类加载机制和包管理机制有关系。Java采用双亲加载的机制。在加载类的过程中，如果遇到两个唯一标识相同的类（ClassLoader+类的全路径），那么谁先加载谁生效，后遇到的类并不会被加载。通常情况下，我们自己的项目内部类并不会冲突。但是当我们通过maven依赖第三方jar包的时候，尤其是间接依赖的时候（参考图一和图二），不可避免的冲突依赖就发生了。
暂时无法在飞书文档外展示此内容
那么我们怎么解决呢？常见的冲突的解决策略有两种，
  1. 把所有依赖的库更新到同一版本，比如图三将Lib A依赖的版本也升级为Guava 24-Lib
    1. 能解决所有问题吗？不能，1. 升级到新版本，LibA 可能会报错，需要也同步更新。2. 比如碰到如下做隔离的，直接把依赖的包shade进自己的包里面，如果有两个人这么干，这个冲突没法简单的通过同步到新版本解决了。 那么怎么解决呢？1. 可以找jar包提供人修改，2. 我们就要流氓一些，想办法把依赖包里面的类排除掉（借助于maven-shade-plugin的filter可以轻松实现)
[图片]
    2. 注意事项：如果你是提供jar包给别人，你最好不要给别人一个暴露自己依赖的jar包。每个项目的使用者情况千差万别。所以最好通过减少对外依赖项，来控制对外暴露的jar包。
  2. 把依赖的库做隔离。比如图四，将Lib A 联通其依赖和Lib B连同其依赖做隔离。
    1. 怎么做隔离？我们知道ClassLoader+类的全路径可以唯一定位一个类，那么如果ClassLoader不一样，或者有办法把类路径修改了，那就可以做到隔离。（参考SOFAStack和maven-shade-plugin)都是相对成熟的方案。shade插件可参考下面
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-shade-plugin</artifactId>
  <version>3.1.1</version>
  <configuration>
    <artifactSet>
       <!--需要做shade包-->
      <includes>
        <include>cn.bjca.footstone.sword.json.jkson.core:bjca-json-databind</include>
        <include>cn.bjca.footstone.sword.json.jkson.core:bjca-json-annotations</include>
        <include>cn.bjca.footstone.sword.json.jkson.core:bjca-json-core</include>
        <include>cn.bjca.footstone.sword.json.jkson.datatype:bjca-json-datatype-jsr310</include>
        <include>cn.bjca.footstone.sword.json.jkson.datatype:bjca-json-datatype-jdk8</include>
        <include>cn.bjca.footstone.sword.json.jkson.module:bjca-json-module-parameter-names</include>
        <include>cn.bjca.footstone.sword:json-api</include>
        <include>cn.bjca.footstone.sword:json-api-impl-jkson</include>
        <include>cn.bjca.footstone.sword:json-api-impl-jkson-jdk8</include>
        <include>cn.bjca.daps:daps-processor-apiserver</include>
        <include>cn.bjca.daps:daps-processor-worker</include>
      </includes>
    </artifactSet>
    <filters>
      <filter>
        <artifact>*:*</artifact>
        <!--排除某些类-->
        <excludes>
          <exclude>META-INF/*.SF</exclude>
          <exclude>META-INF/*.DSA</exclude>
          <exclude>META-INF/*.RSA</exclude>
        </excludes>
      </filter>
    </filters>
    <relocations>
      <!-- 重写包路径-->
      <relocation>
        <pattern>com.fasterxml.jackson</pattern>
        <shadedPattern>cn.org.bjca.footstone.com.fasterxml.jackson</shadedPattern>
      </relocation>
      <relocation>
        <pattern>com.bjca.footstone.sword</pattern>
        <shadedPattern>com.org.bjca.footstone.sword</shadedPattern>
      </relocation>
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
    2. 隔离后会带来什么影响？类臃肿，依赖jar包庞大。参考下图：
[图片]
    3. 如何避免做包隔离后带来的类膨胀。
      1. 减少不必要的依赖。
      2. 借助工具，只依赖需要的类（这是一个相对复杂的东西，java是个动态语言，我们提取的依赖链可能不全），maven-sharde-plugin提供了相对应的技术 
<plugin>
    <artifactId>maven-shade-plugin</artifactId>
    <version>3.2.1</version>
    <configuration>
        <minimizeJar>true</minimizeJar>
     </configuration>
          <!--动态依赖的补充机制-->
          <filters>
              <filter>
                  <artifact>ch.qos.logback:logback-core</artifact>
                  <includes>
                      <include>**</include>
                  </includes>
              </filter>
          </filters>
     <executions>
         <execution>
             <phase>package</phase>
             <goals>
                 <goal>shade</goal>
             </goals>
             </execution>
           </executions>
    </plugin>
2. 我只使用了这个开源包中的极少类，一有漏洞，就要更新， 烦死了。
  两种方案：
  1. 将依赖的类提出来重写。
  2. 用shade的minimizeJar功能，但是要切记排除掉jar包中的pom文件，否则安全扫描软件还是会认为有漏洞。
3. 我要同时支持依赖jar包的不同版本，怎么办？
  1. 参考前面讲过的隔离机制，不同版本的jar包进一步封装到不同的classloader中或类路径下。
4. 要支持信创了，可是我用的框架没有计划支持，而更让人崩溃的是，他们的扩展的地方被代码写死了。
比如sharding-jdbc的框架， 官方已经明确不支持商业闭源数据库。
[图片]
更糟糕的是他的一个扩展类 SystemSchemaBuilderRule是个枚举，没法扩展。真让人直呼脏字。
[图片]
换句话说就是我们项目太依赖于第三方框架。而第三方框架扩展又做的太low了，这直接导致我们在没有他们支持的情况下进退两难：换框架吧，已经来不及了，项目陷入太深了。通过框架的扩展项支持吧，又是个死胡同，遇到这种扯淡的事情，怎么办？他不仁我不义，重写这个类。
  1. 有人要问了：为什么不直接把框架拿过来，自己维护？ 我们以后还是需要利用他们的技术来修复安全更新，增加新功能，毕竟人家是专业的。我们只重写部分类，一是方便复用，二是等他们以后支持了，我们可以不再这种骚操作.
  2. 那么重写类有什么问题？ 很明显：1. 你需要对所使用的框架足够理解。2. 以后框架升级，不可避免的会导致不兼容，而且维护起来比较困难，3. 如果你的改动是给别人使用的。项目中已有的依赖就极大可能和你重写的类冲突，实施和维护起来还比较麻烦。
  3. 怎么解决这些问题？ 问题1 是必须直面的。2. 升级框架导致不兼容的概率会激增，但是我们可以通过控制升级频率（除非严重安全漏洞）减少不必要的升级、够用就行。3. 项目中的所有依赖都通过我们重写的入口引入。（借助于springboot的starter机制）使用的时候，通过工具检查一些可能冲突的项。通过maven的jar包依赖管理做好屏蔽工作。
  4. ok，那么怎么重写类呢？有两种办法。
    1. 直接重写。借助于项目的类加载优先级比jar包的高，项目中的类优先加载。
    比如如果你是一个独立部署的项目，那么最简单的方法，就是在自己的工程启动模块里面，用相同路径的类覆盖。比如Elastic-Job要想支持Oracle类数据库。那么我们可以在启动的模块里，新建一个包名org.quartz.impl.jdbcjobstore。然后覆盖一下StdJDBCDelegate类的实现。修改里面的相关sql语句即可。局部如下：
 ## 将原来的PRIORITY 修改为\"PRIORITY\" 为了兼容多数据库，加了个全局开关
 String sql=  "SELECT TRIGGER_NAME FROM {0}TRIGGERS WHERE SCHED_NAME = {1} AND NOT (MISFIRE_INSTR = -1) AND NEXT_FIRE_TIME < ? AND TRIGGER_GROUP = ? AND TRIGGER_STATE = ? ORDER BY NEXT_FIRE_TIME ASC, PRIORITY DESC";
 if(Globals.isElasticJobSupportOracle()) {
    sql = "SELECT TRIGGER_NAME FROM {0}TRIGGERS WHERE SCHED_NAME = {1} AND NOT (MISFIRE_INSTR = -1) AND NEXT_FIRE_TIME < ? AND TRIGGER_GROUP = ? AND TRIGGER_STATE = ? ORDER BY NEXT_FIRE_TIME ASC, \"PRIORITY\" DESC";
}
    2. 如果我们是作为jar包提供给别人的。优先级我们无法控制。那么我们就借助于maven-shade-plugin，排除掉部分类，然后重写后形成新的jar包。然后在项目使用中，排除掉其他的包。只保留新生成的jar包。
[图片]
总结
ok，至此，我们在项目中遇到的导致项目不稳定，以及项目无法适应新场景的几个重要方法就介绍完毕了。适当的耍点非常规手段。可以使我们的好多问题迎刃而解。最后在总结一下关键的集中技术：
1. 解决类和包冲突
  1. 统一冲突jar包的版本（包括直接依赖的jar包，间接依赖的jar包），适合项目而非lib库。
         借助于Maven的<dependencyManagement> 模块去统一依赖的jar版本。
  2. 去除依赖、或者修正依赖（如果方法a，无法解决）
    1. 对于直接依赖的jar包冲突，可以统一为最新版本，然后使用新版本的功能重新实现业务
  3. 隔离依赖（适合依赖功能重写复杂，多个间接依赖冲突）
    1. 不同的依赖库使用不同的classloader。可以自己定义classloader， 可以参考阿里的开源SOFAStack
    2. 对依赖的jar包，把他的依赖的类路径，以及内部的依赖关系修改了就可以解决冲突。maven-shade-plugin插件就是这个神器。
2. 覆盖重写类
  1. 如果是一个项目，那么直接在启动的module里，使用相同的包路径和类路径覆盖原有类
  2. 如果是一个lib库，那么你需要借助于第三方工具或者手动maven-shade-plugin，然后打成新的jar包。别人使用的时候，直接将原来的包，替换成你们自己的包。
3. 如何管理好自己依赖的类库，降低对使用者的影响。
  1. 如果是独立项目。 
    1. 如果是直接依赖jar包，功能简单，可以直接替换掉
    2. 尝试使用Maven的<dependencyManagement> 去统一依赖的jar包的版本。适用于正规的jar包升级（向前兼容）
    3. 如果是所有版本都不能正常运行,  可以使用隔离方法去解决。
  2. 如果是提供给第三方的jar包
    目标客户使用的依赖包是不确定的，所以我们最好的办法将自己的jar包连同依赖一块隔离。
参考
1. 使用 classloader 解决jar包冲突
2. Apache Maven Shade Plugin – Project Information
