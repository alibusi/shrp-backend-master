package nwafu.cie.shrp.common;

import lombok.Data;

/**
 * 分页请求
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Data
public class Pagination {
    /**
     * 页面大小
     */
    private long size = 10;

    /**
     * 当前页号
     */
    private long current = 1;

    /**
     * 排序字段
     */
    private String sortField;

    /**
     * 排序顺序（默认升序）
     */
    private String sortOrder = "ascent";
}
