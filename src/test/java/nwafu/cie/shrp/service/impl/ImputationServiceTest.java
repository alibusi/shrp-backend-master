package nwafu.cie.shrp.service.impl;

import nwafu.cie.shrp.service.ImputationService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ImputationServiceTest {

    @Resource
    private ImputationService imputationService;

    @Test
    void executeBeagleJar() {
        Long id = 1730469785189085185L;
        File file = new File("D:\\Personal Files\\Code\\resource\\sheep339.40k.gatkfinal.snp.maf0.05.miss0.1.imp.chr1.phase.vcf.gz");
//        imputationService.executeBeagleJar(id, file);
    }

}