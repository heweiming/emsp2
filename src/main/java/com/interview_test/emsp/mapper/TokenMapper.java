package com.interview_test.emsp.mapper;

import com.interview_test.emsp.model.Token;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

//@Mapper
@Deprecated
public interface TokenMapper {
    @Insert("INSERT INTO tokens (account_id, token, expired_at, created_at, last_updated_at) VALUES (#{accountId}, #{token}, #{expiredAt}, #{createdAt}, #{lastUpdatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public void insertToken(Token token) ;

    @Select("SELECT id, account_id, token, expired_at, created_at, last_updated_at FROM tokens WHERE id = #{id}")
    @Results(id="tokenResultMap", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "accountId", column = "account_id"),
            @Result(property = "token", column = "token"),
            @Result(property = "expiredAt", column = "expired_at"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "lastUpdatedAt", column = "last_updated_at")
    })
    public Token getTokenById(Long id);

    @Select("SELECT id, account_id, token, expired_at, created_at, last_updated_at FROM tokens WHERE account_id = #{accountId}")
    public List<Token> getTokenByAccountId(Long accountId);
    @Update("UPDATE tokens SET token = #{token}, expired_at = #{expiredAt}, last_updated_at = #{lastUpdatedAt} WHERE id = #{id}")
    public void updateToken(Token token) ;
    @Delete("DELETE FROM tokens WHERE id = #{id}")
    public void deleteToken(Long id) ;

    @Select("select id, account_id, token, expired_at, created_at, last_updated_at from tokens where last_updated_at >= #{lastUpdateAt} and account_id = #{accountId} and id>#{lastId} order by id asc limit #{pageSize}")
    public List<Token> getTokensByAccountIdAndLastUpdatedAt(@Param("accountId") Long accountId, @Param("lastUpdateAt") Date lastUpdateAt, @Param("lastId") Long lastId, @Param("pageSize") int pageSize);
}
