package com.example.deploydocker.web.res.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BadRequestAlertException extends IllegalArgumentException {
    private String serialVersionUID;
    private  String entityName;
    private  String errorKey;
}
