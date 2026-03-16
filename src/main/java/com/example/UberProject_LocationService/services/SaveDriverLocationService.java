package com.example.UberProject_LocationService.services;

import com.example.UberProject_LocationService.dto.DriverLocationResponseDto;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SaveDriverLocationService implements LocationService{
    private static final double SEARCH_RADIUS = 1.0;

    private static final String DRIVER_GEO_OPS_KEY = "drivers";
    private StringRedisTemplate stringRedisTemplate;
    public SaveDriverLocationService(StringRedisTemplate stringRedisTemplate){
        this.stringRedisTemplate=stringRedisTemplate;
    }

    @Override
    public Boolean SaveDriverLocation(String driverId, Double latitude, Double longitude) {
        try{
            GeoOperations<String, String> geoOps = stringRedisTemplate.opsForGeo();
            geoOps.add(DRIVER_GEO_OPS_KEY,
                    new RedisGeoCommands.GeoLocation<String>(driverId, new Point( latitude, longitude)));
            return true;
        } catch (Exception e) {
            System.out.println("error message: "+e.getMessage());
            return false;
        }
    }

    @Override
    public List<DriverLocationResponseDto> GetNearByDriver(Double latitude, Double longitude) {

            GeoOperations<String, String> geoOps = stringRedisTemplate.opsForGeo();
            Distance radius = new Distance(SEARCH_RADIUS, Metrics.KILOMETERS);
            Circle areaWithIn = new Circle(new Point(latitude, longitude), radius);
            RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs()
                .includeCoordinates();

        GeoResults<RedisGeoCommands.GeoLocation<String>> results = geoOps.radius(DRIVER_GEO_OPS_KEY, areaWithIn, args);
            List<DriverLocationResponseDto> drivers = new ArrayList<>();
            DriverLocationResponseDto driverLocationResponseDto;
            if(results != null){
                for(GeoResult<RedisGeoCommands.GeoLocation<String>> result: results){
                    String driverId= result.getContent().getName();
                    Point point = result.getContent().getPoint();
                    driverLocationResponseDto = DriverLocationResponseDto.builder()
                            .name(driverId)
                            .latitude(point.getY())
                            .longitude(point.getX())
                            .build();
                    drivers.add(driverLocationResponseDto);
                }
            }
            return drivers;

    }


}
