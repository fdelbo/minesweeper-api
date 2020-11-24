package com.deviget.minesweeper.converter;

import com.deviget.minesweeper.model.document.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserToUserResponseConverterTest {

    @Test
    void givenAUser_thenReturnAUserResponse() {
        //Given
        var converter = new UserToUserResponseConverter();
        var user = createUser();

        //When
        var converted = converter.convert(user);

        //Then
        assertNotNull(converted);
        assertEquals(user.getName(), converted.getName());
        assertEquals(user.getLastName(), converted.getLastName());
        assertEquals(user.getUserName(), converted.getUserName());
    }

    private User createUser() {
        return new User("name-test", "last-name-test", "user-name");
    }
}
