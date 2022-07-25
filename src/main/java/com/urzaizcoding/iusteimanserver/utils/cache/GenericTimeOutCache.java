package com.urzaizcoding.iusteimanserver.utils.cache;



import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class GenericTimeOutCache<K,V> implements Cacheable<K, V> {
	public final long DEFAUT_TIME_OUT_CACHE = 60000L;
	protected ConcurrentHashMap<K, CacheValue<V>> m_cacheData;
	protected long m_timeOut;
	
	/**
	 * Constructors
	 */
	public GenericTimeOutCache() {
		m_timeOut = DEFAUT_TIME_OUT_CACHE;
		m_cacheData = new ConcurrentHashMap<>();
	}
	public GenericTimeOutCache(long timeOut) {
		m_timeOut = timeOut;
		m_cacheData = new ConcurrentHashMap<>();
	}
	
	@Override
	public Optional<V> get(K key) {
		clean();	//we clean the cache first
		return Optional.ofNullable(m_cacheData.get(key)).map(CacheValue::getValue);
	}

	@Override
	public void put(K key, V value) {
		m_cacheData.put(key, createNewCacheValue(value));
		System.out.println(this.get(key).get());
	}

	@Override
	public void remove(K key) {
		m_cacheData.remove(key);
	}

	@Override
	public void clean() {
		/**
		 * remove all the expired key from the cache
		 */
		m_cacheData.keySet().removeAll(getExpiredKeys());
	}
	public boolean isExpired(K key) {
		/**
		 * the expiration date is obtained by adding the created date of the value to the timeout value. 
		 */
		LocalDateTime expirationDate = m_cacheData.get(key).getCreatedTime().plus(m_timeOut,ChronoUnit.MILLIS);
		return LocalDateTime.now().isAfter(expirationDate);
	}
	public Set<K> getExpiredKeys(){
		return m_cacheData.keySet().stream().filter(e -> isExpired(e)).collect(Collectors.toSet());
	}
	@Override
	public void clear() {
		m_cacheData.clear();
	}
	/**
	 * this is the wrapper for a item in the cache data structure
	 * @author URZAIZ
	 *
	 * @param <V>
	 */
	protected interface CacheValue<V>{
		V getValue();
		LocalDateTime getCreatedTime();
	}
	/**
	 * return an instance of an anonymous inner class implementing the interface CacheValue<V> that
	 * represent a new object to cache into the cache data structure
	 * @param value
	 * @return
	 */
	protected CacheValue<V> createNewCacheValue(V value) {
		LocalDateTime createdDate = LocalDateTime.now();
		return new CacheValue<V>() {
			@Override
			public V getValue() {
				// TODO Auto-generated method stub
				return value;
			}

			@Override
			public LocalDateTime getCreatedTime() {
				// TODO Auto-generated method stub
				return createdDate;
			}
			
		};
	}
}
