package cn.zengcanxiang.mvp_practice_project_template.base.mvp;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

class ClassUtil {
    /**
     * 获取该类的泛型的实体对象
     *
     * @param o 指定泛型的类
     * @param i 第几个泛型
     */
    @SuppressWarnings("unchecked")
    static <T> T getT(Object o, int i) {
        try {
            Class<?> aClass = o.getClass();
            ParameterizedType g = (ParameterizedType) aClass.getGenericSuperclass();
            Type[] a = g.getActualTypeArguments();
            Class<T> tClass = (Class<T>) a[i];
            return tClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Class<?> forName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
