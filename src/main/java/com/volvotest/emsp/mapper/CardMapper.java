package com.volvotest.emsp.mapper;

import com.volvotest.emsp.common.CardStatus;
import com.volvotest.emsp.model.Card;
import com.volvotest.emsp.typehandler.CardStatusTypeHandler;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface CardMapper {
    @Insert("INSERT INTO cards (card_number, contract_id, status, balance, expired_at, created_at, last_updated_at) " +
            "VALUES (#{cardNumber}, #{contractId}, #{status}, #{balance}, #{expiredAt}, #{createdAt}, #{lastUpdatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertCard(Card card);

    @ConstructorArgs({
            @Arg(column = "id", javaType = int.class, id = true),
            @Arg(column = "card_number", javaType = String.class),
            @Arg(column = "contract_id", javaType = String.class),
            @Arg(column = "status", javaType = CardStatus.class, typeHandler = CardStatusTypeHandler.class),
            @Arg(column = "balance", javaType = int.class),
            @Arg(column = "expired_at", javaType = Date.class),
            @Arg(column = "created_at", javaType = Date.class),
            @Arg(column = "last_updated_at", javaType = Date.class)
    })
    @Select("SELECT id, card_number, contract_id, status, balance, expired_at, created_at, last_updated_at FROM cards WHERE id = #{id}")
    Card getCardById(Long id);

    @Select("SELECT id, card_number, contract_id, status, balance, expired_at, created_at, last_updated_at FROM cards WHERE contract_id = #{contractId}")
    @ConstructorArgs({
            @Arg(column = "id", javaType = int.class, id = true),
            @Arg(column = "card_number", javaType = String.class),
            @Arg(column = "contract_id", javaType = String.class),
            @Arg(column = "status", javaType = CardStatus.class, typeHandler = CardStatusTypeHandler.class),
            @Arg(column = "balance", javaType = int.class),
            @Arg(column = "expired_at", javaType = Date.class),
            @Arg(column = "created_at", javaType = Date.class),
            @Arg(column = "last_updated_at", javaType = Date.class)
    })
    List<Card> getCardsByContractId(String contractId);

    @Update("UPDATE cards SET contract_id = #{contractId} WHERE id = #{id}")
    int updateCardContractId(int id, String contractId);

    @Update("UPDATE cards SET status = #{status} WHERE id = #{id}")
    void updateCardStatus(Card card);

    @Delete("DELETE FROM cards WHERE id = #{id}")
    int deleteCard(Long id);




    @Select("SELECT id, card_number, contract_id, status, balance, expired_at, created_at, last_updated_at FROM cards")
    @ConstructorArgs({
            @Arg(column = "id", javaType = int.class, id = true),
            @Arg(column = "card_number", javaType = String.class),
            @Arg(column = "contract_id", javaType = String.class),
            @Arg(column = "status", javaType = CardStatus.class, typeHandler = CardStatusTypeHandler.class),
            @Arg(column = "balance", javaType = int.class),
            @Arg(column = "expired_at", javaType = Date.class),
            @Arg(column = "created_at", javaType = Date.class),
            @Arg(column = "last_updated_at", javaType = Date.class)
    })
    List<Card> getAllCards();

    @Select("select cards.id, cards.card_number, cards.contract_id, cards.status, cards.balance, cards.expired_at, cards.created_at, cards.last_updated_at " +
            "from cards inner join accounts on cards.contract_id=accounts.contract_id " +
            "where cards.last_updated_at >= #{lastUpdateAt} and accounts.uid = #{accountId} " +
            "and cards.id>#{lastId} order by cards.id asc limit #{pageSize}")
    @ConstructorArgs({
            @Arg(column = "id", javaType = int.class, id = true),
            @Arg(column = "card_number", javaType = String.class),
            @Arg(column = "contract_id", javaType = String.class),
            @Arg(column = "status", javaType = CardStatus.class, typeHandler = CardStatusTypeHandler.class),
            @Arg(column = "balance", javaType = int.class),
            @Arg(column = "expired_at", javaType = Date.class),
            @Arg(column = "created_at", javaType = Date.class),
            @Arg(column = "last_updated_at", javaType = Date.class)
    })
    List<Card> getCardsByAccountIdAndLastUpdatedAt(@Param("accountId") Long accountId, @Param("lastUpdateAt") Date lastUpdateAt, @Param("lastId") Long lastId, @Param("pageSize") int pageSize);
}
