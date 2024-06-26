package com.example.city_db_ddang;

import com.example.city_db_ddang.service.AdminDistrictService;
import com.example.city_db_ddang.service.TownService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class CityDbDdangApplicationTests {

    @Autowired
    TownService townService;

    @Autowired
    AdminDistrictService adminDistrictService;

    @Test
    @Order(1)
    @Rollback(false)
    @DisplayName("townList 엑셀 파일 DB에 저장")
    void saveTownListDB() {
        //given, when,then
        adminDistrictService.createAdminDistrict();
    }

    @Test
    @Order(2)
    @Rollback(false)
    @DisplayName("town DB에 저장")
    void saveTownDB() throws JsonProcessingException {
        //given, when, then
        townService.createTown();
    }

}
