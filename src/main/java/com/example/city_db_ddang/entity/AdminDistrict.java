package com.example.city_db_ddang.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "admin_district")
public class AdminDistrict {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String state;

    @Column
    private String county;

    @Column
    private String city;

    @Column
    private String district;

    @Column
    private String village;

    @Column
    private Double x;

    @Column
    private Double y;

    public AdminDistrict(
        String state,
        String county,
        String city,
        String district,
        String village,
        Double x,
        Double y
    ) {
        this.state = state;
        this.county = county;
        this.city = city;
        this.district = district;
        this.village = village;
        this.x = x;
        this.y = y;
    }

}
