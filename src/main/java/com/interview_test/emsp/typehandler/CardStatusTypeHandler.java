package com.interview_test.emsp.typehandler;

import com.interview_test.emsp.common.CardStatus;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CardStatusTypeHandler extends BaseTypeHandler<CardStatus> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, CardStatus parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.ordinal()); // Store enum ordinal (integer)
    }

    @Override
    public CardStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
        System.out.println("columnName=== " + columnName);
        int ordinal = rs.getInt(columnName);
        return CardStatus.values()[ordinal]; // Convert integer back to enum
    }

    @Override
    public CardStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int ordinal = rs.getInt(columnIndex);
        return CardStatus.values()[ordinal];
    }

    @Override
    public CardStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int ordinal = cs.getInt(columnIndex);
        return CardStatus.values()[ordinal];
    }
}
