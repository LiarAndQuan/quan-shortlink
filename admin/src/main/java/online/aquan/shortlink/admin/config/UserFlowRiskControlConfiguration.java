package online.aquan.shortlink.admin.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "link.flow-limit")
public class UserFlowRiskControlConfiguration {
    private Boolean enable;

    private String timeWindow;

    private Long maxAccessCount;
}
