# Spring Bean名称重复ConflictingBeanDefinitionException解决
## 问题描述
如果我们希望将相同名称的类放入spring中时，如果未指定bean名称，则会抛出异常：
```java
Caused by: org.springframework.context.annotation.ConflictingBeanDefinitionException: Annotation-specified bean name 'xxxx' for bean class [xxx] conflicts with existing, non-compatible bean definition of same name and class [xxx]
```
翻看Spring源码得知，当我们使用注解创建bean时，spring使用了AnnotationBeanNameGenerator来创建bean的名称。
``` java
        if (definition instanceof AnnotatedBeanDefinition) {
            String beanName = determineBeanNameFromAnnotation((AnnotatedBeanDefinition) definition);
            if (StringUtils.hasText(beanName)) {
                // Explicit bean name found.
                return beanName;
            }
        }
        // Fallback: generate a unique default bean name.
        return buildDefaultBeanName(definition, registry);
```
如果我们项目中存在相同名称的类，而在使用注解时（@Service、@Component）指定了不同的name，则不会抛出异常，否则Spring使用类的名称作为bean的名称，则会抛出异常。

但有些特殊场景，如多版本模块，我们不希望版本号污染类名，而希望以包的形式控制。则此时我们需要替换spring的默认名称生成器。
``` bash
- com.v1
    - UserController
- com.v2
    - UserController
```
## 冲突解决
创建全类名的BeanNameGenerator
此处代码摘自ConfigurationClassPostProcessor.importBeanNameGenerator。
``` java
public class AnnotationBeanNameGenerator implements BeanNameGenerator {
    @Override
    public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        if (definition instanceof AnnotatedBeanDefinition) {
            String beanName = determineBeanNameFromAnnotation((AnnotatedBeanDefinition) definition);
            if (StringUtils.hasText(beanName)) {
                // Explicit bean name found.
                return beanName;
            }
        }
        String beanClassName = definition.getBeanClassName();
        Assert.state(beanClassName != null, "No bean class name set");
        return beanClassName;
    }
}
``` 
## Spring Boot
SpringBoot提供了在启动时传入BeanNameGenerator的方式，可以修改Spring扫描注解时使用的名称。
``` java
@SpringBootApplication
@ComponentScan(nameGenerator = AnnotationBeanNameGenerator.class)
```
## Mybatis
而Mybatis并不是由Spring直接扫描的，而是由其本身自主扫描到Mapper而注入到Spring中。关键代码如下:
``` java
  @Override
  public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

    AnnotationAttributes annoAttrs = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(MapperScan.class.getName()));
    ClassPathMapperScanner scanner = new ClassPathMapperScanner(registry);
    .....
    .....
}
```
mybatis创建了spring提供的ClassPathMapperScanner，其默认使用的扔是AnnotationBeanNameGenerator。而mybatis也提供了修改方式。
``` java
@MapperScan(nameGenerator = AnnotationBeanNameGenerator.class)
``` 
## SpringBoot && Mybatis
完整代码如下:
``` java
@SpringBootApplication
@ComponentScan(nameGenerator = Application.SpringBeanNameGenerator.class)
@MapperScan(value = "**.mapper", markerInterface = BaseMapper.class, nameGenerator = Application.SpringBeanNameGenerator.class)
public class Application {
    public static class SpringBeanNameGenerator extends AnnotationBeanNameGenerator {
        @Override
        protected String buildDefaultBeanName(BeanDefinition definition) {
        if (definition instanceof AnnotatedBeanDefinition) {
            String beanName = determineBeanNameFromAnnotation((AnnotatedBeanDefinition) definition);
            if (StringUtils.hasText(beanName)) {
                // Explicit bean name found.
                return beanName;
            }
        }
            return definition.getBeanClassName();
        }
    }
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```
## 暴力覆盖方案
上述方式到是一般情况下足够了，但如果此时项目中又存在一个类似mybatis的组件，那么你仍需要配置BeanNameGenerator。我们可以暴力覆盖原本的AnnotationBeanNameGenerator，
建立与其包名相同，类名相同的文件在项目中：
``` java
public class AnnotationBeanNameGenerator implements BeanNameGenerator {
    @Override
    public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        if (definition instanceof AnnotatedBeanDefinition) {
            String beanName = determineBeanNameFromAnnotation((AnnotatedBeanDefinition) definition);
            if (StringUtils.hasText(beanName)) {
                // Explicit bean name found.
                return beanName;
            }
        }
        String beanClassName = definition.getBeanClassName();
        Assert.state(beanClassName != null, "No bean class name set");
        return beanClassName;
    }
}
```
此时无须配置任何配置，默认会走此BeanNameGenerator。

# 参考
[1、Bean Name Id 覆盖的探讨](https://cloud.tencent.com/developer/article/1497702)


