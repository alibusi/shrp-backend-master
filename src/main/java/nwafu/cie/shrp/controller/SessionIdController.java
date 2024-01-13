package nwafu.cie.shrp.controller;

import nwafu.cie.shrp.common.BaseResponse;
import nwafu.cie.shrp.common.ResultUtils;
import nwafu.cie.shrp.util.SessionIdGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/session")
public class SessionIdController {

    @GetMapping("/generateSessionId")
    public BaseResponse<String> generateSessionId() {
        String sessionId = SessionIdGenerator.generateSessionId();
        return ResultUtils.success(sessionId);
    }
}
