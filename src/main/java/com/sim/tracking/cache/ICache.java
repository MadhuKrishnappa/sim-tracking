package com.sim.tracking.cache;

public interface ICache {

    void put(String key, Object object);

    Object get(String key);

    void delete(String key);

    public void put(String key, Object object, int daysLessThan30) throws Exception;

}
