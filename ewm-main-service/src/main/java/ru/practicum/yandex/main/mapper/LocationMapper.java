package ru.practicum.yandex.main.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.yandex.main.dto.event.LocationDto;
import ru.practicum.yandex.main.model.Location;

@Component
@AllArgsConstructor
public class LocationMapper {

    public LocationDto toLocationDto(Location location) {
        return LocationDto.builder()
                .id(location.getId())
                .lat(location.getLat())
                .lon(location.getLon())
                .build();
    }

    public Location toLocation(LocationDto locationDto) {
        return Location.builder()
                .lat(locationDto.getLat())
                .lon(locationDto.getLon())
                .build();
    }
}