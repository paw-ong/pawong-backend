package kr.co.pawong.pwbe.user.presentation.controller.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String username;
    private List<String> roles;

    public static UserResponse of(String username, List<String> roles) {
        return new UserResponse(username, roles);
    }
}
