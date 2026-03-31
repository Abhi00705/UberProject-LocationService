package com.example.UberProject_LocationService.Controllers;

import com.example.UberProject_LocationService.dto.DriverLocationResponseDto;
import com.example.UberProject_LocationService.dto.NearByDriverRequestDto;
import com.example.UberProject_LocationService.dto.SaveDriverLocationRequestDto;
import com.example.UberProject_LocationService.services.SaveDriverLocationService;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/location")
public class LocationController {

    private static final double SEARCH_RADIUS = 1.0;
    private static final String DRIVER_GEO_OPS_KEY = "drivers";
    private SaveDriverLocationService saveDriverLocationService;
    private StringRedisTemplate stringRedisTemplate;
    public LocationController(StringRedisTemplate stringRedisTemplate, SaveDriverLocationService saveDriverLocationService){
        this.stringRedisTemplate=stringRedisTemplate;
        this.saveDriverLocationService=saveDriverLocationService;
    }

    @PostMapping("/drivers")
    public ResponseEntity<Boolean> SaveDriverLocation(@RequestBody SaveDriverLocationRequestDto saveDriverLocationRequestDto){
        try{
            boolean response = saveDriverLocationService.SaveDriverLocation(saveDriverLocationRequestDto.getDriverId(), saveDriverLocationRequestDto.getLatitude(), saveDriverLocationRequestDto.getLongitude());
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        }catch (Exception e ){
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/nearby/drivers")
    public ResponseEntity<List<DriverLocationResponseDto>> GetNearByDrivers(@RequestBody NearByDriverRequestDto nearByDriverRequestDto){
      try{
          List<DriverLocationResponseDto> drivers = saveDriverLocationService.GetNearByDriver(nearByDriverRequestDto.getLatitude(), nearByDriverRequestDto.getLongitude());
          return new ResponseEntity<>(drivers, HttpStatus.OK);
      } catch (Exception e) {
          System.out.println("error: "+ e.getMessage());
          return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }
}
