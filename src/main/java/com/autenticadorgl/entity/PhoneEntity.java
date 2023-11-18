package com.autenticadorgl.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "phones")
public class PhoneEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, nullable = false)
    private String id;

    @Column(nullable = false)
    private long number;

    @Column(nullable = false)
    private int citycode;

    @Column(nullable = false)
    private String countrycode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    /**
     *
     * @param user
     * @param number
     * @param citycode
     * @param countrycode
     */
    public PhoneEntity(UserEntity user, long number, int citycode, String countrycode) {
        this.user = user;
        this.number = number;
        this.citycode = citycode;
        this.countrycode = countrycode;
    }
}
