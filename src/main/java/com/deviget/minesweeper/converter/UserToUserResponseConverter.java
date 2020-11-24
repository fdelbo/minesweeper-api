package com.deviget.minesweeper.converter;

import com.deviget.minesweeper.model.api.UserResponse;
import com.deviget.minesweeper.model.document.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
class UserToUserResponseConverter implements Converter<User, UserResponse> {

    @Override
    public UserResponse convert(User source) {
        var target = new UserResponse();

        target.setId(source.getId());
        target.setName(source.getName());
        target.setLastName(source.getLastName());
        target.setUserName(source.getUserName());

        return target;
    }
}
