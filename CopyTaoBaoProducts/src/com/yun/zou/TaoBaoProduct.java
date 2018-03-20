package com.yun.zou;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaoBaoProduct {
	public static void main(String[] args) {
		TaoBaoProduct t = new TaoBaoProduct();
		String srcPath = "/Users/Alex/Desktop/src/Taobao/txtFiles/5.txt";
		//保存详情图片并且保存下文件夹的名字
		String detailPath = t.saveHeadPic(t.getUrlMatchRegex(t.getContentFromTxt(srcPath), t.tmDetailRegex), t.destPath,false,null);
		//保存下相同文件夹名字的头图片
		t.saveHeadPic(t.getUrlMatchRegex(t.getContentFromTxt(srcPath), t.tmHeadRegex), t.destPath,true,detailPath);	
		//生成csv文件
		t.writerStringToCsv((t.creatCsv(t.getFields(t.getContentFromTxt(srcPath)), t.setDetailPic(t.detailPic,detailPath), t.setHeadPic(t.headPic))), detailPath);
	}
	/**
	 * 将指定的字符写入到指定的文件夹
	 * @param str
	 * @param destName
	 */
	public void writerStringToCsv(String str,String destName) {
		OutputStreamWriter osw;
		try {
			osw = new OutputStreamWriter(new FileOutputStream(new File(this.destPath+"/"+destName+".csv")),"utf-16");
			BufferedWriter bw = new BufferedWriter(osw);
			bw.write(str);
			bw.flush();
			bw.close();
			System.out.println("已经将csv文件写入！");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String creatCsv(String t,String d,String h) {
		StringBuilder csv = new StringBuilder();
		csv.append("version 1.00\n" + 
				"title,cid,seller_cids,stuff_status,location_state,location_city,item_type,price,auction_increment,num,valid_thru,freight_payer,post_fee,ems_fee,express_fee,has_invoice,has_warranty,approve_status,has_showcase,list_time,description,cateProps,postage_id,has_discount,modified,upload_fail_msg,picture_status,auction_point,picture,video,skuProps,inputPids,inputValues,outer_id,propAlias,auto_fill,num_id,local_cid,navigation_type,user_name,syncStatus,is_lighting_consigment,is_xinpin,foodparame,features,buyareatype,global_stock_type,global_stock_country,sub_stock_type,item_size,item_weight,sell_promise,custom_design_flag,wireless_desc,barcode,sku_barcode,newprepay,subtitle,cpv_memo,input_custom_cpv,qualification,add_qualification,o2o_bind_service,tmall_extend,product_combine,tmall_item_prop_combine,taoschema_extend\n" + 
				"宝贝名称,宝贝类目,店铺类目,新旧程度,省,城市,出售方式,宝贝价格,加价幅度,宝贝数量,有效期,运费承担,平邮,EMS,快递,发票,保修,放入仓库,橱窗推荐,开始时间,宝贝描述,宝贝属性,邮费模版ID,会员打折,修改时间,上传状态,图片状态,返点比例,新图片,视频,销售属性组合,用户输入ID串,用户输入名-值对,商家编码,销售属性别名,代充类型,数字ID,本地ID,宝贝分类,用户名称,宝贝状态,闪电发货,新品,食品专项,尺码库,采购地,库存类型,国家地区,库存计数,物流体积,物流重量,退换货承诺,定制工具,无线详情,商品条形码,sku 条形码,7天退货,宝贝卖点,属性值备注,自定义属性值,商品资质,增加商品资质,关联线下服务,tmall扩展字段,产品组合,tmall属性组合,taoschema扩展字段\n").append("\""+t+"\"").append(
				",162201,\"\",1,\"浙江\",\"杭州\",1,100,\"\",100,0,0,2.37266e+25,2.0112e-38,0,0,0,1,0,\"\",").append(d).append(",\"\",9554565510,0,\"2018-03-17 13:45:39 \",\"200\",\"2;2;2;2;2;2;\",0,").append(h).append(",\"\",\"\",\"\",\"\",\"\",\"\",0,0,-1,1,,2,168,166,\"product_date_end:;product_date_start:;stock_date_end:;stock_date_start:\",mysize_tp:-1;sizeGroupId:136553091;sizeGroupType:women_bottoms,0,-1,,2,bulk:0.000000,0.000,0,,\"<wapDesc><img>E:\\Program Files (x86)\\TaobaoAssistant\\Users\\思聪的店铺\\Images4wap\\13-1P315112T0-50.jpg</img><img>E:\\Program Files (x86)\\TaobaoAssistant\\Users\\思聪的店铺\\Images4wap\\13-1P315112T0.jpg</img></wapDesc>\",,,1,\"\",\"\",\"\",\"%7B%20%20%7D\",0,0,,,,\n" + 
						"es4wap\\13-1P315112T0.jpg</img></wapDesc>\",,,1,\"\",\"\",\"\",\"%7B%20%20%7D\",0,0,,,,\n" + 
						"");
		return csv.toString();
	}
	String tmHeadRegex = "(?<=<img src=\"\\/\\/)img.\\S+?[.]jpg(?=_60)";//头图片的正则
	String tmDetailRegex = "(?<=src=\"https:\\/\\/).\\S+?[.]jpg(?=\")";//详情图片的正则
	String destPath = "/Users/Alex/Desktop/src/Taobao/txtFiles";
	List<String> detailPic = new ArrayList<String>();
	List<String> headPic = new ArrayList<String>();
	
	/**
	 * 生成随机数字的文件夹名
	 * @return
	 */
	public String createFile() {
		String destName = "";
		for (int i = 0; i < 11; i++) {
			destName += (int)(Math.random()*9);
		}
		return destName;
	}
	/**
	 * 读取到指定主页上的源代码
	 * @param URLstr
	 * @return
	 */
	public String getQuanUrl(String URLstr) {
		StringBuilder QuanURL = new StringBuilder();
		URL url;
		try {
			url = new URL(URLstr);
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "gbk"));
				String temp = null;
				while(null!=(temp=br.readLine())) {
					QuanURL.append(temp);
				}
				br.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		return QuanURL.toString();
	}
	/**
	 * 将文件写成字符串：
	 * @param srcPath
	 * @return
	 */
	public String getContentFromTxt(String srcPath) {
		StringBuilder content = new StringBuilder();
		try {
			BufferedReader bir = new BufferedReader(new FileReader(srcPath));
			String temp = null;
			while(null!=(temp=bir.readLine())) {
				content.append(temp);
			}
			bir.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content.toString();
	}
	/**
	 * 返回一个从某个文本中找出符合某个正则表达式的字符串列表
	 * @param content
	 * @param regex
	 * @return
	 */
	public List<String> getUrlMatchRegex(String content,String regex){
		List<String> list = new ArrayList<>();
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(content);
		while(m.find()) {
			String temp = m.group();
			list.add(temp);
		}
		return list;
	}
	/**
	 * 将head图片存到一个指定的文件夹,并返回文件夹名字的字符串
	 * @param list
	 * @param destPath
	 */
	public String saveHeadPic(List<String> list,String destPath,boolean isHead,String destName) {
		URL url = null;
		int i = 1;
		String destTempName = createFile();
		//判断是否是头部的图片
		if(!isHead) {
			destPath = destPath+"/"+destTempName+"_detailPic";
		}
		else {
			destPath = destPath+"/"+destName;
		}
		
		//将目标路径与生成的文件夹连起来并且mkds
		File dest = new File(destPath);
		dest.mkdirs();
		//开始遍历每条图片的数据并且写入文件夹下
		for(String urlStr:list) {
			try {
				url = new URL("https://"+urlStr);
				List<String> tempName = getUrlMatchRegex(urlStr,"\\w+(?=_!!)");
				BufferedInputStream bis = new BufferedInputStream(url.openStream());
				String name = null;
				for(String tempname:tempName) {
					name = i +"_"+tempname;
				}
				BufferedOutputStream bos = null;
				if(!isHead) {
					bos = new BufferedOutputStream(new FileOutputStream(new File(destPath+"/"+name+".jpg")));
				}
				else {
					bos = new BufferedOutputStream(new FileOutputStream(new File(destPath+"/"+name+".tbi")));
				}
				byte[] flush = new byte[1024];
				int len = 0;
				while(-1!=(len=bis.read(flush))) {
					bos.write(flush, 0, len);
				}
				System.out.println("已经将"+name+".jpg写入文件");
				if(!isHead) {
					this.detailPic.add(name);
				}
				else {
					this.headPic.add(name);
				}
				bos.flush();
				bos.close();
				i++;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return destTempName;
	}
	//用于剪切字符串
	public String splitString(String str) {
		str.split("img[.].+?[.]jpg");
		return str;
	}
	//从文本中获取商品名
	public String getFields(String content) {
		List<String> titleList = getUrlMatchRegex(content, "(?<=name=\"keywords\" content=\").+?(?=\")");
		String title = null;
		for (String string : titleList) {
			title = string;
		}
		return title;
		
	}
	//生成detail文件的String
	public String setDetailPic(List<String> list,String destPath) {
		String str = "\"<P>";
		for (String s : list) {
			str = str+"<IMG align=middle src=\"\"FILE:///F:\\1234\\"+destPath+"_detailPic\\"+s+".jpg"+"\"\">";
		}
		str = str + "</P>\"";
		return str;
	}
	//生成head文件的String
	public String setHeadPic(List<String> list) {
		String str = "\"";
		int i=0;
		for (String s : list) {
			str = str+s+":1:"+i+":|;";
			i++;
		}
		str = str + "\"";
		return str;
	}
}
