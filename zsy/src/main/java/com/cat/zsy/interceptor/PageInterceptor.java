package com.cat.zsy.interceptor;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.Properties;

@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class})})
public class PageInterceptor implements Interceptor {
    static final Logger logger = LoggerFactory.getLogger(PageInterceptor.class);

    public Object intercept(Invocation invocation) throws Throwable {
       /* RoutingStatementHandler handler = (RoutingStatementHandler) invocation.getTarget();
        BaseStatementHandler delegate = (BaseStatementHandler) BeanPropertyUtils.getFieldValue(handler, "delegate");

        BoundSql boundSql = delegate.getBoundSql();
        // 获取分页参数
        Object param = boundSql.getParameterObject();
        if (param == null || !(param instanceof PageModel)) {
            return invocation.proceed();
        }

        PageModel<?> model = (PageModel<?>) param;

        if (model.isQueryAll()) {
            return invocation.proceed();
        }

        String sql = boundSql.getSql();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            Connection connection = (Connection) invocation.getArgs()[0];
            // 查询总记录数
            String countSql = model.countSql(sql);
            ps = connection.prepareStatement(countSql);
            BeanPropertyUtils.setFieldValue(boundSql, "sql", countSql);
            MappedStatement ms = (MappedStatement) BeanPropertyUtils.getFieldValue(delegate, "mappedStatement");
            DefaultParameterHandler dph = new DefaultParameterHandler(ms, param, boundSql);
            dph.setParameters(ps);

            rs = ps.executeQuery();
            long count = 0;
            if (rs.next()) {
                count = ((Number) rs.getObject(1)).longValue();
            }

            model.setTotalCount(count);
        } catch (SQLException ex) {
            logger.error("can't get count " + ex.getMessage());
        } finally {
            JdbcUtils.close(rs);
            JdbcUtils.close(ps);
        }

        BeanPropertyUtils.setFieldValue(boundSql, "sql", model.pageSql(sql));*/

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
    /* 当目标类是StatementHandler类型时，才包装目标类，不做无谓的代理 */
        return (target instanceof StatementHandler) ? Plugin.wrap(target, this) : target;
    }

    @Override
    public void setProperties(Properties properties) {
    }

}