package nwafu.cie.shrp.controller;

import cn.hutool.core.bean.BeanUtil;
import nwafu.cie.shrp.common.BaseResponse;
import nwafu.cie.shrp.common.ResultUtils;
import nwafu.cie.shrp.model.entity.ReferencePanel;
import nwafu.cie.shrp.model.vo.ReferencePanelVO;
import nwafu.cie.shrp.service.ReferencePanelService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/referencePanel")
public class ReferencePanelController {

    @Resource
    private ReferencePanelService referencePanelService;

    @GetMapping("/list")
    public BaseResponse<List<ReferencePanelVO>> list() {
        List<ReferencePanel> referencePanelList = referencePanelService.list();
        return ResultUtils.success(BeanUtil.copyToList(referencePanelList, ReferencePanelVO.class));
    }
}
