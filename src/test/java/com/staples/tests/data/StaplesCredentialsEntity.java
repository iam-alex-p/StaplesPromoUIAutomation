package com.staples.tests.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaplesCredentialsEntity {
    private String username;
    private String password;
    private boolean isValidUsername;
    private boolean isValidPassword;
    private boolean isAuthentication;
    private String description;
}
