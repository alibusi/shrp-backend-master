package nwafu.cie.shrp.util;

import org.apache.commons.lang3.RandomStringUtils;

public class SessionIdGenerator {
    public static String generateSessionId() {
        return RandomStringUtils.randomAlphanumeric(32);
    }
}
