package nwafu.cie.shrp.model.dto;

import lombok.Data;

@Data
public class ProjectAddDTO {

    /**
     * 工程名
     */
    private String projectName;

    /**
     * 工程模式
     */
    private String mode;

    /**
     * 参考面板
     */
    private String referencePanel;

    /**
     * 参考基因型
     */
    private String referenceGenome;

    /**
     * 邮箱
     */
    private String email;

    private String[] files;

}
