package com.example.city_db_ddang;

import com.example.city_db_ddang.repository.AdminDistrictRepository;
import com.example.city_db_ddang.service.ExcelReadService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
class CityDbDdangApplicationTests {

    @Autowired
    AdminDistrictRepository adminDistrictRepository;

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

}
