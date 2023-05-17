package com.testehan.SpringMVCExperiments.mapper;

import com.testehan.SpringMVCExperiments.dto.ClubDTO;
import com.testehan.SpringMVCExperiments.model.Club;

import java.util.stream.Collectors;

public class ClubMapper {


    public static Club mapToClub(ClubDTO clubDTO) {
        return Club.builder()
                .id(clubDTO.getId())
                .title(clubDTO.getTitle())
                .photoUrl(clubDTO.getPhotoUrl())
                .content(clubDTO.getContent())
                .createdOn(clubDTO.getCreatedOn())
                .updatedOn(clubDTO.getUpdatedOn())
                .build();
    }

    public static ClubDTO mapToClubDTO(Club club) {
        return ClubDTO.builder()
                .id(club.getId())
                .title(club.getTitle())
                .photoUrl(club.getPhotoUrl())
                .content(club.getContent())
                .createdOn(club.getCreatedOn())
                .updatedOn(club.getUpdatedOn())
                .events(club.getEvents().stream().map((event)->EventMapper.mapToEventDTO(event)).collect(Collectors.toList()))
                .build();

    }
}
