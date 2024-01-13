package nwafu.cie.shrp.controller;

import lombok.extern.slf4j.Slf4j;
import nwafu.cie.shrp.common.BaseResponse;
import nwafu.cie.shrp.common.ErrorCode;
import nwafu.cie.shrp.common.ResultUtils;
import nwafu.cie.shrp.exception.BusinessException;
import nwafu.cie.shrp.manager.CosManager;
import nwafu.cie.shrp.util.FileUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {

    @Resource
    private CosManager cosManager;

    @PostMapping("/upload")
    public BaseResponse<String> upload(@RequestPart("file") MultipartFile multipartFile, @RequestParam("sessionId") String sessionId) {
        // 先校验文件
        validFile(multipartFile);

        // 获取系统临时目录
        String tempDir = System.getProperty("java.io.tmpdir");
        // 使用sessionId创建上传目录
        String uploadDir = tempDir + File.separator + "imputation" + File.separator + sessionId;
        File directory = new File(uploadDir);
        // 如果目录不存在，则创建
        if (!directory.exists()) {
            directory.mkdirs();
        }
        String uuid = RandomStringUtils.randomAlphanumeric(8);
        String fileName = uuid + "-" + multipartFile.getOriginalFilename();
        File file = null;
        try {
            // 上传文件
            file = new File(uploadDir + File.separator + fileName);
            log.info("路径: " + file.getAbsolutePath());
            multipartFile.transferTo(file);
        } catch (Exception e) {
            log.error("file upload error, filepath = " + file.getAbsolutePath(), e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "上传失败");
        } finally {
        }
        return ResultUtils.success(file.getAbsolutePath());
    }

    private void validFile(MultipartFile multipartFile) {
        if (!FileUtil.containChr(multipartFile)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件名不合规范");
        }
        try {
            if (!FileUtil.isGzipFile(multipartFile)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件类型出错");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
