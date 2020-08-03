package com.landleaf.homeauto.common.mybatis.typehandler;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 布尔类型转换器
 *
 * @author Yujiumin
 * @version 2020/07/13
 */
public class BooleanTypeHandler implements TypeHandler<Boolean> {

    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, Boolean flag, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, flag ? "1" : "0");
    }

    @Override
    public Boolean getResult(ResultSet resultSet, String columnName) throws SQLException {
        String flag = resultSet.getString(columnName);
        return "1".equals(flag);
    }

    @Override
    public Boolean getResult(ResultSet resultSet, int i) throws SQLException {
        String flag = resultSet.getString(i);
        return "1".equals(flag);
    }

    @Override
    public Boolean getResult(CallableStatement callableStatement, int i) throws SQLException {
        String flag = callableStatement.getString(i);
        return "1".equals(flag);
    }
}
