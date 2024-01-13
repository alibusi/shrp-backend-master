package nwafu.cie.shrp.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 参考面板数据
 * @TableName reference_panel
 */
@TableName(value ="reference_panel")
@Data
public class ReferencePanel implements Serializable {
    /**
     * 面板id
     */
    @TableId
    private Long id;

    /**
     * 面板名
     */
    private String name;

    /**
     * 单倍型
     */
    private Integer haplotypes;

    /**
     * 品种
     */
    private Integer breeds;

    /**
     * snp位点
     */
    private BigDecimal snpSites;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}