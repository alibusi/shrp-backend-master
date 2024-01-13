package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.Project;
import generator.service.ProjectService;
import generator.mapper.ProjectMapper;
import org.springframework.stereotype.Service;

/**
* @author Xiao
* @description 针对表【basic_project(工程)】的数据库操作Service实现
* @createDate 2023-12-01 11:56:43
*/
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project>
    implements ProjectService{

}




