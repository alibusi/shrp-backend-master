package nwafu.cie.shrp.service.impl;

import nwafu.cie.shrp.model.entity.ReferencePanel;
import nwafu.cie.shrp.service.ReferencePanelService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReferencePanelServiceImplTest {

    @Resource
    private ReferencePanelService referencePanelService;

    @Test
    void add() {
        ReferencePanel referencePanel = new ReferencePanel();
        referencePanel.setName("Africa Sample Reference Panel (ARS-UI_Ramb_v2.0)");
        referencePanel.setHaplotypes(978);
        referencePanel.setBreeds(113);
        referencePanel.setSnpSites(BigDecimal.valueOf(26.1));
        referencePanelService.save(referencePanel);
    }

}