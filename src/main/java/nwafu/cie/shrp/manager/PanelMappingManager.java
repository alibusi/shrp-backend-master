package nwafu.cie.shrp.manager;

import nwafu.cie.shrp.config.PanelMappingConfig;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class PanelMappingManager {
    @Resource
    private PanelMappingConfig panelMappingConfig;

    public String getFileName(String referencePanel, String referenceGenome) {
        return panelMappingConfig.getMapping().get(referencePanel).get(referenceGenome);
    }
}
