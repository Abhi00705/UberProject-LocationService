package com.example.UberProject_LocationService.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DriverLocationResponseDto {
    String name;
    Double latitude;
    Double longitude;
}
