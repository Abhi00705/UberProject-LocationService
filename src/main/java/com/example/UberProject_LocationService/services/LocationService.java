package com.example.UberProject_LocationService.services;

import com.example.UberProject_LocationService.dto.DriverLocationResponseDto;

import java.util.List;

public interface LocationService {
    public Boolean SaveDriverLocation(String driverId, Double latitude, Double longitude);
    public List<DriverLocationResponseDto> GetNearByDriver(Double latitude, Double longitude);
}
