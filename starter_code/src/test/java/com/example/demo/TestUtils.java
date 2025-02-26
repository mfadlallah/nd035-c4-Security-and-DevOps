package com.example.demo;

import java.lang.reflect.Field;

public class TestUtils {

    public static void injectMocks(Object target, String field, Object toInject) {

        boolean wasPrivate = false;

        try {
            Field f = target.getClass().getDeclaredField(field);
            if(!f.isAccessible()){
                f.setAccessible(true);
                wasPrivate = true;
            }
            f.set(target, toInject);

            if(wasPrivate){
                f.setAccessible(false);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}

