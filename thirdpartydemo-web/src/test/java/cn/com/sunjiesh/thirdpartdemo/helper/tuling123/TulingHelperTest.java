/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.com.sunjiesh.thirdpartdemo.helper.tuling123;

import cn.com.sunjiesh.thirdpartdemo.response.tuling.TulingResponse;
import cn.com.sunjiesh.xcutils.common.base.ServiceException;
import org.apache.http.util.Asserts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

/**
 *
 * @author tom
 */
@Test
public class TulingHelperTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(TulingHelperTest.class);
    
    @Test
    public void testCallTuling() throws ServiceException{
        TulingResponse response=new TulingHelper().callTuling("你好");
        LOGGER.debug("code="+response.getCode());
        Asserts.check(response.getCode()==100000, "返回不是文字");
    }
}
