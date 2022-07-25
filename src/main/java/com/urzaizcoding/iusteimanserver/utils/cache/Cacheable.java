package com.urzaizcoding.iusteimanserver.utils.cache;



import java.util.Optional;

public interface Cacheable<K, V> {
	public Optional<V> get(final K key);
	public void put(final K key, V value);
	public void remove(final K key);
	public void clean();
	public void clear();
}
