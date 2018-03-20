package playThread;

public class ExtendsThread extends Thread{
	private String str;//用来给run方法传参数
	private boolean flag = true;//信号灯
	public String getStr() {
		return str;
	}
	public void setStr(String str) {
		this.str = str;
	}
	public ExtendsThread() {
	}
	//重写ruan方法
	@Override
	public void run() {
		while(flag) {
			this.print(str);
		}
//		for(int i=0;i<28;i++) {
//			System.out.println("Extends>>>>"+i);
//		}
	}
	public void print(String str) {
		System.out.println("Extends打印了>>>"+str);
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
}
