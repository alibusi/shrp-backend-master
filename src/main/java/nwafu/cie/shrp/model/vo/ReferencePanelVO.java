package nwafu.cie.shrp.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
@Data
public class ReferencePanelVO {
    /**
     * 面板id
     */
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
}