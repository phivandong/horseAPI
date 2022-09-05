package com.pvdong.lesson3.entity;

import java.util.HashMap;
import java.util.Map;

public class SingletonCollector {
    private Map<String, Integer> singleton = new HashMap<String, Integer>();

    private static SingletonCollector instance = null;

    public SingletonCollector() {}

    public static SingletonCollector getInstance() {
        if (instance == null) {
            instance = new SingletonCollector();
        }
        return instance;
    }

    public void put(String str, Integer id) {
        singleton.put(str, id);
    }
}
