package nwafu.cie.shrp.model.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ImputationVO {

    private Long id;

    /**
     * 染色体号
     */
    private String chr;

    /**
     * 填充状态
     */
    private Byte status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 完成时间
     */
    private Date completeTime;

    private ProjectVO projectVO;
}
