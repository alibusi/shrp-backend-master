package generator.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

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
     * 创建时间
     */
    private Date createtime;

    /**
     * 更新时间
     */
    private Date updatetime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}