package com.cat.kit;

public class DaoKit {

    public static final Match DEFAULT = String::equals;
    public static final Match SCAN = (ref, str) -> str.startsWith(ref);

    /**
     * @param elements 栈信息
     * @param name     需要匹配的信息
     * @param match    匹配规则
     * @param desc     是否逆序搜寻
     * @return 索引位置
     */
    private static int findIndex(StackTraceElement[] elements, String name, Match match, boolean desc) {
        /*for (int i = 0; i < elements.length; i++) {
			System.out.println(i + ":" + elements[i].getClassName() + "." + elements[i].getMethodName());
		}*/
        String className;
        if (desc) {
            for (int i = elements.length - 1; i > -1; i--) {
                className = elements[i].getClassName();
                if (match.test(name, className)) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < elements.length; i++) {
                className = elements[i].getClassName();
                //System.out.println(i + ":" + className + "." + elements[i].getMethodName());
                if (match.test(name, className)) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * @param className 类名
     * @param index     接口下标
     * @return 接口名称
     */
    private static String getInterface(String className, int index) {
        if (className == null || className.isEmpty()) {
            return null;
        }
        try {
            return Class.forName(className).getInterfaces()[index].getName();
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    private static String namespace(String className, String methodName, boolean isFace) {
        if (className == null || className.isEmpty() || methodName == null || methodName.isEmpty()) {
            return null;
        }
        if (isFace) {
            className = getInterface(className, 0);
        }
        return className == null ? null : className + "." + methodName;
    }

    /**
     * @param name 扫描包
     * @return 默认的命名空间
     */
    public static String statement(String name) {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        int i = findIndex(elements, name, SCAN, false);
        return i == -1 ? null : namespace(elements[i].getClassName(), elements[i].getMethodName(), true);
    }

    private static int findIndex(StackTraceElement[] elements, Class clazz, boolean desc) {
        return findIndex(elements, clazz.getName(), DEFAULT, desc);
    }

	/*TODO 以下方法基本废弃*/

    /**
     * 倒序搜寻,查询到第一个立即返回,查找次数一般较多 2016-09-23
     */
    public static String findDesc(Class clazz) {

        //TODO
		/*Match match = new Match() {
			@Override
			public boolean match(List<String> list) {
				return false;
			}
		};*/

        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        int index = findIndex(elements, clazz, true);
        String className = elements[++index].getClassName(), methodName = elements[index].getMethodName();
        return namespace(className, methodName, true);
    }

    /**
     * 顺序搜寻,直至不是类内部方法调用,速度较快 2016-09-22
     */
    public static String findAsc(Class clazz) {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        int index = -1;
        for (int i = 0; i < elements.length; i++) {
            String name = elements[i].getClassName();
            System.out.println(i + ":" + name + "." + elements[i].getMethodName());
            if (name.equals(clazz.getName())) {
                if (i + 1 < elements.length) {
                    name = elements[i + 1].getClassName();
                    System.out.println("search next for check.it's : " + name + "." + elements[i + 1].getMethodName());
                    if (!name.equals(clazz.getName())) {
                        index = i;
                        break;
                    }
                } else {
                    index = i;
                    break;
                }
            }
        }

        if (index == -1 || index == elements.length - 1) {
            return null;
        }

        index++;//调用者
        String className = elements[index].getClassName(), methodName = elements[index].getMethodName();
        return namespace(className, methodName, true);
    }

    /**
     * @param clazz         基准类,查找起点
     * @param classForward  类调用栈顶层次
     * @param classOrder    顺序查询:为最近调用对象 A->X1->X2->C ==> X2,倒序查询:为最远调用对象 A->X1->X2->C ==> X1
     * @param methodForward 方法调用栈顶层次
     * @param methodOrder   同classOrder
     * @param face          是否用接口替代
     * @return 调用路径:包名+方法名
     */
    //TODO classOrder == methodOrder 时可优化
    @Deprecated
    public static String findSpace(Class clazz, int classForward, boolean classOrder, int methodForward, boolean methodOrder, boolean face) {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();

        String className, methodName;

        int classIndex = findIndex(elements, clazz, classOrder);
        if (classIndex == -1) {
            return null;
        }

        int methodIndex = findIndex(elements, clazz, methodOrder);
        if (methodIndex == -1) {
            return null;
        }

        //TODO 考虑方向+/-
        int classLevel = classIndex + classForward, methodLevel = methodIndex + methodForward;
        if (classLevel > -1 && classLevel < elements.length && methodLevel > -1 && methodLevel < elements.length) {
            className = elements[classLevel].getClassName();
            methodName = elements[methodLevel].getMethodName();
            return namespace(className, methodName, face);
        }
        return null;
    }

    private interface Match {
        boolean test(String ref, String str);
    }

}
