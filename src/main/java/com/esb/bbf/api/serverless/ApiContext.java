package com.esb.bbf.api.serverless;

/**
 * api相关信息的上下文
 */
public class ApiContext {
    private static InheritableThreadLocal<String> tokenPool = new InheritableThreadLocal<>();

    /**
     * UserInfo
     */
    private ApiContext() {
    }

    /**
     * apiId
     *
     * @return return
     */
    public static String apiId() {
        return tokenPool.get();
    }


    /**
     * set apiId
     *
     * @param apiId
     */
    public static void set(final String apiId) {
        tokenPool.set(apiId);
    }

}
