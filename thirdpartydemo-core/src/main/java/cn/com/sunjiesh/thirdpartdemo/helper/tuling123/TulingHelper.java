/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.com.sunjiesh.thirdpartdemo.helper.tuling123;

import com.alibaba.fastjson.JSONObject;

import cn.com.sunjiesh.thirdpartdemo.common.TulingProperties;
import cn.com.sunjiesh.utils.thirdparty.base.HttpService;
import cn.com.sunjiesh.xcutils.common.base.ServiceException;

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
    public JSONObject callTuling(String text) throws ServiceException{
        StringBuilder tulingUrlSb=new StringBuilder();
        tulingUrlSb.append(TulingProperties.TULING_URL);
        tulingUrlSb.append("?key=");
        tulingUrlSb.append(TulingProperties.getProperty(TulingProperties.TULING123_KEY));
        tulingUrlSb.append("&info=");
        tulingUrlSb.append(text);
        String tulingUrl=tulingUrlSb.toString();
        JSONObject responseJson = new HttpService().getJSONObjectResponseFromHttpGetMethod(tulingUrl);
        return responseJson;
    }
    
}
