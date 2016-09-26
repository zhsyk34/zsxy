package com.cat.zsy.dao.base;

import com.cat.kit.DaoKit;

/**
 * 仅供直接调用,嵌套调用会产生statement错误
 */
//@Repository
public class TemplateSession extends CustomSession {

	/**
	 * 1.未重写namespace()方法时,调用父类继承而来的方法session.*时,将直接执行父类方法,此时的this为子类,搜索不到
	 * 2.简单的super.namespace()会导致。。。
	 * 3.Override 采用findAsc因为调用方法依然为父类...
	 * 4.父类abstract namespace()方法...
	 * 5.findDesc()在AOP时被代理方法阻断出错
	 */
	/*protected String namespace1() {
		DaoKit.findDesc(this.getClass());
		return "com.cat.jsh.dao.RoleDao.save";
	}*/

	private String scanPackageName;

	public void setScanPackageName(String scanPackageName) {
		this.scanPackageName = scanPackageName;
	}

	@Override
	protected String namespace() {
		String name = DaoKit.statement(scanPackageName);
		System.err.println("set default namespace : " + name);
		return name;
	}
}