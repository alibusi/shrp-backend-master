package nwafu.cie.shrp.model.dto;

import lombok.Data;
import nwafu.cie.shrp.common.Pagination;

@Data
public class ImputationQueryDTO extends Pagination {

    private String projectName;

    private String chr;

}
