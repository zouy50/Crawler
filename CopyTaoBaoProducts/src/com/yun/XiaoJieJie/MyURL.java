package com.yun.XiaoJieJie;

public class MyURL {
	private String name;
	private String ext;
	private String url;
	private int width = 1;
	private int height = 5;
	public MyURL() {
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int isVertical() {
		int flag = -1;
		int VS = (int)(((double)this.getWidth()/this.getHeight())*100);
		if(VS>=45&&VS<=70) {
			flag = 1;
		}
		else if(VS>=145&&VS<=230){
			flag = 0;
		}
		return flag;
	}
	public boolean isClear() {
		boolean flag = false;
		if(this.height>900||this.width>900) {
			flag = true;
		}
		return flag;
	}
}
