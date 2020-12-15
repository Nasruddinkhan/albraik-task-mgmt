package com.albraik.task.cache;

import java.util.Map;

public interface ObjectCache<K, V> {

	void load();

	Map<K, V> getCacheData();

	V get(K key);

	void put(K key, V value);
	
	void remove(K key);

}
