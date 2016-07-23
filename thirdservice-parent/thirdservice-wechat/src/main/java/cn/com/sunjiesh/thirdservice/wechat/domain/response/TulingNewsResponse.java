package cn.com.sunjiesh.thirdservice.wechat.domain.response;

/**
 * 图灵新闻对象
 * 
 * @author tom
 *
 */
public class TulingNewsResponse {

	private String article;

	private String icon;

	private String source;

	private String detailurl;

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getArticle() {
		return article;
	}

	public void setArticle(String article) {
		this.article = article;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDetailurl() {
		return detailurl;
	}

	public void setDetailurl(String detailurl) {
		this.detailurl = detailurl;
	}

}
