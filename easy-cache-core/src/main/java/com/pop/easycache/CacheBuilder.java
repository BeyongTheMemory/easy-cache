package com.pop.easycache;

import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.pop.easycache.cache.Cache;
import com.pop.easycache.factory.CacheFactory;
import com.pop.easycache.factory.CacheFactoryImlp;
import com.pop.easycache.proxy.CglibProxy;
import com.pop.easycache.proxy.Proxy;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import redis.clients.jedis.JedisPool;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by xugang on 17/6/21.
 */
public class CacheBuilder implements BeanPostProcessor,InitializingBean {
    /**
     * 序列化类型
     */
    private String serializeType = "fastJson";
    /**
     * 本地缓存实现类型
     */
    private String localCacheType = "guava";
    /**
     * 远程缓存实现类型
     */
    private String remoteCacheType = "redis";
    /**
     * 注册中心类型，用于同步缓存更新
     */
    private String registryType = "zooKeeper";
    /**
     * 是否开启本地缓存
     * 默认:false
     */
    private boolean useLocalCache = false;
    /**
     * 是否开启远程缓存
     * 默认:false
     */
    private boolean useRemoteCache = false;
    /**
     * 是否启用注册中心
     * 默认:false
     */
    private boolean useRegistry = false;
    /**
     * 用户自定义zk引用
     */
    private ZooKeeper zooKeeper;
    /**
     * 用户自定义jedis引用
     */
    private JedisPool jedisPool;
    /**
     * redis Url
     */
    private String redisUrl;
    /**
     * redis端口
     */
    private int redisPort;
    /**
     * zk地址
     */
    private String registryServer;

    /**
     * zk超时时间
     */
    private int sessionTimeOut = 200000;

    /**
     * 注册中心写根节点
     */
    private String registryPath = "/easyCache_localCache";

    /**
     * fastJson反序列化参数
     */
    private SerializerFeature[] serializerFeatures = new SerializerFeature[0];

    /**
     * fastJson序列化参数
     */
    private Feature[] feature = new Feature[0];

    /**
     * 统一缓存超时时间
     */
    private long ttl = -1;

    /**
     * 统一缓存超时时间单位
     */
    private TimeUnit ttlUnit = TimeUnit.SECONDS;

    /**
     * 本地缓存大小
     */
    private long localMaxSize  = -1;

    private Map<String,Object> cacheBeanMap;

    private Proxy proxy;

    public void afterPropertiesSet() throws Exception {
        cacheBeanMap = new ConcurrentHashMap<String, Object>();
        CacheFactory cacheFactory = new CacheFactoryImlp(this);
        proxy = new CglibProxy(cacheFactory.getCache(),cacheFactory.getSerialize());
    }

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(bean != null){
            wrapIfNecessary(bean,beanName);
        }

        return bean;
    }

    private Object wrapIfNecessary(Object bean,String beanName){
        Object cacheBean = cacheBeanMap.get(beanName);
        if(cacheBean != null){
            return cacheBean;
        }
        Method[] methods = bean.getClass().getMethods();
        for (Method method:methods){
            if(method.getAnnotation(Cacheable.class) != null || method.getAnnotation(CacheEvict.class) != null){
                Object proxyBean = proxy.getProxyObject(bean.getClass());
                cacheBeanMap.put(beanName,proxyBean);
                return proxyBean;
            }
        }
        return bean;
    }

    public String getSerializeType() {
        return serializeType;
    }

    public void setSerializeType(String serializeType) {
        this.serializeType = serializeType;
    }

    public String getLocalCacheType() {
        return localCacheType;
    }

    public void setLocalCacheType(String localCacheType) {
        this.localCacheType = localCacheType;
    }

    public String getRemoteCacheType() {
        return remoteCacheType;
    }

    public void setRemoteCacheType(String remoteCacheType) {
        this.remoteCacheType = remoteCacheType;
    }

    public String getRegistryType() {
        return registryType;
    }

    public void setRegistryType(String registryType) {
        this.registryType = registryType;
    }

    public boolean isUseLocalCache() {
        return useLocalCache;
    }

    public void setUseLocalCache(boolean useLocalCache) {
        this.useLocalCache = useLocalCache;
    }

    public boolean isUseRemoteCache() {
        return useRemoteCache;
    }

    public void setUseRemoteCache(boolean useRemoteCache) {
        this.useRemoteCache = useRemoteCache;
    }

    public boolean isUseRegistry() {
        return useRegistry;
    }

    public void setUseRegistry(boolean useRegistry) {
        this.useRegistry = useRegistry;
    }

    public ZooKeeper getZooKeeper() {
        return zooKeeper;
    }

    public void setZooKeeper(ZooKeeper zooKeeper) {
        this.zooKeeper = zooKeeper;
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public String getRedisUrl() {
        return redisUrl;
    }

    public void setRedisUrl(String redisUrl) {
        this.redisUrl = redisUrl;
    }

    public int getRedisPort() {
        return redisPort;
    }

    public void setRedisPort(int redisPort) {
        this.redisPort = redisPort;
    }


    public SerializerFeature[] getSerializerFeatures() {
        return serializerFeatures;
    }

    public void setSerializerFeatures(SerializerFeature[] serializerFeatures) {
        this.serializerFeatures = serializerFeatures;
    }

    public Feature[] getFeature() {
        return feature;
    }

    public void setFeature(Feature[] feature) {
        this.feature = feature;
    }

    public long getTtl() {
        return ttl;
    }

    public void setTtl(long ttl) {
        this.ttl = ttl;
    }

    public TimeUnit getTtlUnit() {
        return ttlUnit;
    }

    public void setTtlUnit(TimeUnit ttlUnit) {
        this.ttlUnit = ttlUnit;
    }

    public long getLocalMaxSize() {
        return localMaxSize;
    }

    public void setLocalMaxSize(long localMaxSize) {
        this.localMaxSize = localMaxSize;
    }

    public String getRegistryServer() {
        return registryServer;
    }

    public void setRegistryServer(String registryServer) {
        this.registryServer = registryServer;
    }

    public int getSessionTimeOut() {
        return sessionTimeOut;
    }

    public void setSessionTimeOut(int sessionTimeOut) {
        this.sessionTimeOut = sessionTimeOut;
    }

    public String getRegistryPath() {
        return registryPath;
    }

    public void setRegistryPath(String registryPath) {
        this.registryPath = registryPath;
    }
}
