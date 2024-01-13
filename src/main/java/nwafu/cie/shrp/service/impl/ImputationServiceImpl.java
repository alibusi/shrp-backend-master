package nwafu.cie.shrp.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import main.Main;
import nwafu.cie.shrp.common.ErrorCode;
import nwafu.cie.shrp.exception.BusinessException;
import nwafu.cie.shrp.manager.PanelMappingManager;
import nwafu.cie.shrp.mapper.ImputationMapper;
import nwafu.cie.shrp.model.dto.ImputationQueryDTO;
import nwafu.cie.shrp.model.entity.Imputation;
import nwafu.cie.shrp.model.entity.Project;
import nwafu.cie.shrp.model.vo.ImputationVO;
import nwafu.cie.shrp.model.vo.ProjectVO;
import nwafu.cie.shrp.service.ImputationService;
import nwafu.cie.shrp.service.ProjectService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Xiao
 * @description 针对表【basic_imputation(基因型填充)】的数据库操作Service实现
 * @createDate 2023-12-01 11:56:43
 */
@Service
@Slf4j
public class ImputationServiceImpl extends ServiceImpl<ImputationMapper, Imputation>
        implements ImputationService {

    private static final String BEAGLE_OUTPUT_EXTENSION = ".out";

    @Resource
    private ProjectService projectService;

    @Resource
    private PanelMappingManager panelMappingManager;

    @Override
    public void executeBeagleJar(Long id) {
        Imputation imputation = this.getById(id);
        File inputFile = new File(imputation.getInput());

        // 指定输出文件名前缀
        String out = inputFile.getAbsolutePath().substring(0, inputFile.getAbsolutePath().length() - ".vcf.gz".length()) + BEAGLE_OUTPUT_EXTENSION;
        // 创建输出文件
        File outputFile = new File(out);

        Project project = projectService.getById(imputation.getProjectId());
        String mode = project.getMode();
        if ("imputation".equals(mode)) {
            String referencePanel = imputation.getReferencePanel();
            String referenceGenome = imputation.getReferenceGenome();
            String chr = imputation.getChr();

            String panelName = panelMappingManager.getFileName(referencePanel, referenceGenome);
            if (panelName == null) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "面板不存在");
            }
            String ref = "D:\\Personal Files\\Workspace\\Graduation Project\\test\\三个小面板2.0版本" + panelName;

            performGenotypeImputation(String.format(ref, chr), inputFile.getAbsolutePath(), out);
        } else if ("phase".equals(mode)) {
            executeGenotypePhasing(inputFile.getAbsolutePath(), out);
        }

//        System.out.println("开始等待");
//        try {
//            // 等待10秒 (10,000毫秒)
//            Thread.sleep(20000);
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//            // 处理中断异常
//        }
//        System.out.println("等待结束");

        imputation.setStatus((byte) 1);
        imputation.setCompleteTime(new Date());
        // TODO 设置输出文件的地址
        imputation.setOutput(out);

        this.updateById(imputation);
    }

    public void performGenotypeImputation(String ref, String gt, String out) {
        log.info("ref = {}", ref);
        log.info("gt = {}", gt);
        log.info("out = {}", out);

        String[] cmd = {
                "ref=" + ref,
                "gt=" + gt,
                "ne=500000",
                "nthreads=" + (Runtime.getRuntime().availableProcessors() + 1),
                "out=" + out,
        };
        try {
            Main.main(cmd);
        } catch (Exception e) {
            log.error("填充失败", e);
        }
    }

    public void executeGenotypePhasing(String gt, String out) {
        String[] cmd = {
                "gt=" + gt,
                "out=" + out,
                "gp=true"
        };
        try {
            Main.main(cmd);
        } catch (Exception e) {
            log.error("分型失败", e);
        }
    }

    @Override
    public QueryWrapper<Imputation> getQueryWrapper(ImputationQueryDTO imputationQueryDTO) {
        QueryWrapper<Imputation> imputationQueryWrapper = new QueryWrapper<>();
        if (imputationQueryDTO == null) {
            return imputationQueryWrapper;
        }
        String projectName = imputationQueryDTO.getProjectName();
        String chr = imputationQueryDTO.getChr();
        String sortField = imputationQueryDTO.getSortField();
        String sortOrder = imputationQueryDTO.getSortOrder();

        List<Long> projectIdList = projectService.getByName(projectName).stream()
                .map(Project::getId).collect(Collectors.toList());

        // 拼接查询条件
        imputationQueryWrapper.in(CollectionUtils.isNotEmpty(projectIdList), "project_id", projectIdList);
        imputationQueryWrapper.eq(StringUtils.isNotBlank(chr), "chr", chr);
        // orderBy(boolean condition, boolean isAsc, R... columns)
//        imputationQueryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        imputationQueryWrapper.orderBy(true, false, "update_time");
        return imputationQueryWrapper;
    }

    @Override
    public Page<ImputationVO> getImputationVOPage(Page<Imputation> imputationPage) {
        List<Imputation> imputationList = imputationPage.getRecords();
        Page<ImputationVO> imputationVOPage = new Page<>(imputationPage.getCurrent(), imputationPage.getSize(), imputationPage.getTotal());
        if (CollectionUtils.isEmpty(imputationList)) {
            return imputationVOPage;
        }
        Set<Long> projectIdSet = imputationList.stream().map(Imputation::getProjectId).collect(Collectors.toSet());
        Map<Long, List<Project>> projectIdProjectListMap = projectService.listByIds(projectIdSet).stream()
                .collect(Collectors.groupingBy(Project::getId));

        // 填充信息
        List<ImputationVO> imputationVOList = imputationList.stream().map(imputation -> {
            ImputationVO imputationVO = BeanUtil.copyProperties(imputation, ImputationVO.class);
            Long projectId = imputation.getProjectId();
            Project project = null;
            if (projectIdProjectListMap.containsKey(projectId)) {
                project = projectIdProjectListMap.get(projectId).get(0);
            }
            imputationVO.setProjectVO(BeanUtil.copyProperties(project, ProjectVO.class));
            return imputationVO;
        }).collect(Collectors.toList());
        imputationVOPage.setRecords(imputationVOList);
        return imputationVOPage;
    }
}




