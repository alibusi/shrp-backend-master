package nwafu.cie.shrp.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ZipUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import nwafu.cie.shrp.common.BaseResponse;
import nwafu.cie.shrp.common.ErrorCode;
import nwafu.cie.shrp.common.ResultUtils;
import nwafu.cie.shrp.exception.BusinessException;
import nwafu.cie.shrp.exception.ThrowUtils;
import nwafu.cie.shrp.model.dto.ImputationDeleteDTO;
import nwafu.cie.shrp.model.dto.ImputationQueryDTO;
import nwafu.cie.shrp.model.entity.Imputation;
import nwafu.cie.shrp.model.vo.ImputationVO;
import nwafu.cie.shrp.rabbitmq.MyMessageProducer;
import nwafu.cie.shrp.service.ImputationService;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/imputation")
public class ImputationController {

    private static final Pattern PATTERN = Pattern.compile("chr(1[0-9]|2[0-6]|[1-9])");

    @Resource
    private ImputationService imputationService;

    @Resource
    private MyMessageProducer myMessageProducer;


    @PostMapping("/delete")
    public BaseResponse<Boolean> delete(@RequestBody ImputationDeleteDTO imputationDeleteDTO) {
        if (imputationDeleteDTO == null || imputationDeleteDTO.getId() < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long id = imputationDeleteDTO.getId();
        Imputation imputation = imputationService.getById(id);
        ThrowUtils.throwIf(imputation == null, ErrorCode.NOT_FOUND_ERROR);

        Boolean deleteResult = imputationService.removeById(id);
        return ResultUtils.success(deleteResult);
    }

    @PostMapping("/list/page")
    public BaseResponse<Page<ImputationVO>> list(@RequestBody ImputationQueryDTO imputationQueryDTO) {
        Long size = imputationQueryDTO.getSize();
        Long current = imputationQueryDTO.getCurrent();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<Imputation> imputationPage = imputationService.page(new Page<>(current, size), imputationService.getQueryWrapper(imputationQueryDTO));
        return ResultUtils.success(imputationService.getImputationVOPage(imputationPage));
    }

    @GetMapping("/count")
    public BaseResponse<Long> count() {
        return ResultUtils.success(imputationService.count());
    }

    @GetMapping("/download1")
    public void download(@RequestParam("id") Long id, HttpServletResponse response) throws IOException {
        Imputation imputation = imputationService.getById(id);
        String out = imputation.getOutput() + ".vcf.gz";
        // 假设这是从数据库查询到的文件路径
        String[] filePaths = {
                out
        };

        File zipFile = ZipUtil.zip(FileUtil.file("example.zip"), true, FileUtil.file(out));
        byte[] bytes = FileUtil.readBytes(zipFile);

        // 设置响应头
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"" +out + ".zip\"");

//        response.setHeader("Transfer-Encoding", "chunked");
//        response.setHeader("Content-Length", String.valueOf(zipFile.length()));


        // 写入响应
        response.getOutputStream().write(bytes);
        response.getOutputStream().flush();
    }

    @GetMapping("/download")
    public ResponseEntity<org.springframework.core.io.Resource> downloadFile(@RequestParam("id") Long id, HttpServletResponse response) throws MalformedURLException {
        Imputation imputation = imputationService.getById(id);
        String out = imputation.getOutput() + ".vcf.gz";

        File zipFile = ZipUtil.zip(FileUtil.file(imputation.getOutput().substring(imputation.getOutput().lastIndexOf(File.separator) + 1) + ".zip"), true, FileUtil.file(out));
        Path filePath = zipFile.toPath();
        org.springframework.core.io.Resource resource = new UrlResource(filePath.toUri());


        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
