/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.com.sunjiesh.thirdpartdemo.helper.tuling123;

import cn.com.sunjiesh.thirdpartdemo.common.TulingProperties;
import cn.com.sunjiesh.thirdpartdemo.response.tuling.TulingResponse;
import cn.com.sunjiesh.utils.thirdparty.base.HttpService;
import cn.com.sunjiesh.xcutils.common.base.ServiceException;
import com.alibaba.fastjson.JSONObject;

/**
 *
 * Tuling123 Helper
 * @author tom
 */
public class TulingHelper {
    
    /**
     * 調用Tuling服務
     * @param text
     * @return
     * @throws ServiceException 
     */
    public TulingResponse callTuling(String text) throws ServiceException{
        StringBuilder tulingUrlSb=new StringBuilder();
        tulingUrlSb.append(TulingProperties.TULING_URL);
        tulingUrlSb.append("?key=");
        tulingUrlSb.append(TulingProperties.getProperty(TulingProperties.TULING123_KEY));
        tulingUrlSb.append("&info=");
        tulingUrlSb.append(text);
        String tulingUrl=tulingUrlSb.toString();
        JSONObject responseJson = new HttpService().getJSONObjectResponseFromHttpGetMethod(tulingUrl);
        TulingResponse response=JSONObject.toJavaObject(responseJson, TulingResponse.class);
        return response;
    }
    
}
