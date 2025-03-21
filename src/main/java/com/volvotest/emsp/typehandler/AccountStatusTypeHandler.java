package com.volvotest.emsp.typehandler;

import com.volvotest.emsp.common.AccountStatus;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountStatusTypeHandler extends BaseTypeHandler<AccountStatus> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, AccountStatus parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.ordinal()); // Store enum ordinal (integer)
    }

    @Override
    public AccountStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int ordinal = rs.getInt(columnName);
        return AccountStatus.values()[ordinal]; // Convert integer back to enum
    }

    @Override
    public AccountStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int ordinal = rs.getInt(columnIndex);
        return AccountStatus.values()[ordinal];
    }

    @Override
    public AccountStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int ordinal = cs.getInt(columnIndex);
        return AccountStatus.values()[ordinal];
    }
}
