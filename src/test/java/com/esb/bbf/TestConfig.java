package com.esb.bbf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

public class TestConfig {

    @SpringBootApplication
    @ComponentScan(basePackages = {
            "com.esb.bbf.console.dao",
            "com.esb.bbf.config"
    }
    )
    @MapperScan("com.esb.bbf.console.dao")
    public static class DaoConfig {


    }

    @SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,
            RedisAutoConfiguration.class,
            RedisRepositoriesAutoConfiguration.class})
    @MapperScan("com.esb.bbf.console.dao")
    @EnableTransactionManagement(order = 2000)
    public static class ServiceConfig {

      /*  @Bean(destroyMethod = "shutdown")
        public EmbeddedDatabase dataSource() {
            final Logger logger = LoggerFactory.getLogger(this.getClass());
            return new EmbeddedDatabaseBuilder().
                    setType(EmbeddedDatabaseType.H2).
                    addScript("order/db.sql").
                    addScript("order/dataMap.sql").
                    setScriptEncoding("UTF-8").
                    build();
        }

        @Bean
        public ObjectMapper objectMapper() {
            return new ObjectMapper();
        }

        @Bean
        public RestTemplate restTemplate() {
            return new RestTemplate();
        }

        @Bean
        public MockRestServiceServer mockRestServiceServer() {
            return MockRestServiceServer.createServer(restTemplate());
        }*/
    }

    @SpringBootApplication
    @MapperScan("com.esb.bbf.console.dao")
    @ComponentScan(basePackages = {"com.esb.bbf"})
    public static class ApiConfig {

    }

}
