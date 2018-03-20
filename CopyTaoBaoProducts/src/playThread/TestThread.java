package playThread;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TestThread {

	public static void main(String[] args) {
		//创建一个资源
		TestData d = new TestData();
		//创建两个线程对象
//		ExtendsThread e = new ExtendsThread();
		ImplementsRunnable i = new ImplementsRunnable("i");
		ImplementsRunnable i2 = new ImplementsRunnable("i2");
		//创建thread代理
		Thread t = new Thread(i);
		Thread t2 = new Thread(i2);
		List<String> list = (ArrayList<String>) d.getList();
		List<String> list2 = splitList(list);
		System.out.println(list.size());
		System.out.println(list2.size());
		i.setList(list);
		i2.setList(list2);
		t.start();
		t2.start();
		//将共享的资源锁住
//		synchronized (list) {
//			t.start();
//			Iterator<String> it = list.iterator();				
//		}
	}
	public static List<String> splitList(List<String> list) {
		List<String> newList = new ArrayList<String>();
		int length = list.size();
		for(int n = 0;n<list.size();n++) {
			if(n<(length/2)) {
				newList.add(list.get(n));
				list.remove(list.get(n));
			}
		}
		return newList;
	}
	public static void itList(List<String> list,String name) {
		Iterator<String> it = list.iterator();
		while(it.hasNext()) {
			String str = it.next();
			System.out.println(name+">>>>>>"+str);
		}
	}

}
