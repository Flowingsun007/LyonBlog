package com.flowingsun.common.dao;

import com.flowingsun.common.utils.SerializeUtils;
import com.flowingsun.user.entity.Role;
import com.flowingsun.user.entity.User;
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Arrays;
import java.util.List;


public class RedisDAO {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private JedisPool jedisPool;

    private final static String REDIS_IP = "localhost";

    private final static int REDIS_PORT = 6379;

    private RuntimeSchema<User> schema = RuntimeSchema.createFrom(User.class);
    //也可以用：private Schema<User> schema = RuntimeSchema.createFrom(User.class);

    public RedisDAO(String REDIS_IP, int REDIS_PORT){
        jedisPool = new JedisPool(REDIS_IP,REDIS_PORT);
    }


    /**
     *@Param [userId]
     *@Return com.flowingsun.user.entity.User
     *@Description getRedisUser
     * 根据传入的userId来做redis中的key，value则是序列化成字节数组的User对象
     * 其中将User对象序列化成自己数组没有用原生的Serilizable接口自己转化，
     * 而是用的protostuff来序列化，具体方法见：setRedisUser()；
     */
    public User getRedisUser(Long userId){
        User userInfo = null;
        try{
            Jedis jedis = jedisPool.getResource();
            try{
                userInfo = schema.newMessage();
                String key = "userId:" + userId;
                //从redis上取key为userId的对象——相应的用户User的字节数组byte[]
                byte[] bytes = jedis.get(key.getBytes());
                if(bytes != null){
                    //schema创建的空User对象，将字节数组中的信息反序列化到User对象上
                    ProtostuffIOUtil.mergeFrom(bytes,userInfo,schema);
                    System.out.println("userInfo:"+userInfo.toString());
                    return userInfo;
                }
            }finally{
                jedis.close();
            }
        } catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return userInfo;
    }

    /**
     *@Param [user]
     *@Return java.lang.String
     *@Description setRedisUser
     * 通过setRedisUser()方法来将从数据库查询出来的User对象序列化存储到redis上
     * 序列化的方式是用protostuff的ProtobufIOUtil中的toByteArray方法，将User对象序列化成字节数组
     * 返回String类的结果，"OK"表示序列化存储成功。返回null则表示失败。
     */
    public String setRedisUser(User user){
        String result=null;
        try{
            Jedis jedis = jedisPool.getResource();
            try{
                System.out.println(user.toString());
                String key = "userId:"+user.getId();
                byte[] userInfo = ProtobufIOUtil.toByteArray(user, schema,
                        LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                String s = new String(userInfo);
                System.out.println(s);
                int timeout = 60 * 60;
                result = jedis.setex(key.getBytes(), timeout, userInfo);
                return result;
            }finally {
                jedis.close();
            }
        } catch(Exception e){
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     *@Param [userId]
     *@Return com.flowingsun.user.entity.User
     *@Description getRedisRole
     * 根据传入的userId来做redis中的key，value则是序列化成字节数组的User对象
     * 其中将User对象序列化成自己数组没有用原生的Serilizable接口自己转化，
     * 而是用的protostuff来序列化，具体方法见：setRedisUser()；
     */
    public User getRedisRole(Long userId){
        User roleInfo = schema.newMessage();
        try{
            Jedis jedis = jedisPool.getResource();
            try{
                String key = "userRole:" + userId;
                //从redis上取key为userRoleInfo+userId —相应的用户User的字节数组byte[]
                byte[] bytes = jedis.get(key.getBytes());
                if(bytes != null){
                    //schema创建的空User对象，将字节数组中的信息反序列化到User对象上
                    ProtostuffIOUtil.mergeFrom(bytes,roleInfo,schema);
                    System.out.println("roleInfo:"+roleInfo.toString());
                    return roleInfo;
                }
            }finally{
                jedis.close();
            }
        } catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return roleInfo;
    }

    /**
     *@Param [userRole]
     *@Return java.lang.String
     *@Description setRedisRole
     * 通过setRedisUser()方法来将从数据库查询出来的User对象序列化存储到redis上
     * 序列化的方式是用protostuff的ProtobufIOUtil中的toByteArray方法，将User对象序列化成字节数组
     * 返回String类的结果，"OK"表示序列化存储成功。返回null则表示失败。
     */
    public String setRedisRole(User userRole){
        String result="set_userRole_fail";
        try{
            Jedis jedis = jedisPool.getResource();
            try{
                System.out.println(userRole.toString());
                String key = "userRole:"+userRole.getId();
                byte[] roleInfo = ProtobufIOUtil.toByteArray(userRole, schema,
                        LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                int timeout = 60 * 60;
                result = jedis.setex(key.getBytes(), timeout, roleInfo);
                return result;
            }finally {
                jedis.close();
            }
        } catch(Exception e){
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    public boolean removeUser(Long userId){
        boolean flag = false;
        try{
            Jedis jedis = jedisPool.getResource();
            try{
                String key = "userId:"+userId;
                Long result = jedis.del(key);
                if(result.equals(new Long(1))){
                    flag=true;
                }
            }finally {
                jedis.close();
                return flag;
            }
        } catch(Exception e){
            logger.error(e.getMessage(), e);
            return flag;
        }
    }

    public <T> String setList(String key ,List<T> list){
        String result="setList_fail";
        try {
            Jedis jedis = jedisPool.getResource();
            try{
                byte[] listInfo = SerializeUtils.serialize(list);
                result = jedis.set(key.getBytes(), listInfo);
            }finally {
                jedis.close();
                return result;
            }
        } catch (Exception e) {
            logger.error("Set key error : "+e);
            return result;
        }
    }

    public <T> List<T> getList(String key){
        List<T> list = null;
        try {
            Jedis jedis = jedisPool.getResource();
            try{
                byte[] in = jedis.get(key.getBytes());
                list = (List<T>) SerializeUtils.unserialize(in);
            }finally {
                jedis.close();
                return list;
            }
        } catch (Exception e) {
            logger.error("Set key error : "+e);
            return list;
        }
    }

    public String setString(String key ,String value){
        String result=null;
        try {
            Jedis jedis = jedisPool.getResource();
            try{
                result = jedis.set(key,value);
            }finally {
                jedis.close();
                return result;
            }
        } catch (Exception e) {
            logger.error("RedisDAO setString error : "+e);
            return result;
        }
    }

    public String getString(String key){
        String result=null;
        try {
            Jedis jedis = jedisPool.getResource();
            try{
                result = jedis.get(key);
            }finally {
                jedis.close();
                return result;
            }
        } catch (Exception e) {
            logger.error("RedisDAO getString error : "+e);
            return result;
        }
    }
}
