package com.example.city_db_ddang.service;

import com.example.city_db_ddang.entity.AdminDistrict;
import com.example.city_db_ddang.entity.Town;
import com.example.city_db_ddang.repository.AdminDistrictRepository;
import com.example.city_db_ddang.repository.TownRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class TownService {

    private final static Double DEFAULT_X = 111.35;
    private final static Double DEFAULT_Y = 88.80;
    private final static Double DEFAULT_DISTANCE = 5.0;
    private final TownRepository townRepository;
    private final AdminDistrictRepository adminDistrictRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public void createTown() throws JsonProcessingException {
        List<AdminDistrict> adminDistricts = adminDistrictRepository.findAll();

        for (AdminDistrict adminDistrict : adminDistricts) {
            List<Long> idList = new ArrayList<>();
            String name = getTownName(adminDistrict);

            for (AdminDistrict comparison : adminDistricts) {
                double x = Math.pow((adminDistrict.getX() - comparison.getX()) * DEFAULT_X, 2.0);
                double y = Math.pow((adminDistrict.getY() - comparison.getY()) * DEFAULT_Y, 2.0);

                double distance = Math.sqrt(x + y);

                if (distance < DEFAULT_DISTANCE) {
                    idList.add(comparison.getId());
                }
            }

            String neighborIdList = objectMapper.writeValueAsString(idList);
            Town town = new Town(name, neighborIdList);
            townRepository.save(town);
        }
    }

    public String getTownName(AdminDistrict adminDistrict) {
        StringBuilder name = new StringBuilder(adminDistrict.getState());

        if (!adminDistrict.getCounty().isEmpty()) {
            name.append(" ").append(adminDistrict.getCounty());
        }

        if (!adminDistrict.getCity().isEmpty()) {
            name.append(" ").append(adminDistrict.getCity());
        }

        if (!adminDistrict.getDistrict().isEmpty()) {
            name.append(" ").append(adminDistrict.getDistrict());
        }

        if (!adminDistrict.getVillage().isEmpty()) {
            name.append(" ").append(adminDistrict.getVillage());
        }

        return String.valueOf(name);
    }

}
