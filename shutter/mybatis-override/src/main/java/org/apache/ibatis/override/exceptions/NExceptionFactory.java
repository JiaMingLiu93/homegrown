package org.apache.ibatis.override.exceptions;

/**
 * @author youyu
 * @date 2019/4/12 5:18 PM
 */
public class NExceptionFactory {
    private NExceptionFactory(){}
    public static RuntimeException wrapException(String message, Exception e){
        return null;
    }
}
