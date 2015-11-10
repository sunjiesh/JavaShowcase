/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.com.sunjiesh.thirdpartdemo.response.tuling;

/**
 * tuling接口返回
 * @author tom
 */
public class TulingResponse {
    
    /**
     * 标识码
     */
    private Integer code;
    
    /**
     * 结果
     */
    private String text;
    
    /**
     * 链接地址
     */
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    
}
