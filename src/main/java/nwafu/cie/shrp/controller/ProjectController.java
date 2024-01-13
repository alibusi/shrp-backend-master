package nwafu.cie.shrp.controller;

import cn.hutool.core.bean.BeanUtil;
import nwafu.cie.shrp.common.BaseResponse;
import nwafu.cie.shrp.common.ErrorCode;
import nwafu.cie.shrp.common.ResultUtils;
import nwafu.cie.shrp.exception.BusinessException;
import nwafu.cie.shrp.model.dto.ProjectAddDTO;
import nwafu.cie.shrp.model.entity.Imputation;
import nwafu.cie.shrp.model.entity.Project;
import nwafu.cie.shrp.rabbitmq.MyMessageProducer;
import nwafu.cie.shrp.service.ImputationService;
import nwafu.cie.shrp.service.ProjectService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/project")
public class ProjectController {

    private static final Pattern PATTERN = Pattern.compile("chr(1[0-9]|2[0-6]|[1-9])");

    @Resource
    private ProjectService projectService;

    @Resource
    private ImputationService imputationService;

    @Resource
    private MyMessageProducer myMessageProducer;

    @PostMapping("/add")
    @Transactional
    public BaseResponse<Boolean> add(@RequestBody ProjectAddDTO projectAddDTO) throws IOException {
        Project project = BeanUtil.copyProperties(projectAddDTO, Project.class);
        String projectName = projectAddDTO.getProjectName();
        project.setName(projectName);
        projectService.save(project);
        String referencePanel = projectAddDTO.getReferencePanel();
        String referenceGenome = projectAddDTO.getReferenceGenome();
        String[] files = projectAddDTO.getFiles();
        for (String file : files) {
            Imputation imputation = new Imputation();
            String chr = "";
            // 匹配文件名中的染色体号
            Matcher matcher = PATTERN.matcher(file);
            if (matcher.find()) {
                chr = matcher.group(1);
            }
            if ("".equals(chr)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件名格式错误");
            }
            imputation.setChr(chr);
            imputation.setProjectId(project.getId());
            imputation.setReferencePanel(referencePanel);
            imputation.setReferenceGenome(referenceGenome);
            imputation.setInput(file);
            imputation.setStatus((byte) 0);
            imputationService.save(imputation);

            // 执行 Beagle.jar
            myMessageProducer.sendMessage("imputation_exchange", "my_routingKey", String.valueOf(imputation.getId()));
        }
        return ResultUtils.success(Boolean.TRUE);
    }
}
