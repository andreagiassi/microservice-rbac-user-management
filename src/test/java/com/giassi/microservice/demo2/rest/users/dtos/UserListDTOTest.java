package com.giassi.microservice.demo2.rest.users.dtos;

import org.junit.Assert;
import org.junit.Test;

public class UserListDTOTest {

    @Test
    public void userListDTOTest() {
        UserListDTO userListDTO = new UserListDTO();

        Assert.assertNotNull(userListDTO.getUserList().size());
        Assert.assertEquals(0, userListDTO.getUserList().size());
    }

}
