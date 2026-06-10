package edu.phystech.hw5.service;

import edu.phystech.hw5.annotation.Cacheable;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author kzlv4natoly
 */
public class CacheableInvocationHandler implements InvocationHandler {
    private final Object target;
    private final Map<CacheKey, Object> cache = new HashMap<>();

    public CacheableInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] arguments) throws Throwable {
        if (method.isAnnotationPresent(Cacheable.class)) {
            CacheKey key = new CacheKey(method, arguments[0]);
            if (cache.containsKey(key)) {
                return cache.get(key);
            }
            Object result = invokeMethod(method, arguments);
            cache.put(key, result);
            return result;
        }
        return invokeMethod(method, arguments);
    }

    private Object invokeMethod(Method method, Object[] arguments) throws Throwable {
        try {
            return method.invoke(target, arguments);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

    private static final class CacheKey {
        private final Method method;
        private final Object argument;

        private CacheKey(Method method, Object argument) {
            this.method = method;
            this.argument = argument;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            CacheKey cacheKey = (CacheKey) o;
            return Objects.equals(method, cacheKey.method) && Objects.equals(argument, cacheKey.argument);
        }

        @Override
        public int hashCode() {
            return Objects.hash(method, argument);
        }
    }
}
