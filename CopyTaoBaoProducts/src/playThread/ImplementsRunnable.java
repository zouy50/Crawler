package playThread;

import java.util.List;

public class ImplementsRunnable implements Runnable{
	private String name;
	private List<String> list;
	private boolean flag = true;
	public ImplementsRunnable(String name) {
		this.name = name;
	}
	@Override
	public void run() {
			print(list);
//		for(int i=0;i<20;i++) {
//			System.out.println("Implements>>>>"+i);
//		}
	}
	public void print(List<String> list) {
		TestThread.itList(list,this.name);
//		System.out.println(this.name+">>>>>>>>"+str);
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
	public void setList(List<String> list) {
		this.list = list;
	}
	

}
