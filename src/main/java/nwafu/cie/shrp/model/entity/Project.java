package nwafu.cie.shrp.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 工程
 * @TableName basic_project
 */
@TableName(value ="basic_project")
@Data
public class Project implements Serializable {
    /**
     * 工程ID
     */
    @TableId
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
     * 邮箱
     */
    private String email;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}