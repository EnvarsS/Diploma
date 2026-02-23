package org.envycorp.diploma.service;

import org.envycorp.diploma.model.DTO.response.AnalyticResponseDto;
import org.envycorp.diploma.model.DTO.response.PathResponseDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnalyticService {
    private final List<AnalyticResponseDto> a_staraAalyticList = new ArrayList<>();
    private final List<AnalyticResponseDto> ALTanalyticList = new ArrayList<>();

    public void a_starAddAnalytic(AnalyticResponseDto analyticResponseDto) {
        a_staraAalyticList.add(analyticResponseDto);
    }
    public List<AnalyticResponseDto> getA_starAnalyticList() {
        return a_staraAalyticList;
    }

    public void ALTAddAnalytic(AnalyticResponseDto analyticResponseDto) {
        ALTanalyticList.add(analyticResponseDto);
    }
    public List<AnalyticResponseDto> getALTAnalyticList() {
        return ALTanalyticList;
    }
}
