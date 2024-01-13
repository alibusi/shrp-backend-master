package nwafu.cie.shrp.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import nwafu.cie.shrp.model.dto.ImputationQueryDTO;
import nwafu.cie.shrp.model.entity.Imputation;
import nwafu.cie.shrp.model.vo.ImputationVO;

import java.io.File;
import java.io.IOException;

/**
* @author Xiao
* @description 针对表【basic_imputation(基因型填充)】的数据库操作Service
* @createDate 2023-12-01 11:56:43
*/
public interface ImputationService extends IService<Imputation> {
    /**
     *
     * @param id
     * @return
     */
    void executeBeagleJar(Long id);

    QueryWrapper<Imputation> getQueryWrapper(ImputationQueryDTO imputationQueryDTO);

    Page<ImputationVO> getImputationVOPage(Page<Imputation> imputationPage);

}
