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

import java.time.LocalDate;
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
                .andExpect(jsonPath("$.userList[0].name").value("Andrea"))
                .andExpect(jsonPath("$.userList[0].surname").value("Giassi"))
                .andExpect(jsonPath("$.userList[0].contactDTO.email").value("andrea.test@gmail.com"))
                .andExpect(jsonPath("$.userList[0].username").value("andrea"))
                .andExpect(jsonPath("$.userList[0].contactDTO.phone").value("+3531122334455"))
                .andExpect(jsonPath("$.userList[1].name").value("Marco"))
                .andExpect(jsonPath("$.userList[1].surname").value("Verdi"))
                .andExpect(jsonPath("$.userList[1].contactDTO.email").value("marco.test@gmail.com"))
                .andExpect(jsonPath("$.userList[1].username").value("marco"))
                .andExpect(jsonPath("$.userList[1].contactDTO.phone").value("+3531122334466"))
                .andExpect(jsonPath("$.userList[2].name").value("Franco"))
                .andExpect(jsonPath("$.userList[2].surname").value("Rossi"))
                .andExpect(jsonPath("$.userList[2].contactDTO.email").value("franco.test@gmail.com"))
                .andExpect(jsonPath("$.userList[2].username").value("franco"))
                .andExpect(jsonPath("$.userList[2].contactDTO.phone").value("+3531122334477"));
    }

    @Test
    public void getUserById() throws Exception {
        User user1 = UserTestHelper.getUserTestData(1L, "andrea", "Andrea",
                "Giassi", "andrea.test@gmail.com", "+3531122334455");

        user1.setBirthDate(LocalDate.of(1977, 8, 14));

        given(userService.getUserById(1L)).willReturn(user1);

        Long userId = 1L;

        mvc.perform(MockMvcRequestBuilders.get("/users/" + userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("name").value("Andrea"))
                .andExpect(jsonPath("surname").value("Giassi"))
                .andExpect(jsonPath("contactDTO.email").value("andrea.test@gmail.com"))
                .andExpect(jsonPath("username").value("andrea"))
                .andExpect(jsonPath("birthDate").value("1977-08-14"))
                .andExpect(jsonPath("contactDTO.phone").value("+3531122334455"));
    }

}
