package io.github.jcchen07944031.Entities;

public class Statistic {

	private String object_id;
	private String title;
	private String image_url;
	private String count;

	public Statistic() {
	
	}
	
	public void setObjectId(String object_id) {
		this.object_id = object_id;
	}
	
	public String getObjectId() {
		return object_id;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setImgUrl(String image_url) {
		this.image_url = image_url;
	}
	
	public String getImgUrl() {
		return image_url;
	}
	
	public void setCount(String count) {
		this.count = count;
	}
	
	public String getCount() {
		return count;
	}
}