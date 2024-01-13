package generator.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 基因型填充
 * @TableName basic_imputation
 */
@TableName(value ="basic_imputation")
@Data
public class Imputation implements Serializable {
    /**
     * 
     */
    @TableId
    private Long id;

    /**
     * 染色体号
     */
    private String chr;

    /**
     * 项目ID
     */
    private Long projectId;

    /**
     * 参考面板
     */
    private String referencePanel;

    /**
     * 参考基因型
     */
    private String referenceGenome;

    /**
     * 输入文件
     */
    private String input;

    /**
     * 输出文件
     */
    private String output;

    /**
     * 填充状态
     */
    private Integer status;

    /**
     * 完成时间
     */
    private Date completeTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}