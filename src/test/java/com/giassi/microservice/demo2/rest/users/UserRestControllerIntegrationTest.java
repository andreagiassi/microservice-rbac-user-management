package com.giassi.microservice.demo2.rest.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.giassi.microservice.demo2.rest.users.dtos.UserDTO;
import com.giassi.microservice.demo2.rest.users.entities.User;
import com.giassi.microservice.demo2.rest.users.services.UserService;
import com.giassi.microservice.demo2.rest.users.services.UserTestHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserRestControllerIntegrationTest {

    final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    public void getUserPresentationList() throws Exception {
        UserDTO user1 = new UserDTO(UserTestHelper.getUserTestData(1L, "andrea", "Andrea",
                "Giassi", "andrea.test@gmail.com", "+3531122334455"));
        UserDTO user2 = new UserDTO(UserTestHelper.getUserTestData(2L, "marco", "Marco",
                "Verdi", "marco.test@gmail.com", "+3531122334466"));
        UserDTO user3 = new UserDTO(UserTestHelper.getUserTestData(3L, "franco", "Franco",
                "Rossi", "franco.test@gmail.com", "+3531122334477"));

        List<UserDTO> userList = Arrays.asList(user1, user2, user3);

        given(userService.getUserPresentationList()).willReturn(userList);

        mvc.perform(MockMvcRequestBuilders.get("/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Andrea"));
    }

    @Test
    public void getUserById() throws Exception {
        User user1 = UserTestHelper.getUserTestData(1L, "andrea", "Andrea",
                "Giassi", "andrea.test@gmail.com", "+3531122334455");

        given(userService.getUserById(1L)).willReturn(user1);

        Long userId = 1L;

        mvc.perform(MockMvcRequestBuilders.get("/users/" + userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("name").value("Andrea"));
    }

}
