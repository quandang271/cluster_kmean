package data;


import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RedisRepository {
    protected final JedisPool pool;
    protected Pipeline pipeline = null;

    private static final String emptySValue="";

    public RedisRepository(String host, int port, String pass) {
        this.pool = new JedisPool(new GenericObjectPoolConfig(), host, port, Protocol.DEFAULT_TIMEOUT, pass);
    }

    public RedisRepository(String host, int port) {
        this.pool = new JedisPool(new GenericObjectPoolConfig(), host, port);
    }

    public Jedis getClient() {
        return this.pool.getResource();
    }

    // PUT
    public void putMap(String dbKey, String key, String value) {
        putMap(dbKey.getBytes(), key.getBytes(), value.getBytes());
    }
    public void putMap(byte[] dbKey, byte[] key, byte[] value) {
        Jedis client = this.getClient();
        try {
            client.hset(dbKey, key, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(client!=null)
                client.close();
        }
    }

    public void putBatchMap(String dbKey, Map<String, String> map) {
        Jedis client = this.getClient();
        try {
            client.hmset(dbKey, map);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(client!=null)
                client.close();
        }
    }

    public void putBatchMap(byte[] dbKey, Map<byte[], byte[]> map) {
        Jedis client = this.getClient();
        try {
            client.hmset(dbKey,map);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(client!=null)
                client.close();
        }
    }

    //READ

    public String takeInMap(String dbKey, String key) {
        byte[]results= takeInMap(dbKey.getBytes(), key.getBytes());
        return (results!=null && results.length>0) ? new String(results) : emptySValue;
    }

    public byte[] takeInMap(byte[]dbKey, byte[] key) {
        byte[] results=null;
        Jedis client = this.getClient();
        try {
            results = client.hget(dbKey, key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(client!=null)
                client.close();
        }
        return results;
    }


    public boolean existKey (byte[]dbKey, byte[] key)
    {
        Jedis client = this.getClient();
        try {
            return client.hexists(dbKey, key);
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            if(client != null)
                client.close();
        }
        return false;
    }

    public boolean existKey (String dbKey, String key)
    {
        return existKey(dbKey.getBytes(), key.getBytes());
    }

    public boolean existDbKey(String dbKey)
    {
        return existDbKey(dbKey.getBytes());
    }

    public boolean existDbKey(byte[]key)
    {
        Jedis client = this.getClient();
        try {
             return client.exists(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(client != null)
                client.close();
        }
        return false;
    }

    public Map<String, String> scanAsString(String dbKey, int size){
        Jedis client = (Jedis) this.pool.getResource();
        String cursor = ScanParams.SCAN_POINTER_START;
        ScanParams block = new ScanParams().count(200);
        ScanResult<Map.Entry<String, String>> s=client.hscan(dbKey, cursor, block);

        Map<String, String> out= new HashMap<>();
        do{
            System.out.println("a");
            List<Map.Entry<String,String>> listRs =s.getResult();
            for(Map.Entry<String, String> rs : listRs){
                out.put(rs.getKey(),rs.getValue());
                if(out.size()==size) break;
            }
            if(out.size()==size) break;
              cursor = s.getStringCursor();
        }
        while (!cursor.equals(ScanParams.SCAN_POINTER_START));
        System.out.println(out.size());
        client.close();
        return out;
    }

    //DELETE

    public void truncate(String dbKey) {
        Jedis client = (Jedis) this.pool.getResource();
        try {
            client.del(dbKey);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(client!=null)
                client.close();
        }
    }

    public void createBatchDel() {
        this.pipeline=this.pool.getResource().pipelined();
    }


    public Pipeline getPipeline() {
        return this.pool.getResource().pipelined();
    }


    public void sync() {
        this.pipeline.sync();
    }


}
