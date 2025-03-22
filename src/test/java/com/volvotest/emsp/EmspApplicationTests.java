package com.volvotest.emsp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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

	@Test
	void testGenerateToken() throws Exception {
		this.createAccount();
		mockMvc.perform(MockMvcRequestBuilders.post("/accounts/1/generate_token"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1));
		mockMvc.perform(MockMvcRequestBuilders.post("/accounts/1/generate_token"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(2));
		mockMvc.perform(MockMvcRequestBuilders.post("/accounts/1/generate_token"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(3));
	}

	@Test
	void testPaginationQueryToken() throws Exception {
		List<String> tokenJsonList = new ArrayList<>();
		this.createAccount();
		for (int i = 0; i < 20; i++) {
			tokenJsonList.add(this.generateToken(1));
			Thread.sleep(100L);
		}
		tokenJsonList.forEach(System.out::println);

//		ObjectMapper objectMapper = new ObjectMapper();
//		JsonNode lastTokenNode = objectMapper.readTree(tokenJsonList.get(tokenJsonList.size() -1));
//		String id = lastTokenNode.get("id").asText();
//		System.out.println("last id: " + id);
//		String tokenJson = mockMvc.perform(MockMvcRequestBuilders.get("/accounts/"+1+"/tokens/" + id))
//				.andDo(print())
//				.andExpect(status().isOk())
//				.andExpect(jsonPath("$.id").value(20))
//				.andReturn().getResponse().getContentAsString();
//		System.out.println("tokenJson: " + tokenJson);


		String jsonString = tokenJsonList.get(0);
		JsonNode rootNode = objectMapper.readTree(jsonString);

		// Extract specific field value
		String lastUpdatedAt = rootNode.get("lastUpdatedAt").asText();
		String id = rootNode.get("id").asText();
		System.out.println("first lastUpdatedAt: " + lastUpdatedAt + ", id: " + id);

		String dateString = lastUpdatedAt;

		// Step 1: Parse the string into OffsetDateTime
		OffsetDateTime offsetDateTime = OffsetDateTime.parse(dateString);

		// Step 2: Convert OffsetDateTime to java.util.Date
		Date date = Date.from(offsetDateTime.toInstant());
		long milliseconds = date.getTime();
		System.out.println("Converted Date: " + date + ", milliseconds: " + date.getTime());

		final int PAGE_SIZE = 3;

		final long[] lastId4Page = {-1};
		final long[] lastUpdatedAtMilliseconds = {-1};
		mockMvc.perform(get("/accounts/1/tokens/last_updated_at/" + milliseconds + "/last_id/0/page_size/" + PAGE_SIZE))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$").isNotEmpty())
				.andExpect(jsonPath("$[0].id").value(1))
				.andExpect(jsonPath("$[2].id").value(3))
				.andDo(new ResultHandler() {
					@Override
					public void handle(MvcResult result) throws Exception {
						String content = result.getResponse().getContentAsString();
						System.out.println(content);
						JsonNode jsonNode = objectMapper.readTree(content);
						long lastId = jsonNode.get(jsonNode.size()-1).get("id").asLong();

						long pageFirstId = jsonNode.get(0).get("id").asLong();
						long pageLastId = jsonNode.get(jsonNode.size()-1).get("id").asLong();
						System.out.println("PaginationQuery: pageFirstId: " + pageFirstId + ", pageLastId: " + pageLastId);


						String lastUpdatedAt = jsonNode.get(jsonNode.size()-1).get("lastUpdatedAt").asText();
						System.out.println("PaginationQuery: lastId: " + lastId + ", lastUpdatedAt: " + lastUpdatedAt);
						Date date = Date.from(OffsetDateTime.parse(lastUpdatedAt).toInstant());
						long milliseconds = date.getTime();
//						System.out.println("Converted Date: " + date + ", milliseconds: " + date.getTime());
						lastUpdatedAtMilliseconds[0] = milliseconds;
						lastId4Page[0] = lastId;
					}
				});

		mockMvc.perform(get("/accounts/1/tokens/last_updated_at/" + lastUpdatedAtMilliseconds[0] + "/last_id/"+lastId4Page[0]+"/page_size/" + PAGE_SIZE))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$").isNotEmpty())
				.andExpect(jsonPath("$[0].id").value(4))
				.andExpect(jsonPath("$[2].id").value(6))
				.andDo(new ResultHandler() {
					@Override
					public void handle(MvcResult result) throws Exception {
						String content = result.getResponse().getContentAsString();
						System.out.println(content);
						JsonNode jsonNode = objectMapper.readTree(content);


						long pageFirstId = jsonNode.get(0).get("id").asLong();
						long pageLastId = jsonNode.get(jsonNode.size()-1).get("id").asLong();
						System.out.println("PaginationQuery: pageFirstId: " + pageFirstId + ", pageLastId: " + pageLastId);


						long lastId = jsonNode.get(jsonNode.size()-1).get("id").asLong();
						String lastUpdatedAt = jsonNode.get(jsonNode.size()-1).get("lastUpdatedAt").asText();
						System.out.println("PaginationQuery: lastId: " + lastId + ", lastUpdatedAt: " + lastUpdatedAt);
					}
				});
	}

	private String generateToken(long accountId) throws Exception {
		String lastUpdatedAt = mockMvc.perform(MockMvcRequestBuilders.post("/accounts/"+accountId+"/generate_token"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").isNumber())
				.andReturn().getResponse().getContentAsString();

		return lastUpdatedAt;
	}
}
