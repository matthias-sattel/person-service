package org.goafabric.personservice.crossfunctional;

public final class TenantRequestContext {
    private TenantRequestContext() {
    }

    private static final ThreadLocal<String> tenantIdThreadLocal = new ThreadLocal<>();

    public static String getTenantId() {
        String tenantId = tenantIdThreadLocal.get();
        if (tenantId == null) {
            tenantId = "0"; //TODO
        }
        return tenantId;
    }

    static void setTenantId(String tenantId) {
        tenantIdThreadLocal.set(tenantId);
    }

    static void remove() {
        tenantIdThreadLocal.remove();
    }
}
