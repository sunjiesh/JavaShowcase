define({ "api": [
  {
    "type": "post",
    "url": "/wechat/receive.do",
    "title": "Receive",
    "name": "Receive",
    "group": "wechat",
    "description": "<p>接收消息</p>",
    "version": "1.0.0",
    "examples": [
      {
        "title": "Example usage:",
        "content": "curl -i -X POST \"http://localhost:18081/wechat/receive.do?signature=04c790ff9b319a4d3a7889d23002fcbdbeff4c0f&timestamp=1469113396&nonce=822252680&openid=oiY-ExO09BRAH3kP80a_l438aBwQ\" --data '<xml><ToUserName><![CDATA[gh_554f61b4d4c8]]></ToUserName><FromUserName><![CDATA[oiY-ExO09BRAH3kP80a_l438aBwQ]]></FromUserName><CreateTime>1469113396</CreateTime><MsgType><![CDATA[event]]></MsgType><Event><![CDATA[CLICK]]></Event><EventKey><![CDATA[GetTemplateMessage]]></EventKey></xml>' --header \"Content-Type:text/xml\"",
        "type": "curl"
      }
    ],
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "signature",
            "description": "<p>微信加密签名</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "timestamp",
            "description": "<p>时间戳</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "nonce",
            "description": "<p>随机数</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "openid",
            "description": "<p>随机字符串</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "requestBody",
            "description": "<p>XML文档</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example: ",
          "content": "&lt;xml&gt;&lt;ToUserName&gt;&lt;![CDATA[gh_554f61b4d4c8]]&gt;&lt;/ToUserName&gt;&lt;FromUserName&gt;&lt;![CDATA[oiY-ExO09BRAH3kP80a_l438aBwQ]]&gt;&lt;/FromUserName&gt;&lt;CreateTime&gt;1469113396&lt;/CreateTime&gt;&lt;MsgType&gt;&lt;![CDATA[event]]&gt;&lt;/MsgType&gt;&lt;Event&gt;&lt;![CDATA[CLICK]]&gt;&lt;/Event&gt;&lt;EventKey&gt;&lt;![CDATA[GetTemplateMessage]]&gt;&lt;/EventKey&gt;&lt;/xml&gt;",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "xml",
            "description": "<p>xml文档</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "正确返回:",
          "content": "HTTP/1.1 200 OK\nContent-Type: text/plain;charset=UTF-8\n&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt;\n&lt;xml&gt;&lt;ToUserName&gt;oiY-ExO09BRAH3kP80a_l438aBwQ&lt;/ToUserName&gt;&lt;FromUserName&gt;gh_554f61b4d4c8&lt;/FromUserName&gt;&lt;CreateTime&gt;1470407769120&lt;/CreateTime&gt;&lt;MsgType&gt;text&lt;/MsgType&gt;&lt;Content&gt;模板消息已发送&lt;/Content&gt;&lt;/xml&gt;",
          "type": "xml"
        }
      ]
    },
    "filename": "thirdservice-parent/thirdservice-wechat/src/main/java/cn/com/sunjiesh/thirdservice/wechat/controller/WechatController.java",
    "groupTitle": "wechat"
  },
  {
    "type": "get",
    "url": "/wechat/receive.do",
    "title": "Valid",
    "name": "Valid",
    "group": "wechat",
    "description": "<p>验证</p>",
    "version": "1.0.0",
    "examples": [
      {
        "title": "Example usage:",
        "content": "curl -i \"http://localhost:18081/wechat/receive.do?timestamp=1469112344&nonce=2096446109&echostr=6272519237797280058&signature=57277cb1d4bc60698cfdf079982c6e99d52f1836\"",
        "type": "curl"
      }
    ],
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "timestamp",
            "description": "<p>时间戳</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "nonce",
            "description": "<p>随机数</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "echostr",
            "description": "<p>随机字符串</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "signature",
            "description": "<p>微信加密签名</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "echostr",
            "description": "<p>随机字符串</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "正确返回:",
          "content": "HTTP/1.1 200 OK\nContent-Type: text/plain;charset=UTF-8\n6272519237797280058",
          "type": "text"
        }
      ]
    },
    "filename": "thirdservice-parent/thirdservice-wechat/src/main/java/cn/com/sunjiesh/thirdservice/wechat/controller/WechatController.java",
    "groupTitle": "wechat"
  }
] });
