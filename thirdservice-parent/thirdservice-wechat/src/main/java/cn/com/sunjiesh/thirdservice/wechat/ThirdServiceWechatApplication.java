package cn.com.sunjiesh.thirdservice.wechat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ThirdServiceWechatApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThirdServiceWechatApplication.class, args);
	}

}
