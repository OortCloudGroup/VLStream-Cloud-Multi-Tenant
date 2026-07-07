package org.springblade;

import org.springblade.common.constant.CommonConstant;
import org.springblade.core.launch.BladeApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 启动器
 *
 * @author Chill
 */
@EnableScheduling
@EnableRedisHttpSession
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		BladeApplication.run(CommonConstant.APPLICATION_NAME, Application.class, args);
		System.out.println( "(♥◠‿◠)ﾉﾞ  系统启动成功   ლ(´ڡ`ლ)ﾞ " );
		System.out.println( "swagger地址： http://localhost:8080/doc.html" );
	}

}

