package playThread;

import java.util.ArrayList;
import java.util.List;

public class TestData {
	private List<String> list = new ArrayList<String>();
	private boolean flag;
	//构造器初始化一个列表用来让其他线程调取数据
	public TestData(){
		for (int i = 0; i < 50; i++) {
			String str = i+"";
			this.list.add(str);
		}
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public List<String> getList() {
		return list;
	}
}
