package com.example.city_db_ddang;

import com.example.city_db_ddang.repository.AdminDistrictRepository;
import com.example.city_db_ddang.service.ExcelReadService;
import com.example.city_db_ddang.service.TownService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
class CityDbDdangApplicationTests {

    @Autowired
    AdminDistrictRepository adminDistrictRepository;

    @Autowired
    TownService townService;

    @Test
    void contextLoads() {
    }

    @Test
    @Rollback(false)
    @DisplayName("townList 엑셀 파일 DB에 저장")
    void saveTownListDB() {
        //given
        ExcelReadService excelReadService = new ExcelReadService(adminDistrictRepository);
        //when,then
        excelReadService.createAdminDistrict();
    }

    @Test
    @Rollback(false)
    @DisplayName("town DB에 저장")
    void saveTownDB() throws JsonProcessingException {
        //given, when, then
        townService.createTown();
    }

}
