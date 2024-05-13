package com.example.city_db_ddang.service;

import com.example.city_db_ddang.entity.AdminDistrict;
import com.example.city_db_ddang.repository.AdminDistrictRepository;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j(topic = "AdminDistrictService")
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AdminDistrictService {

    private final static String excelDirectory = "src/main/resources/AdminDistrictExcel.xlsx";

    private final AdminDistrictRepository adminDistrictRepository;

    @Transactional
    public void createAdminDistrict() {

        try (FileInputStream file = new FileInputStream(excelDirectory)) {

            List<AdminDistrict> townList = new ArrayList<>();
            Workbook workbook = WorkbookFactory.create(file);
            int sheets = workbook.getNumberOfSheets();

            for (int i = 0; i < sheets; i++) {
                Sheet sheet = workbook.getSheetAt(i);

                for (int j = 1; j < sheet.getPhysicalNumberOfRows(); j++) {
                    Row row = sheet.getRow(j);

                    townList.add(new AdminDistrict(
                        row.getCell(0).getStringCellValue(),
                        row.getCell(1).getStringCellValue(),
                        row.getCell(2).getStringCellValue(),
                        row.getCell(3).getStringCellValue(),
                        row.getCell(4).getStringCellValue(),
                        row.getCell(5).getNumericCellValue(),
                        row.getCell(6).getNumericCellValue()
                    ));
                }

            }

            adminDistrictRepository.saveAll(townList);

        } catch (IOException | EncryptedDocumentException e) {
            log.error(e.getMessage());
        }

    }

}
