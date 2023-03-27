package kr.co.popool.bblmember.infra.interceptor;


public class IdentityThreadLocal {

    private static final ThreadLocal<String> threadLocal;

    static {
        threadLocal = new ThreadLocal<>();
    }

    public static void set(String identity){
        threadLocal.set(identity);
    }

    public static void remove(){
        threadLocal.remove();
    }

    public static String get(){
        return threadLocal.get();
    }
}
