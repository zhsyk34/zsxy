package com.cat.zsy.aop;

import org.aspectj.lang.ProceedingJoinPoint;

public class SessionFilter {

	//execution(<方法修饰符>? <方法返回值类型> <包名>.<类名>.<方法名>(<参数类型>) [throws <异常类型>]?)
	//‘*’代表0个或多个任意字符，包名中的..（两个点）代表当前包及其子包，参数列表中的..代表任意个参数。

	/**
	 * Object[] getArgs：返回目标方法的参数
	 * Signature getSignature：返回目标方法的签名
	 * Object getTarget：返回被织入增强处理的目标对象
	 * Object getThis：返回AOP框架为目标对象生成的代理对象
	 */
	public Object namespace(ProceedingJoinPoint point) {
		System.out.println("begin");
		try {
			Object[] args = point.getArgs();

			if (args.length > 0) {
				Object first = args[0];
				if (first instanceof String) {
					System.out.println(">>>");
				}
				String className = point.getTarget().getClass().getName();
				String methodName = point.getSignature().getName();
				System.out.println(className + "." + methodName);
			}
			//修改方法参数:传入args
			Object value = point.proceed();
			System.out.println("end");
			return value;
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}
}
