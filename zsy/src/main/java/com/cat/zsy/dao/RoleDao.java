package com.cat.zsy.dao;

import com.cat.zsy.common.CommonDao;
import com.cat.zsy.common.Page;
import com.cat.zsy.common.Sort;
import com.cat.zsy.entity.Role;

import java.util.List;

public interface RoleDao extends CommonDao<Role, Long> {

    List<Role> findList(String name, Sort sort, Page page);

    int count(String name);
}
