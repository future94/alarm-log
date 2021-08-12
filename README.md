## 1. 日志监控报警
**核心功能**：
- 监控日志中抛出的指定异常或者方法与类中抛出的指定异常，发送到钉钉、企业微信、邮箱等。
- 支持log4j、log4j2、logback等主流框架。
- 支持SpringBoot、SpringMVC等框架。

## 2. SpringBoot

详情查看`alarm-log-examples-spring-boot`包下代码

### 2.1 集成
#### 引入如下依赖
```xml
<dependency>
    <groupId>com.future94</groupId>
    <artifactId>alarm-log-spring-boot-starter</artifactId>
    <version>${latest.version}</version>
</dependency>
```

### 2.2 引入日志监控

#### 2.2.1 同步方式
- **log4j** : 配置项说明见[2.3.3 日志配置中设置](#233-日志配置中设置)
```xml
<!DOCTYPE log4j:configuration SYSTEM "log4j.dtd">
<log4j:configuration>
    <appender name="Console" class="org.apache.log4j.ConsoleAppender">
        <layout class="org.apache.log4j.PatternLayout">
            <param name="ConversionPattern" value="%d{yyyy-MM-dd HH:mm:ss,SSS} [%p] %m  >> %c:%L%n"/>
        </layout>
    </appender>

    <!--这里替换成AspectLog4jAsyncAppender-->
    <appender name="AlarmLog" class="com.future94.alarm.log.core.enhance.log4j.AlarmLogLog4jAsyncAppender">
        <param name="doWarnException" value="false"/>
        <param name="warnExceptionExtend" value="java.lang.Exception,java.lang.RuntimeException"/>
        <appender-ref ref="Console"/>
    </appender>

    <root>
        <priority value="info" />
        <appender-ref ref="Console"/>
        <appender-ref ref="AlarmLog"/>
    </root>
</log4j:configuration>
```

- **log4j2** : 配置项说明见[2.3.3 日志配置中设置](#233-日志配置中设置)
```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration debug="true">
    <!--先定义所有的appender-->
    <appenders>
        <!--控制台日志-->
        <Console name="Console" target="SYSTEM_OUT">
            <PatternLayout pattern="%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"/>
        </Console>

        <AlarmLog name="AlarmLog">
            <warnExceptionExtend>false</warnExceptionExtend>
            <doWarnException>java.lang.Exception,java.lang.RuntimeException</doWarnException>
        </AlarmLog>

    </appenders>
    <!--然后定义logger，只有定义了logger并引入的appender，appender才会生效-->
    <loggers>
        <root level="INFO">
            <appender-ref ref="Console"/>
            <!-- AlarmLog 处理有打印INFO级别的日志，如果level是INFO级别会循环调用，抛出 ERROR Recursive call to appender ALARM_LOG，所以最好将ALARM_LOG 的级别设置为ERROR -->
            <appender-ref ref="AlarmLog" level="ERROR" />
        </root>
    </loggers>
</configuration>
```

- **logback** : 配置项说明见[2.3.3 日志配置中设置](#233-日志配置中设置)
```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration debug="true">

    <appender name="Console" class="ch.qos.logback.core.ConsoleAppender">
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
        </encoder>
    </appender>

    <appender name="AlarmLog" class="com.future94.alarm.log.core.enhance.logback.AlarmLogLogbackAsyncAppender">
        <warnExceptionExtend>false</warnExceptionExtend>
        <doWarnException>java.lang.Exception,java.lang.RuntimeException</doWarnException>
        <includeCallerData>true</includeCallerData>
        <appender-ref ref="Console"/>
    </appender>

    <root level="INFO">
        <appender-ref ref="Console" />
        <appender-ref ref="AlarmLog" level="ERROR" />
    </root>
</configuration>
```

#### 2.2.2 异步方式

- **log4j** : 配置项说明见[2.3.3 日志配置中设置](#233-日志配置中设置)
```xml
<!DOCTYPE log4j:configuration SYSTEM "log4j.dtd">
<log4j:configuration>
    <appender name="Console" class="org.apache.log4j.ConsoleAppender">
        <layout class="org.apache.log4j.PatternLayout">
            <param name="ConversionPattern" value="%d{yyyy-MM-dd HH:mm:ss,SSS} [%p] %m  >> %c:%L%n"/>
        </layout>
    </appender>

    <!--这里替换成AspectLog4jAsyncAppender-->
    <appender name="AlarmLog" class="com.future94.alarm.log.core.enhance.log4j.AlarmLogLog4jAsyncAppender">
        <param name="doWarnException" value="false"/>
        <param name="warnExceptionExtend" value="java.lang.Exception,java.lang.RuntimeException"/>
        <appender-ref ref="Console"/>
    </appender>

    <appender name="AsyncConsole" class="org.apache.log4j.AsyncAppender">
        <appender-ref ref="Console"/>
    </appender>

    <appender name="AsyncAlarmLog" class="org.apache.log4j.AsyncAppender">
        <appender-ref ref="AlarmLog"/>
    </appender>

    <root>
        <priority value="info" />
        <appender-ref ref="AsyncConsole"/>
        <appender-ref ref="AsyncAlarmLog"/>
    </root>
</log4j:configuration>
```

- **log4j2** : 配置项说明见[2.3.3 日志配置中设置](#233-日志配置中设置)
```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration debug="true">
    <!--先定义所有的appender-->
    <appenders>
        <!--控制台日志-->
        <Console name="Console" target="SYSTEM_OUT">
            <PatternLayout pattern="%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"/>
        </Console>

        <AlarmLog name="AlarmLog">
            <warnExceptionExtend>false</warnExceptionExtend>
            <doWarnException>java.lang.Exception,java.lang.RuntimeException</doWarnException>
        </AlarmLog>

        <!--异步输出到控制台-->
        <Async name="AsyncConsole">
            <AppenderRef ref="Console"/>
        </Async>

        <!--异步-->
        <Async name="AsyncAlarmLog">
            <AppenderRef ref="AlarmLog"/>
        </Async>

    </appenders>
    <!--然后定义logger，只有定义了logger并引入的appender，appender才会生效-->
    <loggers>
        <root level="INFO">
            <appender-ref ref="AsyncConsole"/>
            <!-- AlarmLog 处理有打印INFO级别的日志，如果level是INFO级别会循环调用，抛出 ERROR Recursive call to appender ALARM_LOG，所以最好将ALARM_LOG 的级别设置为ERROR -->
            <appender-ref ref="AsyncAlarmLog" level="ERROR" />
        </root>
    </loggers>
</configuration>
```

- **logback** : 配置项说明见[2.3.3 日志配置中设置](#233-日志配置中设置)
```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration debug="true">

    <appender name="Console" class="ch.qos.logback.core.ConsoleAppender">
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
        </encoder>
    </appender>

    <appender name="AlarmLog" class="com.future94.alarm.log.core.enhance.logback.AlarmLogLogbackAsyncAppender">
        <warnExceptionExtend>false</warnExceptionExtend>
        <doWarnException>java.lang.Exception,java.lang.RuntimeException</doWarnException>
        <appender-ref ref="Console"/>
    </appender>

    <appender name="AsyncConsole" class="ch.qos.logback.classic.AsyncAppender">
        <discardingThreshold>0</discardingThreshold>
        <appender-ref ref="Console"/>
    </appender>


    <appender name="AsyncAlarmLog" class="ch.qos.logback.classic.AsyncAppender">
        <discardingThreshold>0</discardingThreshold>
        <appender-ref ref="AlarmLog"/>
    </appender>


    <root level="INFO">
        <appender-ref ref="AsyncConsole" />
        <appender-ref ref="AsyncAlarmLog" level="ERROR" />
    </root>
</configuration>
```

### 2.3 提供多种方式获取指定的日志异常信息

#### 2.3.1 系统全局配置
- spring.alarm-log.do-warn-exception : 获取日志中指定的异常类全路径信息，类型为List.
- spring.alarm-log.warn-exception-extend : 获取日志中指定异常信息是否启动继承判断。如：do-warn-exception为java.lang.Throwable，warn-exception-extend为true，则所有java.lang.Throwable的子类（java.lang.Exception、java.lang.RuntimeException、java.io.IOException等）都会触发警告事件。
```yaml
spring:
    alarm-log:
        warn-exception-extend: false
        do-warn-exception:
            - java.lang.Throwable
            - java.lang.Exception
```

#### 2.3.2 继承、实现指定类
- com.future94.alarm.log.common.exception.**AlarmLogException** : 此类继承java.lang.Exception类，继承AlarmLogException类，即当日志事件中抛出此时触发警告事件。

- com.future94.alarm.log.common.exception.**AlarmLogRuntimeException** : 此类继承java.lang.RuntimeException类，继承AlarmLogException类，即当日志事件中抛出此时触发警告事件。

- com.future94.alarm.log.common.exception.**AlarmLogDoWarnException** : 实现AlarmLogDoWarnException接口，即当日志事件中抛出此时触发警告事件。<font color="red">注意：目前此接口还需要是java.lang.Throwable子类，以后会修改此限制。由于java是单继承，目前项目中不方便继承AlarmLog异常时，可以使用实现此接口的方式</font>。

#### 2.3.3 日志配置中设置

- **log4j** : doWarnException、warnExceptionExtend配置意义与上面一致。
```xml
<appender name="AlarmLog" class="com.future94.alarm.log.core.enhance.log4j.AlarmLogLog4jAsyncAppender">
    <param name="doWarnException" value="false"/>
    <param name="warnExceptionExtend" value="java.lang.Exception,java.lang.RuntimeException"/>
    <appender-ref ref="Console"/>
</appender>
```

- **log4j2** :  doWarnException、warnExceptionExtend配置意义与上面一致。<font color="red">注意：标签必须为AlarmLog，name属性可以随意</font>。
```xml
<AlarmLog name="AlarmLog">
    <warnExceptionExtend>false</warnExceptionExtend>
    <doWarnException>java.lang.Exception,java.lang.RuntimeException</doWarnException>
</AlarmLog>
```

- **logback** : doWarnException、warnExceptionExtend配置意义与上面一致。
```xml
<appender name="AlarmLog" class="com.future94.alarm.log.core.enhance.logback.AlarmLogLogbackAsyncAppender">
    <warnExceptionExtend>false</warnExceptionExtend>
    <doWarnException>java.lang.Exception,java.lang.RuntimeException</doWarnException>
    <appender-ref ref="Console"/>
</appender>
```

#### 2.3.4 使用@Alarm注解
- doWarnException、warnExceptionExtend配置意义与上面一致。
- 注解加载类上时，注解设置作用域为当前类，只对当前类生效。
- 注解加载方法上时，注解设置作用域为当前方法，只对当前方法生效。
```java
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Alarm {

    Class<? extends Throwable>[] doWarnException() default {Throwable.class};

    boolean warnExceptionExtend() default false;
}
```

**示例** ：
- test1只有在抛出Exception时候才会触发。
- test2当抛出TestAspectException或者Exception时都会触发。
```java
@RestController
@Alarm(doWarnException = Exception.class, warnExceptionExtend = false)
public class TestController {

    @GetMapping("/test1")
    public void test1() {
        logger.error("test4", new TestAspectException());
    }

    @GetMapping("/test2")
    @Alarm(doWarnException = TestAspectException.class, warnExceptionExtend = false)
    public void test1() {
        logger.error("test2", new TestAspectException());
    }

}
```

### 2.4 提供多种监控通知方式

多种方式可以同时使用，设置其enabled为true即可。

#### 2.4.1 邮件
```yaml
spring:
    alarm-log:
        warn:
            mail:
                enabled: true
                smtpHost: xxx
                smtpPort: xxx
                # 邮箱,多个用逗号分开
                to: future94@qq.com,xxx
                from: xxx
                username: xxx
                password: xxx
```
#### 2.4.2 企业微信
```yaml
spring:
    alarm-log:
        warn:
            workweixin:
                enabled: true
                # 企业ID,多个用逗号分开
                to: WeiLai,xxx
                applicationId: xxx
                corpid: xxx
                corpsecret: xxx
```

#### 2.4.3 钉钉群
```yaml
spring:
    alarm-log:
        warn:
            dingtalk:
                enabled: true
                token: xxx
                secret: xxx
```

## 3. SpringMvc

具体示例可以查看`alarm-log-examples-spring-mv`包

### 3.1 集成
#### 引入核心包依赖
```xml
<dependency>
    <groupId>com.future94</groupId>
    <artifactId>alarm-log-core</artifactId>
    <version>${latest.version}</version>
</dependency>
```

### 3.2 引入日志监控

#### 3.2.1 同步方式
- **log4j** : 配置项说明见[3.3.3 日志配置中设置](#333-日志配置中设置)
```xml
<!DOCTYPE log4j:configuration SYSTEM "log4j.dtd">
<log4j:configuration>
    <appender name="Console" class="org.apache.log4j.ConsoleAppender">
        <layout class="org.apache.log4j.PatternLayout">
            <param name="ConversionPattern" value="%d{yyyy-MM-dd HH:mm:ss,SSS} [%p] %m  >> %c:%L%n"/>
        </layout>
    </appender>

    <!--这里替换成AspectLog4jAsyncAppender-->
    <appender name="AlarmLog" class="com.future94.alarm.log.core.enhance.log4j.AlarmLogLog4jAsyncAppender">
        <param name="doWarnException" value="false"/>
        <param name="warnExceptionExtend" value="java.lang.Exception,java.lang.RuntimeException"/>
        <appender-ref ref="Console"/>
    </appender>

    <root>
        <priority value="info" />
        <appender-ref ref="Console"/>
        <appender-ref ref="AlarmLog"/>
    </root>
</log4j:configuration>
```

- **log4j2** : 配置项说明见[3.3.3 日志配置中设置](#333-日志配置中设置)
```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration debug="true">
    <!--先定义所有的appender-->
    <appenders>
        <!--控制台日志-->
        <Console name="Console" target="SYSTEM_OUT">
            <PatternLayout pattern="%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"/>
        </Console>

        <AlarmLog name="AlarmLog">
            <warnExceptionExtend>false</warnExceptionExtend>
            <doWarnException>java.lang.Exception,java.lang.RuntimeException</doWarnException>
        </AlarmLog>

    </appenders>
    <!--然后定义logger，只有定义了logger并引入的appender，appender才会生效-->
    <loggers>
        <root level="INFO">
            <appender-ref ref="Console"/>
            <!-- AlarmLog 处理有打印INFO级别的日志，如果level是INFO级别会循环调用，抛出 ERROR Recursive call to appender ALARM_LOG，所以最好将ALARM_LOG 的级别设置为ERROR -->
            <appender-ref ref="AlarmLog" level="ERROR" />
        </root>
    </loggers>
</configuration>
```

- **logback** : 配置项说明见[3.3.3 日志配置中设置](#333-日志配置中设置)
```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration debug="true">

    <appender name="Console" class="ch.qos.logback.core.ConsoleAppender">
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
        </encoder>
    </appender>

    <appender name="AlarmLog" class="com.future94.alarm.log.core.enhance.logback.AlarmLogLogbackAsyncAppender">
        <warnExceptionExtend>false</warnExceptionExtend>
        <doWarnException>java.lang.Exception,java.lang.RuntimeException</doWarnException>
        <includeCallerData>true</includeCallerData>
        <appender-ref ref="Console"/>
    </appender>

    <root level="INFO">
        <appender-ref ref="Console" />
        <appender-ref ref="AlarmLog" level="ERROR" />
    </root>
</configuration>
```

#### 3.2.2 异步方式

- **log4j** : 配置项说明见[3.3.3 日志配置中设置](#333-日志配置中设置)
```xml
<!DOCTYPE log4j:configuration SYSTEM "log4j.dtd">
<log4j:configuration>
    <appender name="Console" class="org.apache.log4j.ConsoleAppender">
        <layout class="org.apache.log4j.PatternLayout">
            <param name="ConversionPattern" value="%d{yyyy-MM-dd HH:mm:ss,SSS} [%p] %m  >> %c:%L%n"/>
        </layout>
    </appender>

    <!--这里替换成AspectLog4jAsyncAppender-->
    <appender name="AlarmLog" class="com.future94.alarm.log.core.enhance.log4j.AlarmLogLog4jAsyncAppender">
        <param name="doWarnException" value="false"/>
        <param name="warnExceptionExtend" value="java.lang.Exception,java.lang.RuntimeException"/>
        <appender-ref ref="Console"/>
    </appender>

    <appender name="AsyncConsole" class="org.apache.log4j.AsyncAppender">
        <appender-ref ref="Console"/>
    </appender>

    <appender name="AsyncAlarmLog" class="org.apache.log4j.AsyncAppender">
        <appender-ref ref="AlarmLog"/>
    </appender>

    <root>
        <priority value="info" />
        <appender-ref ref="AsyncConsole"/>
        <appender-ref ref="AsyncAlarmLog"/>
    </root>
</log4j:configuration>
```

- **log4j2** : 配置项说明见[3.3.3 日志配置中设置](#333-日志配置中设置)
```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration debug="true">
    <!--先定义所有的appender-->
    <appenders>
        <!--控制台日志-->
        <Console name="Console" target="SYSTEM_OUT">
            <PatternLayout pattern="%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"/>
        </Console>

        <AlarmLog name="AlarmLog">
            <warnExceptionExtend>false</warnExceptionExtend>
            <doWarnException>java.lang.Exception,java.lang.RuntimeException</doWarnException>
        </AlarmLog>

        <!--异步输出到控制台-->
        <Async name="AsyncConsole">
            <AppenderRef ref="Console"/>
        </Async>

        <!--异步-->
        <Async name="AsyncAlarmLog">
            <AppenderRef ref="AlarmLog"/>
        </Async>

    </appenders>
    <!--然后定义logger，只有定义了logger并引入的appender，appender才会生效-->
    <loggers>
        <root level="INFO">
            <appender-ref ref="AsyncConsole"/>
            <!-- AlarmLog 处理有打印INFO级别的日志，如果level是INFO级别会循环调用，抛出 ERROR Recursive call to appender ALARM_LOG，所以最好将ALARM_LOG 的级别设置为ERROR -->
            <appender-ref ref="AsyncAlarmLog" level="ERROR" />
        </root>
    </loggers>
</configuration>
```

- **logback** : 配置项说明见[3.3.3 日志配置中设置](#333-日志配置中设置)
```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration debug="true">

    <appender name="Console" class="ch.qos.logback.core.ConsoleAppender">
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
        </encoder>
    </appender>

    <appender name="AlarmLog" class="com.future94.alarm.log.core.enhance.logback.AlarmLogLogbackAsyncAppender">
        <warnExceptionExtend>false</warnExceptionExtend>
        <doWarnException>java.lang.Exception,java.lang.RuntimeException</doWarnException>
        <appender-ref ref="Console"/>
    </appender>

    <appender name="AsyncConsole" class="ch.qos.logback.classic.AsyncAppender">
        <discardingThreshold>0</discardingThreshold>
        <appender-ref ref="Console"/>
    </appender>


    <appender name="AsyncAlarmLog" class="ch.qos.logback.classic.AsyncAppender">
        <discardingThreshold>0</discardingThreshold>
        <appender-ref ref="AlarmLog"/>
    </appender>


    <root level="INFO">
        <appender-ref ref="AsyncConsole" />
        <appender-ref ref="AsyncAlarmLog" level="ERROR" />
    </root>
</configuration>
```

### 3.3 提供多种方式获取指定的日志异常信息

#### 3.3.1 系统全局配置
- spring.alarm-log.**do-warn-exception** : 获取日志中指定的异常类全路径信息，类型为List.
- spring.alarm-log.**warn-exception-extend** : 获取日志中指定异常信息是否启动继承判断。如：do-warn-exception为java.lang.Throwable，warn-exception-extend为true，则所有java.lang.Throwable的子类（java.lang.Exception、java.lang.RuntimeException、java.io.IOException等）都会触发警告事件。
```xml
<bean id="alarmLogConfigContext" class="com.future94.alarm.log.common.cache.AlarmLogContext">
    <property name="warnExceptionExtend" value="true" />
    <property name="doWarnExceptionList">
        <list>
            <value>java.lang.Exception</value>
            <value>java.lang.RuntimeException</value>
        </list>
    </property>
</bean>
```

#### 3.3.2 继承、实现指定类
- com.future94.alarm.log.common.exception.**AlarmLogException** : 此类继承java.lang.Exception类，继承AlarmLogException类，即当日志事件中抛出此时触发警告事件。

- com.future94.alarm.log.common.exception.**AlarmLogRuntimeException** : 此类继承java.lang.RuntimeException类，继承AlarmLogException类，即当日志事件中抛出此时触发警告事件。

- com.future94.alarm.log.common.exception.**AlarmLogDoWarnException** : 实现AlarmLogDoWarnException接口，即当日志事件中抛出此时触发警告事件。<font color="red">注意：目前此接口还需要是java.lang.Throwable子类，以后会修改此限制。由于java是单继承，目前项目中不方便继承AlarmLog异常时，可以使用实现此接口的方式</font>。

#### 3.3.3 日志配置中设置

- **log4j** : doWarnException、warnExceptionExtend配置意义与上面一致。
```xml
<appender name="AlarmLog" class="com.future94.alarm.log.core.enhance.log4j.AlarmLogLog4jAsyncAppender">
    <param name="doWarnException" value="false"/>
    <param name="warnExceptionExtend" value="java.lang.Exception,java.lang.RuntimeException"/>
    <appender-ref ref="Console"/>
</appender>
```

- **log4j2** :  doWarnException、warnExceptionExtend配置意义与上面一致。<font color="red">注意：标签必须为AlarmLog，name属性可以随意</font>。
```xml
<AlarmLog name="AlarmLog">
    <warnExceptionExtend>false</warnExceptionExtend>
    <doWarnException>java.lang.Exception,java.lang.RuntimeException</doWarnException>
</AlarmLog>
```

- **logback** : doWarnException、warnExceptionExtend配置意义与上面一致。
```xml
<appender name="AlarmLog" class="com.future94.alarm.log.core.enhance.logback.AlarmLogLogbackAsyncAppender">
    <warnExceptionExtend>false</warnExceptionExtend>
    <doWarnException>java.lang.Exception,java.lang.RuntimeException</doWarnException>
    <appender-ref ref="Console"/>
</appender>
```

#### 3.3.4 使用@Alarm注解

需要引入如下依赖
```xml
<dependency>
    <groupId>com.future94</groupId>
    <artifactId>alarm-log-aspect</artifactId>
    <version>${latest.version}</version>
</dependency>
```
并启动aop，保证可以扫描到`com.future94.alarm.log.aspect`包
```xml
<context:component-scan base-package="com.future94.alarm.log" />
<aop:aspectj-autoproxy />
<mvc:annotation-driven />
<aop:aspectj-autoproxy proxy-target-class="true" />
<aop:config proxy-target-class="true"/>
```

- doWarnException、warnExceptionExtend配置意义与上面一致。
- 注解加载类上时，注解设置作用域为当前类，只对当前类生效。
- 注解加载方法上时，注解设置作用域为当前方法，只对当前方法生效。
```java
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Alarm {

    Class<? extends Throwable>[] doWarnException() default {Throwable.class};

    boolean warnExceptionExtend() default false;
}
```

**示例** ：
- test1只有在抛出Exception时候才会触发。
- test2当抛出TestAspectException或者Exception时都会触发。
```java
@RestController
@Alarm(doWarnException = Exception.class, warnExceptionExtend = false)
public class TestController {

    @GetMapping("/test1")
    public void test1() {
        logger.error("test4", new TestAspectException());
    }

    @GetMapping("/test2")
    @Alarm(doWarnException = TestAspectException.class, warnExceptionExtend = false)
    public void test1() {
        logger.error("test2", new TestAspectException());
    }

}
```

### 3.4 提供多种监控通知方式

多种方式可以同时使用，将其对应的bean添加到AlarmLogWarnServiceFactory即可。

```xml
<bean id="factory" class="com.future94.alarm.log.warn.common.factory.AlarmLogWarnServiceFactory">
    <constructor-arg>
        <list>
            <ref bean="mailWarnService" />
            <ref bean="dingtalkWarnService" />
            <ref bean="workWeixinWarnService" />
        </list>
    </constructor-arg>
</bean>
```

#### 3.4.1 邮件
```xml
<bean id="mailWarnService" class="com.future94.alarm.log.warn.mail.MailWarnService">
        <constructor-arg index="0" value="${alarmLog.warn.mail.smtpHost}"/>
        <constructor-arg index="1" value="${alarmLog.warn.mail.smtpPort}"/>
        <!--多个用逗号分割-->
        <constructor-arg index="2" value="${alarmLog.warn.mail.to}"/>
        <constructor-arg index="3" value="${alarmLog.warn.mail.from}"/>
        <constructor-arg index="4" value="${alarmLog.warn.mail.username}"/>
        <constructor-arg index="5" value="${alarmLog.warn.mail.password}"/>
</bean>

<bean id="factory" class="com.future94.alarm.log.warn.common.factory.AlarmLogWarnServiceFactory">
    <constructor-arg>
        <list>
            <ref bean="mailWarnService" />
        </list>
    </constructor-arg>
</bean>
```

#### 3.4.2 企业微信

```xml
<bean id="workWeixinWarnService" class="com.future94.alarm.log.warn.workweixin.WorkWeixinWarnService">
    <!--多个用逗号分割-->
    <constructor-arg index="0" value="${alarmLog.warn.workweixin.to}"/>
    <constructor-arg index="1" value="${alarmLog.warn.workweixin.applicationId}"/>
    <constructor-arg index="2" value="${alarmLog.warn.workweixin.corpid}"/>
    <constructor-arg index="3" value="${alarmLog.warn.workweixin.corpsecret}"/>
</bean>

<bean id="factory" class="com.future94.alarm.log.warn.common.factory.AlarmLogWarnServiceFactory">
    <constructor-arg>
        <list>
            <ref bean="workWeixinWarnService" />
        </list>
    </constructor-arg>
</bean>
```

#### 3.4.3 钉钉群

```xml
<bean id="dingtalkWarnService" class="com.future94.alarm.log.warn.dingtalk.DingtalkWarnService">
        <constructor-arg index="0" value="${alarmLog.warn.dingtalk.token}"/>
        <constructor-arg index="1" value="${alarmLog.warn.dingtalk.secret}"/>
    </bean>

<bean id="factory" class="com.future94.alarm.log.warn.common.factory.AlarmLogWarnServiceFactory">
    <constructor-arg>
        <list>
            <ref bean="dingtalkWarnService" />
        </list>
    </constructor-arg>
</bean>
```