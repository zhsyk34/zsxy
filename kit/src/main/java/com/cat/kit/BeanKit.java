package com.cat.kit;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class BeanKit {

    //缓存类中的所有字段
    private static final Map<Class, List<String>> CLASS_FIELDS_CACHE = new ConcurrentHashMap<>();

    //TODO 无序
    private static boolean loadFieldsByIntrospector(Class clazz) {
        BeanInfo beanInfo;
        try {
            beanInfo = Introspector.getBeanInfo(clazz, Object.class);
        } catch (IntrospectionException e) {
            throw new RuntimeException(e);
        }
        PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();

        List<String> list = new ArrayList<>(descriptors.length);
        for (PropertyDescriptor descriptor : descriptors) {
            list.add(descriptor.getName());
        }
        CLASS_FIELDS_CACHE.put(clazz, list);
        return true;
    }

    private static boolean loadFieldsByReflect(Class clazz) {
        //TODO clazz.getSuperclass();
        Field[] fields = clazz.getDeclaredFields();
        List<String> list = new ArrayList<>(fields.length);
        for (Field field : fields) {
            list.add(field.getName());
        }
        CLASS_FIELDS_CACHE.put(clazz, list);
        return true;
    }

    public static List<String> getFieldList(Class clazz) {
        List<String> list = CLASS_FIELDS_CACHE.get(clazz);
        if (list == null) {
            loadFieldsByReflect(clazz);
        }
        return Collections.unmodifiableList(CLASS_FIELDS_CACHE.get(clazz));
    }

    public static String getFields(Class<?> clazz) {
        String str = Arrays.toString(getFieldList(clazz).toArray());
        return str.substring(1, str.length() - 1);
    }

    public static Map<String, Object> beanToMap(Object object) {
        Map<String, Object> map = new HashMap<>();
        BeanInfo beanInfo;
        try {
            beanInfo = Introspector.getBeanInfo(object.getClass(), Object.class);
        } catch (IntrospectionException e) {
            throw new RuntimeException(e);
        }
        PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();

        for (PropertyDescriptor descriptor : descriptors) {
            String property = descriptor.getName();
            Method getter = descriptor.getReadMethod();
            Object value = null;
            try {
                if (getter.getParameterCount() == 0) {
                    value = getter.invoke(object);
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
            map.put(property, value);
        }
        return map;
    }

    public static Map<String, Object> beanToMapByReflect(Object object) {
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();

        Map<String, Object> map = new HashMap<>(fields.length);
        for (Field field : fields) {
            String property = field.getName();
            Class<?> type = field.getType();

            Object value;
            try {
                String getter = getter(property, type == Boolean.class || type == boolean.class);
                Method method = clazz.getMethod(getter);
                value = method.invoke(object);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
            map.put(property, value);
        }
        return map;
    }

    public static Object getFieldValue(Object object, String field) {
        try {
            PropertyDescriptor descriptor = new PropertyDescriptor(field, object.getClass());
            return descriptor.getReadMethod().invoke(object);
        } catch (Exception e) {
            throw new RuntimeException("can't get the value in " + field + " by " + object);
        }
    }

    public static boolean setFieldValue(Object object, String field, Object value) {
        try {
            PropertyDescriptor descriptor = new PropertyDescriptor(field, object.getClass());
            descriptor.getWriteMethod().invoke(object, value);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("can't set the value (" + value + ") in " + field + " for " + object);
        }
    }

    private static String getter(String field, boolean boole) {
        String str = field.substring(0, 1).toUpperCase() + field.substring(1);
        return (boole ? "is" : "get") + str;
    }

    private static String setter(String field) {
        String str = field.substring(0, 1).toUpperCase() + field.substring(1);
        return "set" + str;
    }

    public static Class getClassGenericType(Class clazz, int index) {
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            return Object.class;
        }
        return params[index] instanceof Class ? (Class) params[index] : Object.class;
    }

    public static Class getClassGenericType(Class clazz) {
        return getClassGenericType(clazz, 0);
    }

    /**
     * @param value 主键值
     * @return 是否已经持久化
     */
    public static boolean hasPersistent(Object value) {
        if (value == null) {
            return false;
        }

        Class clazz = value.getClass();
        if (clazz == String.class) {
            return ((String) value).trim().length() > 0;
        }
        if (clazz == int.class || clazz == Integer.class) {
            return (Integer) value > 0;
        }
        return (clazz == long.class || clazz == Long.class) && (Long) value > 0;
    }
}
