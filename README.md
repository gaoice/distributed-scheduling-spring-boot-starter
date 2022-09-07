# Distributed Scheduling
增强 Spring `@Scheduled` 注解，使其支持分布式的定时调度。服务有多个实例的情况下，不同实例之间不会重复执行相同的定时任务。
## 使用
先决条件：配置 redis 。

使用方法：引入注解 `@EnableDistributedScheduling` 即可生效。

```java
@EnableDistributedScheduling
@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
```

已提交至 `Maven` 中央仓库。

`Maven`

```xml
<dependency>
    <groupId>com.gaoice</groupId>
    <artifactId>distributed-scheduling-spring-boot-starter</artifactId>
    <version>1.2</version>
</dependency>
```

## 版本

- v 1.2，新增 `@NonDistributed` 注解，用来标记非分布式的定时任务（即不进行增强处理）。
- v 1.1，支持直接使用 `@EnableDistributedScheduling` 注解，不必再手动引入 `@EnableScheduling` 注解。
- v 1.0，增强 `@Scheduled` 注解，使其支持分布式的定时调度。
