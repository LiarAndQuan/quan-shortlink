package online.aquan.shortlink;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication(scanBasePackages = {
        "online.aquan.shortlink.admin",
        "online.aquan.shortlink.project",
        "online.aquan.shortlink.aggregation"
})
@MapperScan(value = {
        "online.aquan.shortlink.admin.dao.mapper",
        "online.aquan.shortlink.project.dao.mapper"
})
public class AggregationServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AggregationServiceApplication.class, args);
    }
}