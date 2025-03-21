package com.volvotest.emsp.mapper;

import com.volvotest.emsp.common.CardStatus;
import com.volvotest.emsp.model.Card;
import com.volvotest.emsp.typehandler.CardStatusTypeHandler;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CardMapper {
    @Insert("INSERT INTO cards (card_number, contract_id, status, balance) VALUES (#{cardNumber}, #{contractId}, #{status}, #{balance})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertCard(Card card);

    @ConstructorArgs({
            @Arg(column = "id", javaType = int.class, id = true),
            @Arg(column = "card_number", javaType = String.class),
            @Arg(column = "contract_id", javaType = String.class),
            @Arg(column = "status", javaType = CardStatus.class, typeHandler = CardStatusTypeHandler.class),
            @Arg(column = "balance", javaType = int.class)
    })
    @Select("SELECT * FROM cards WHERE id = #{id}")
    Card getCardById(Long id);

    @Select("SELECT * FROM cards WHERE contract_id = #{contractId}")
    List<Card> getCardsByContractId(Long contractId);

    @Update("UPDATE cards SET contract_id = #{contractId} WHERE id = #{id}")
    int updateCardContractId(int id, String contractId);

    @Update("UPDATE cards SET status = #{status} WHERE id = #{id}")
    void updateCardStatus(Card card);

    @Delete("DELETE FROM cards WHERE id = #{id}")
    int deleteCard(Long id);


    @Results(id = "userResultMap", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "card_number", column = "cardNumber"),
            @Result(property = "contract_id", column = "contractId"),
            @Result(property = "status", column = "status", typeHandler = CardStatusTypeHandler.class)
    })

    @Select("SELECT * FROM cards")
    List<Card> getAllCards();
}
