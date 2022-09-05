package com.pvdong.lesson3.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "horse_account")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HorseAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "account_id", nullable = false)
    private Integer account_id;

    @Column(name = "horse_id", nullable = false)
    private Integer horse_id;

    @Column(name = "archive", nullable = false, columnDefinition = "bit(1) default 1")
    private Boolean archive;

    public HorseAccount(Integer account_id, Integer horse_id, Boolean archive) {
        this.account_id = account_id;
        this.horse_id = horse_id;
        this.archive = archive;
    }
}
