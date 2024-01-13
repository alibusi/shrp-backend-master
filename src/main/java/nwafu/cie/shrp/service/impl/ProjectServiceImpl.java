package nwafu.cie.shrp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import nwafu.cie.shrp.mapper.ProjectMapper;
import nwafu.cie.shrp.model.entity.Project;
import nwafu.cie.shrp.service.ProjectService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Xiao
 * @description 针对表【basic_project(工程)】的数据库操作Service实现
 * @createDate 2023-12-01 11:56:43
 */
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project>
        implements ProjectService {

    @Override
    public List<Project> getByName(String name) {
        QueryWrapper<Project> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(name), "name", name);
        return this.list(queryWrapper);
    }
}




