package com.testehan.SpringMVCExperiments.service;

import com.testehan.SpringMVCExperiments.dto.ClubDTO;
import com.testehan.SpringMVCExperiments.model.Club;

import java.util.List;

public interface ClubService {

    List<ClubDTO> findAllClubs();

    Club save(ClubDTO clubDTO);

    ClubDTO findClubById(Long clubId);

    void updateClub(ClubDTO clubDTO);

    void delete(Long clubId);

    List<ClubDTO> searchClubs(String keyword);
}
