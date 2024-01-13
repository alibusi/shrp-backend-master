package nwafu.cie.shrp.model.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ProjectVO {

    List<ImputationVO> imputationVOS;

    /**
     * 工程ID
     */
    private Long id;

    /**
     * 工程名
     */
    private String name;

    /**
     * 工程模式
     */
    private String mode;

    /**
     * 创建时间
     */
    private Date createTime;
}
