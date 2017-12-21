package com.demo;

import com.demo.entity.Department;
import com.demo.entity.Role;
import com.demo.entity.User;
import com.demo.service.UserRedis;
import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RedisConfig.class, UserRedis.class})
public class RedisTest {
    private static Logger logger = LoggerFactory.getLogger(RedisTest.class);

    @Autowired
    UserRedis userRedis;

    @Before
    public void setUp(){
        Department department = new Department();
        department.setName("开发部");
        Role role = new Role();
        role.setName("admin");

        User user = new User();
        user.setName("user");
        user.setCreateDate(new Date());
        user.setDepartment(department);

        ArrayList<Role> roles = Lists.newArrayList(role);
        user.setRoles(roles);

        userRedis.delete(this.getClass().getName()+":userByname:"+user.getName());
        userRedis.add(this.getClass().getName()+":userByname:"+user.getName(),10L,user);
    }

    @Test
    public void get(){
        User user = userRedis.get(this.getClass().getName() + ":userByname:user");
        Assert.assertNotNull(user);
        logger.info("==========user========== name:{},department:{},role:{}",user.getName(),user.getDepartment()
        ,user.getRoles().get(0).getName());
    }

}
