package com.auth.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecoveryPasswordRequest {

    private String mailFrom;
    private String mailTo;
    private String subject;
    private String userName;
    private String tokenPassword;

}
