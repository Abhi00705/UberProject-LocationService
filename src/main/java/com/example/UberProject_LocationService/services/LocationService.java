package com.example.UberProject_LocationService.services;

import com.example.UberProject_LocationService.dto.DriverLocationResponseDto;

import java.util.List;

public interface LocationService {
    public Boolean SaveDriverLocation(String driverId, double latitude, double longitude);
    public List<DriverLocationResponseDto> GetNearByDriver(double latitude, double longitude);
}
