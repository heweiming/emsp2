package com.interview_test.emsp.mapper;

import com.interview_test.emsp.model.Account;
import com.interview_test.emsp.valueobject.AccountStatusVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AccountMapper {
    @Insert("INSERT INTO accounts (email, name, contract_id, status) VALUES (#{email}, #{name}, #{contractId}, #{status, jdbcType=INTEGER})")
    @Options(useGeneratedKeys = true, keyProperty = "uid")
    void insertAccount(Account account);

    @Select("SELECT * FROM accounts WHERE uid = #{uid}")
    Account getAccountById(Long id);

    @Select("SELECT * FROM accounts")
    List<Account> getAllAccounts();

    @Update("UPDATE accounts SET status = #{status} WHERE uid = #{id}")
    void updateAccount(AccountStatusVO accountStatusVO);

    @Delete("DELETE FROM accounts WHERE uid = #{uid}")
    void deleteAccount(Long id);
}
