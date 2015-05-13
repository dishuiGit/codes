package cn.itcast.erp.test.impl;

import cn.itcast.erp.test.Target_inter_base;

public class Target_impl2 implements Target_inter_base{

	public void base() {
		System.out.println(this.getClass().getSimpleName()+"==base===");
	}

}
