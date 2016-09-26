package com.cat.zsy.dao.impl;

import com.cat.zsy.dao.RoleDao;
import com.cat.zsy.entity.Role;
import org.junit.Test;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RoleDaoImplTest extends InitTest {

    @Resource
    private RoleDao roleDao;

    @Test
    public void save() throws Exception {
        Role role = new Role(null, "hello", null, null);
        System.out.println(roleDao.save(role));
    }

    @Test
    public void saves() throws Exception {
        List<Role> list = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            Role role = new Role(null, "hello" + i, LocalDateTime.now(), null);
            list.add(role);
        }
        System.out.println(roleDao.saves(list));

        list.forEach(r -> System.out.println(r));
    }

    @Test
    public void deleteById() throws Exception {

    }

    @Test
    public void deleteByEntity() throws Exception {

    }

    @Test
    public void deleteByIds() throws Exception {

    }

    @Test
    public void deleteByIds1() throws Exception {

    }

    @Test
    public void deleteByEntities() throws Exception {

    }

    @Test
    public void update() throws Exception {

    }

    @Test
    public void updates() throws Exception {

    }

    @Test
    public void merge() throws Exception {

    }

    @Test
    public void merge1() throws Exception {

    }

    @Test
    public void findById() throws Exception {

    }

    @Test
    public void findList() throws Exception {

    }

    @Test
    public void findList1() throws Exception {

    }

    @Test
    public void findList2() throws Exception {

    }

    @Test
    public void count() throws Exception {

    }

    @Test
    public void count1() throws Exception {

    }

}