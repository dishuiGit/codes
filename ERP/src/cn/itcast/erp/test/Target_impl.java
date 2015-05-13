package cn.itcast.erp.test;

public class Target_impl implements Target_inter{

	public void save() {
		System.out.println("save");
	}

	public void find() {
		System.out.println("find");
	}

	public void update() {
		System.out.println("update");
	}

	public void base() {
		System.out.println("base");
	}

}
