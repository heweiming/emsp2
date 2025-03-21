package com.volvotest.emsp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.volvotest.emsp.model.Account;
import com.volvotest.emsp.common.AccountStatus;
import com.volvotest.emsp.model.Card;
import com.volvotest.emsp.common.CardStatus;
import org.junit.jupiter.api.Test;
import org.slf4j.ILoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class EmspApplicationTests {
	@Autowired
	private MockMvc mockMvc;
	private static final ObjectMapper om = new ObjectMapper();
	@Autowired
	private ObjectMapper objectMapper; // Converts objects to JSON and vice versa

	private final String contractId = "SEVOLXC40123455";

	@Test
	void contextLoads() {
	}
	@Test
	void testQueryAccount() throws Exception {
		System.out.println("At first, Account should empty.");
		mockMvc.perform(get("/accounts"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$").isEmpty());
		System.out.println("Then create a account.");
		this.createAccount();
		System.out.println("After create a account, Account should not empty.");
		mockMvc.perform(get("/accounts"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$").isNotEmpty())
				.andExpect(jsonPath("$[0].uid").value(1));
	}

	void createAccount() throws Exception {
		// Create an Account object
		Account account = new Account(-1, "tohe@qq.com", "heweiming", contractId, AccountStatus.ACTIVED);
		// Convert the Account object to JSON
		String accountJson = objectMapper.writeValueAsString(account);

		mockMvc.perform(post("/accounts").contentType(MediaType.APPLICATION_JSON_VALUE).content(accountJson))
				.andDo(print());
	}
	@Test
	void testCreateAccount() throws Exception {

		// Create an Account object
		Account account = new Account(-1, "tohe@qq.com", "heweiming", contractId, AccountStatus.ACTIVED);
		// Convert the Account object to JSON
		String accountJson = objectMapper.writeValueAsString(account);

		mockMvc.perform(post("/accounts").contentType(MediaType.APPLICATION_JSON_VALUE).content(accountJson))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isNumber())
				.andExpect(jsonPath("$").value(1));
	}

	@Test
	void updateAccountStatus() throws Exception {
		// Create an Account object
		Account account = new Account(-1, "tohe@qq.com", "heweiming", contractId, AccountStatus.DEACTIVED);

		// Convert the Account object to JSON
		String accountJson = objectMapper.writeValueAsString(account);

		mockMvc.perform(post("/accounts").contentType(MediaType.APPLICATION_JSON_VALUE).content(accountJson))
				.andDo(print()).andExpect(jsonPath("$").value(1));

		mockMvc.perform(get("/accounts/1"))
				.andDo(print())
				.andExpect(status().isOk())
						.andExpect(jsonPath("$.status").value("DEACTIVED"));

		account = new Account(1, "tohe@qq.com", "heweiming", contractId, AccountStatus.ACTIVED);

		mockMvc.perform(MockMvcRequestBuilders.put("/accounts")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(account)))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isBoolean());

		mockMvc.perform(get("/accounts/1"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("ACTIVED"));
	}

	@Test
	void testQueryCardList() throws Exception {
		this.createCard();
		System.out.println("At first, Card should empty.");
		mockMvc.perform(get("/cards"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$").isNotEmpty());

	}

	void createCard() throws Exception {
		// Create a Card object
		Card card = new Card(-1, "123456","", CardStatus.DEACTIVED, 100);
		// Convert the Card object to JSON
		String cardJson = objectMapper.writeValueAsString(card);
		mockMvc.perform(post("/cards").contentType(MediaType.APPLICATION_JSON_VALUE).content(cardJson))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isNotEmpty());
	}

	@Test
	void testCreateCard() throws Exception {
		// Create a Card object
		Card card = new Card(-1, "123456","123456", CardStatus.ACTIVED, 100);
		// Convert the Card object to JSON
		String cardJson = objectMapper.writeValueAsString(card);
		mockMvc.perform(post("/cards").contentType(MediaType.APPLICATION_JSON_VALUE).content(cardJson))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").value(1));
	}

	@Test
	void testUpdateCard() throws Exception {
		this.createCard();
		Card card = new Card(1,"123456", "1234",  CardStatus.ACTIVED, 200);
		mockMvc.perform(MockMvcRequestBuilders.put("/cards")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(card)))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isBoolean());

		mockMvc.perform(get("/cards/1"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("ACTIVED"));
	}

	@Test
	void testDeleteCard() throws Exception {
		this.createCard();
		mockMvc.perform(MockMvcRequestBuilders.delete("/cards/1"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").value(1));
	}

	@Test
	void testLinkCardToAccount() throws Exception {
		this.createAccount();
		this.createCard();
		mockMvc.perform(get("/cards/1")).andDo(print()).andExpect(jsonPath("$.contractId").value(""));

		mockMvc.perform(MockMvcRequestBuilders.post("/accounts/1/link")
						.contentType(MediaType.APPLICATION_JSON)
						.content("1"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isBoolean());

		mockMvc.perform(get("/cards/1")).andDo(print()).andExpect(jsonPath("$.contractId").value(this.contractId));
	}


}
