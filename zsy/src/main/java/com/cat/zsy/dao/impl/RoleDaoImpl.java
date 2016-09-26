package com.cat.zsy.dao.impl;

import com.cat.kit.BeanKit;
import com.cat.zsy.common.Page;
import com.cat.zsy.common.Sort;
import com.cat.zsy.dao.RoleDao;
import com.cat.zsy.dao.base.TemplateSession;
import com.cat.zsy.entity.Role;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RoleDaoImpl implements RoleDao {

	@Resource
	private TemplateSession session;

	@Resource
	private SqlSessionTemplate sessionTemplate;

	@Override
	public int save(Role role) {
		long begin = System.nanoTime();
		int r = sessionTemplate.insert(RoleDao.class.getName() + ".save", role);
		long end = System.nanoTime();
		System.out.println((end - begin) / 1000 + " us.");
		return r;
	}

	@Override
	public int saves(Collection<Role> roles) {
		return session.insert(roles);
	}

	@Override
	public int deleteById(Long id) {
		return session.delete(id);
	}

	@Override
	public int deleteByEntity(Role role) {
		return session.delete(role);
	}

	@Override
	public int deleteByIds(Long[] ids) {
//		Collection<Long> list = Arrays.asList(ids);
		return session.delete(ids);
	}

	@Override
	public int deleteByIds(Collection<Long> ids) {
		return session.delete(ids.toArray());//TODO
	}

	@Override
	public int deleteByEntities(Collection<Role> roles) {
		return session.delete(roles);
	}

	@Override
	public int update(Role role) {
		return session.update(role);
	}

	@Override
	public int updates(Collection<Role> roles) {
		int count = 0;
		for (Role role : roles) {
			count += this.update(role);
		}
		return count;
	}

	@Override
	public int merge(Role role, String key) {
		//TODO
		Object value = BeanKit.getFieldValue(role, key);
		System.out.println("primary key [" + key + "] value is:" + value);
		return BeanKit.hasPersistent(value) ? this.update(role) : this.save(role);
	}

	@Override
	public int merge(Role role) {
		return this.merge(role, "id");//TODO 默认主键
	}

	@Override
	public Role findById(Long id) {
		return session.selectOne(id);
	}

	@Override
	public List<Role> findList(String name, Sort sort, Page page) {
		Map<String, Object> map = new HashMap<>();
		if (!StringUtils.isEmpty(name)) {
			map.put("name", name);
		}
//		return session.selectList(map, sort, page);
		return null;
	}

	@Override
	public List<Role> findList(Sort sort, Page page) {
		return this.findList(null, sort, page);
	}

	@Override
	public List<Role> findList() {
		return this.findList(null, null);
	}

	@Override
	public int count(String name) {
		Map<String, Object> map = new HashMap<>();
		if (!StringUtils.isEmpty(name)) {
			map.put("name", name);
		}
		return session.selectOne(map);
	}

	@Override
	public int count() {
		return session.selectOne();
	}
}
