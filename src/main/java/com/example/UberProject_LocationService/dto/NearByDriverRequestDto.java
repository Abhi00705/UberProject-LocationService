package com.example.UberProject_LocationService.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NearByDriverRequestDto {
    Double latitude;
    Double longitude;
}
