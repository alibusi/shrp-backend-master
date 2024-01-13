package nwafu.cie.shrp.config;

import nwafu.cie.shrp.config.PanelMappingConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PanelMappingConfigTest {

    @Resource
    private PanelMappingConfig panelMappingConfig;

    @Test
    void getMapping() {
        System.out.println(panelMappingConfig.getMapping());
    }
}