package com.yun.XiaoJieJie;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import com.yun.zou.TaoBaoProduct;

public class Start {

	public static void main(String[] args) {
		TaoBaoProduct t = new TaoBaoProduct();
		PaXiaoJieJie cr = new PaXiaoJieJie();

		String srcPath = "/Users/Alex/Desktop/src/XJJ/6 .txt";
		cr.createDirs("vertical","horizontal","unknown");
		String imgRegex = "(?<=data-objurl=\")http:\\/\\/.+?(?=data-hostname)";
		List<String> list = t.getUrlMatchRegex(t.getContentFromTxt(srcPath), imgRegex);//获得整体链接的地址
		List<MyURL> myurls = cr.createURLs(list);//将所有的链接提取成MyURL对象，并组成列表
		List<List<MyURL>> finnalList = ssplitList(myurls);
		Iterator<List<MyURL>> it = finnalList.iterator();
		while(it.hasNext()) {
			PaXiaoJieJie temp = new PaXiaoJieJie(it.next());
			Thread th = new Thread(temp);
			th.start();
		}
	}
	/**
	 * 将俩表中的前五十项取出成一个新数组
	 * @param list
	 * @return list
	 */
	public static List<MyURL> splitList(List<MyURL> list) {
		List<MyURL> newList = new ArrayList<MyURL>();
		if(list.size()>50) {
			for(int n = 0;n<50;n++) {
				newList.add(list.get(n));
			}
			list.removeAll(newList);
			return newList;		
		}
		return list;
		
	}
	public static List<List<MyURL>> ssplitList(List<MyURL> list) {
		List<List<MyURL>> finalList = new ArrayList<List<MyURL>>();
		while(list.size()>50) {
			finalList.add(splitList(list));			
		}
		return finalList;
	}

}
