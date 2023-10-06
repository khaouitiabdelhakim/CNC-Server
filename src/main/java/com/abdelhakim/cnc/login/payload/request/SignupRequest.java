package com.abdelhakim.cnc.login.payload.request;

import com.abdelhakim.cnc.login.models.ERole;
import jakarta.validation.constraints.*;
 
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String cin;
 
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    
    private ERole role;
    
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }

    public ERole getRole() {
        return role;
    }

    public void setRole(ERole role) {
        this.role = role;
    }
}
