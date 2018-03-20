package com.yun.XiaoJieJie;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PaXiaoJieJie implements Runnable{
	String destPath = "/Users/Alex/Desktop/src/XJJ";
	private List<MyURL> list = new ArrayList<MyURL>();
	
	public PaXiaoJieJie(List<MyURL> list) {
		this.list=list;
	}
	public PaXiaoJieJie() {
	}
	@Override
	public void run() {
		this.outputPic(list);
	}

	/**
	 * 在一个字符串列表中的每一行都提取出URL的属性，,并且添加到urls列表中中
	 * @param list
	 * @return List<URL>
	 */
	public List<MyURL> createURLs(List<String> list) {
		List<MyURL> urls = new ArrayList<MyURL>();
		String rImg = "http.+?(?=\" data-thum)";
		String rWidth = "(?<=data-width=\")\\d{3,4}";
		String rHeight = "(?<=data-height=\")\\d{3,4}";
		String rName = "(?<=\\/u=)\\d{6,11},\\d{6,11}";
		String rExt = "(?<=data-ext=\")\\w{3,6}(?=\")";
		for (String str : list) {
			MyURL u = new MyURL();
			if(getUrlMatchRegex(str, rWidth)!=null) {
				u.setWidth(Integer.parseInt(getUrlMatchRegex(str, rWidth)));
			}
			if(getUrlMatchRegex(str, rHeight)!=null) {
				u.setHeight(Integer.parseInt(getUrlMatchRegex(str, rHeight)));
			}
			u.setUrl(getUrlMatchRegex(str, rImg));
			u.setName(getUrlMatchRegex(str, rName));
			u.setExt(getUrlMatchRegex(str, rExt));
			urls.add(u);
		}
		return urls;
	}
	public  void createDirs(String...str) {
		for (int i = 0; i < str.length; i++) {
			File temp = new File(destPath+"/"+str[i]);
			temp.mkdirs();
		}
	}
	/**
	 * 从某个字符串中返回一个符合这个表达式的文本，如果找不到就返回空
	 * 
	 * @param content
	 * @param regex
	 * @return
	 */
	public String getUrlMatchRegex(String content, String regex) {
		String str = null;
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(content);
		if (m.find()) {
			str = m.group();
		}
		return str;
	}
	/**
	 * 按MyURL对象列表将每个指定图片写入到文件中
	 * @param list
	 */
	public void outputPic(List<MyURL> list) {
		// 判断是否是头部的图片
		MyURL myurl = null;
		int count = 0;
		Iterator<MyURL> it = list.iterator();
		while (it.hasNext()) {
			myurl = it.next();
			String newDestPath = null;
			// 判断是否是纵向的图片
			if(!myurl.isClear()) {
				continue;
			}
			if (1 == myurl.isVertical()) {
				newDestPath = destPath + "/vertical/" + myurl.getName() + "." + myurl.getExt();
			} else if (0 == myurl.isVertical()) {
				newDestPath = destPath + "/horizontal/" + myurl.getName() + "." + myurl.getExt();
			} else {
				newDestPath = destPath + "/unknown/" + myurl.getName() + "." + myurl.getExt();
			}
			File dest = new File(newDestPath);
			//如果已经有这个图片就不再写入
			if(dest.exists()) {
				continue;
			}
			// 开始写入图片
			BufferedInputStream bis;
			try {
				bis = new BufferedInputStream(new URL(myurl.getUrl()).openStream());
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(dest));
				byte[] flush = new byte[1024];
				int len = 0;
				while (-1 != (len = bis.read(flush))) {
					bos.write(flush, 0, len);
				}
				System.out.println("已经将****"+myurl.getName()+"."+myurl.getExt()+"写入。");
				bos.flush();
				bos.close();
				count++;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("写入完毕，此次共写入"+count+"个图片");
	}

}
