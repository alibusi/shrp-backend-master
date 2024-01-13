package nwafu.cie.shrp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import nwafu.cie.shrp.model.entity.Project;

import java.util.List;

/**
 * @author Xiao
 * @description 针对表【basic_project(工程)】的数据库操作Service
 * @createDate 2023-12-01 11:56:43
 */
public interface ProjectService extends IService<Project> {

    public List<Project> getByName(String name);

}
