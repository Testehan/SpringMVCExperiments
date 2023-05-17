package com.testehan.SpringMVCExperiments.service.impl;

import com.testehan.SpringMVCExperiments.dto.ClubDTO;
import com.testehan.SpringMVCExperiments.mapper.ClubMapper;
import com.testehan.SpringMVCExperiments.model.Club;
import com.testehan.SpringMVCExperiments.repository.ClubRepository;
import com.testehan.SpringMVCExperiments.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClubServiceImpl implements ClubService
{
    private final ClubRepository clubRepository;

    @Autowired
    public ClubServiceImpl(ClubRepository clubRepository){
         this.clubRepository = clubRepository;
    }

    @Override
    public List<ClubDTO> findAllClubs() {
        List<Club> allClubs =  clubRepository.findAll();
        return allClubs.stream().map((club)-> ClubMapper.mapToClubDTO(club)).collect(Collectors.toList());
    }

    @Override
    public Club save(ClubDTO clubDTO) {
        return clubRepository.save(ClubMapper.mapToClub(clubDTO));
    }

    @Override
    public ClubDTO findClubById(Long clubId) {
        return ClubMapper.mapToClubDTO(clubRepository.findById(clubId).get());
    }

    @Override
    public void updateClub(ClubDTO clubDTO) {
        Club club = ClubMapper.mapToClub(clubDTO);
        clubRepository.save(club);
    }

    @Override
    public void delete(Long clubId) {
        clubRepository.deleteById(clubId);
    }

    @Override
    public List<ClubDTO> searchClubs(String keyword) {
        List<Club> clubsFound = clubRepository.searchClubs(keyword);
        return clubsFound.stream().map((club)-> ClubMapper.mapToClubDTO(club)).collect(Collectors.toList());
    }

}
