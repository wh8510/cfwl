package org.example.cfwl.context;

public class BaseContext {
    private static ThreadLocal<String> ThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<Long> ThreadLocal1 = new ThreadLocal<>();
    public static void setCurrentId(Long id){
        ThreadLocal1.set(id);
    }
    public static Long getCurrentId(){
        return ThreadLocal1.get();
    }

    public static void setCurrentName(String name) {
        ThreadLocal.set(name);
    }

    public static String getCurrentName() {
        return ThreadLocal.get();
    }

    public static void clearCurrentName() {
        ThreadLocal.remove();
    }
}

