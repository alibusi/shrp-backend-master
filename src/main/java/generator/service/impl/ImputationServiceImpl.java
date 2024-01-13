package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.Imputation;
import generator.service.ImputationService;
import generator.mapper.ImputationMapper;
import org.springframework.stereotype.Service;

/**
* @author Xiao
* @description 针对表【basic_imputation(基因型填充)】的数据库操作Service实现
* @createDate 2024-01-06 17:44:12
*/
@Service
public class ImputationServiceImpl extends ServiceImpl<ImputationMapper, Imputation>
    implements ImputationService{

}




