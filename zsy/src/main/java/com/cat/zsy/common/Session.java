package com.cat.zsy.common;

import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class Session extends SqlSessionTemplate {

	public Session(SqlSessionFactory sqlSessionFactory) {
		super(sqlSessionFactory);
	}

	public <T> T selectOne() {
		return super.selectOne(null);
	}

	public <T> T selectOne(Object parameter) {
		return null;
	}

	public <E> List<E> selectList() {
		return null;
	}

	public <E> List<E> selectList(Object parameter) {
		return null;
	}

	public <E> List<E> selectList(Object parameter, RowBounds rowBounds) {
		return null;
	}

	public <K, V> Map<K, V> selectMap(String mapKey) {
		return null;
	}

	public <K, V> Map<K, V> selectMap(Object parameter, String mapKey) {
		return null;
	}

	public <K, V> Map<K, V> selectMap(Object parameter, String mapKey, RowBounds rowBounds) {
		return null;
	}

	public <T> Cursor<T> selectCursor() {
		return null;
	}

	public <T> Cursor<T> selectCursor(Object parameter) {
		return null;
	}

	public <T> Cursor<T> selectCursor(Object parameter, RowBounds rowBounds) {
		return null;
	}

	public void select(Object parameter, ResultHandler handler) {

	}

	public void select(ResultHandler handler) {

	}

	public void select(Object parameter, RowBounds rowBounds, ResultHandler handler) {

	}

	public int insert() {
		return 0;
	}

	public int insert(Object parameter) {
		return 0;
	}

	public int update() {
		return 0;
	}

	public int update(Object parameter) {
		return 0;
	}

	public int delete() {
		return 0;
	}

	public int delete(Object parameter) {
		return 0;
	}

	public void commit() {

	}

	public void commit(boolean force) {

	}

	public void rollback() {

	}

	public void rollback(boolean force) {

	}

	public List<BatchResult> flushStatements() {
		return null;
	}

	public void close() {

	}

	public void clearCache() {

	}

	public Configuration getConfiguration() {
		return null;
	}

	public <T> T getMapper(Class<T> type) {
		return null;
	}

	public Connection getConnection() {
		return null;
	}

	/*public Session(SqlSessionFactory sqlSessionFactory) {
		super(sqlSessionFactory);
	}*/

}
