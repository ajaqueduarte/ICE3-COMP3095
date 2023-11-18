package ca.gbc.inventoryservice;

import ca.gbc.inventoryservice.dto.InventoryRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class InventoryServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	// Mocks for any other services InventoryController might interact with

	@BeforeEach
	public void setup() {
		// Setup test data in the database or mocks for other services
	}

	@Test
	public void testIsInStock() throws Exception {
		List<InventoryRequest> inventoryRequests = new ArrayList<>();
		InventoryRequest inventoryRequest = new InventoryRequest();
		inventoryRequest.setSkuCode("someSkuCode");
		inventoryRequest.setQuantity(10);
		inventoryRequests.add(inventoryRequest);

		mockMvc.perform(post("/api/inventory")
						.contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(inventoryRequests)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].skuCode").value("someSkuCode"))
				.andExpect(jsonPath("$[0].sufficientStock").isBoolean());
	}


	// Helper method to convert objects to JSON strings
	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
