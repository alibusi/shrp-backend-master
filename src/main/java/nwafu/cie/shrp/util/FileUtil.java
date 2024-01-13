package nwafu.cie.shrp.util;

import cn.hutool.core.io.FileTypeUtil;
import nwafu.cie.shrp.common.ErrorCode;
import nwafu.cie.shrp.exception.BusinessException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtil {
    private static final Pattern PATTERN = Pattern.compile("chr(1[0-9]|2[0-6]|[1-9])");

    public static boolean containChr(MultipartFile multipartFile) {
        if (multipartFile == null || multipartFile.getOriginalFilename() == null) {
            return false;
        }
        String filename = multipartFile.getOriginalFilename();
        Matcher matcher = PATTERN.matcher(filename);
        return matcher.find();
    }

    public static boolean isGzipFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile == null || multipartFile.isEmpty()) {
            return false;
        }

        InputStream inputStream = null;
        try {
            inputStream = multipartFile.getInputStream();
            String fileType = FileTypeUtil.getType(inputStream);
            return "gz".equalsIgnoreCase(fileType);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }
}
