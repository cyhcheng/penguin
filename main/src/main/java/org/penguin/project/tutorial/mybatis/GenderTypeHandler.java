package org.penguin.project.tutorial.mybatis;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;
import org.penguin.project.tutorial.enums.Gender;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedJdbcTypes(JdbcType.INTEGER)
@MappedTypes(value = Gender.class)
public class GenderTypeHandler implements TypeHandler<Gender> {

    @Override
    public void setParameter(PreparedStatement preparedStatement, int paramInt, Gender paramType, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(paramInt, paramType.getIndex());
    }

    @Override
    public Gender getResult(ResultSet resultSet, String name) throws SQLException {
        return Gender.getGender(resultSet.getInt(name));
    }

    @Override
    public Gender getResult(ResultSet resultSet, int col) throws SQLException {
        return Gender.getGender(resultSet.getInt(col));
    }

    @Override
    public Gender getResult(CallableStatement callableStatement, int i) throws SQLException {
        return Gender.getGender(callableStatement.getInt(i));
    }
}
