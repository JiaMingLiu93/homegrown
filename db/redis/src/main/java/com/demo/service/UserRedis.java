package com.demo.service;

import com.demo.entity.User;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Repository
public class UserRedis {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void add(String key, Long time, User user) {
        Gson gson = new Gson();
        redisTemplate.opsForValue().set(key, gson.toJson(user), time, TimeUnit.SECONDS);
    }

    public void add(String key, Long time, List<User> users) {
        Gson gson = new Gson();
        redisTemplate.opsForValue().set(key, gson.toJson(users), time, TimeUnit.SECONDS);
    }

    public User get(String key){
        Gson gson = new Gson();
        String value = redisTemplate.opsForValue().get(key);
        if (StringUtils.isEmpty(value)){
            return null;
        }
        return gson.fromJson(value,User.class);
    }

    public List<User> getList(String key){
        Gson gson = new Gson();
        String value = redisTemplate.opsForValue().get(key);
        if (StringUtils.isEmpty(value)){
            return Lists.newArrayList();
        }
        return gson.fromJson(value,new TypeToken<List<User>>(){}.getType());
    }

    public void delete(String key){
        redisTemplate.opsForValue().getOperations().delete(key);
    }

}