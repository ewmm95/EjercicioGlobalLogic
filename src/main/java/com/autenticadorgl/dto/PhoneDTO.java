package com.autenticadorgl.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PhoneDTO {

    private long number;
    private int citycode;
    private String countrycode;

    public PhoneDTO(long number, int citycode, String countrycode) {
        this.number = number;
        this.citycode = citycode;
        this.countrycode = countrycode;
    }
}
