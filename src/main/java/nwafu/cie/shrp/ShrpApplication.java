package nwafu.cie.shrp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan("nwafu/cie/shrp-backend-master/mapper")
public class ShrpApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ShrpApplication.class);
    }
    public static void main(String[] args) {
        Long time = System.currentTimeMillis();
        SpringApplication.run(ShrpApplication.class, args);
        System.out.println("===应用启动耗时： "+ (System.currentTimeMillis()-time) + "===");
    }
}
