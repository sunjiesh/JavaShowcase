package cn.com.sunjiesh.thirdservice.wechat.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author tom
 */
public class TulingProperties {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(TulingProperties.class);
    
    private static Properties properties;
    
    public static final String TULING_URL="http://www.tuling123.com/openapi/api";
    
    public static final String TULING123_KEY="tuling123.key";
    
    static {
		initProperties();
	}

	/**
	 * 初始化属性
	 */
	public static void initProperties() {
		if (properties == null) {
			try {
				properties = new Properties();
				InputStream is = TulingProperties.class.getResourceAsStream("/tuling123.properties");
				properties.load(is);
			} catch (IOException ex) {
				LOGGER.error("Init Properties Error",ex);
			}
		}

	}

	/**
	 * 获取属性值
	 *
	 * @param propertyKey
	 *            属性Key值
	 * @return 返回属性值
	 */
	public static String getProperty(String propertyKey) {
		return properties.getProperty(propertyKey);
	}
}
