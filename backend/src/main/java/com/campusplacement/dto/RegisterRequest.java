package com.campusplacement.dto;

import com.campusplacement.enums.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {

    private String fullName;

    private String email;

    private String password;

    private Role role;
}