package nwafu.cie.shrp.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "panel")
@Data
public class PanelMappingConfig {
    private Map<String, Map<String, String>> mapping;

    public Map<String, Map<String, String>> getMapping() {
        return mapping;
    }
}
