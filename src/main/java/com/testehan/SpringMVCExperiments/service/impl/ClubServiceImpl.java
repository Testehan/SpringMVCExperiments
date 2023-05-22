package com.testehan.SpringMVCExperiments.service.impl;

import com.testehan.SpringMVCExperiments.dto.ClubDTO;
import com.testehan.SpringMVCExperiments.mapper.ClubMapper;
import com.testehan.SpringMVCExperiments.model.Club;
import com.testehan.SpringMVCExperiments.model.UserEntity;
import com.testehan.SpringMVCExperiments.repository.ClubRepository;
import com.testehan.SpringMVCExperiments.repository.UserRepository;
import com.testehan.SpringMVCExperiments.security.SecurityUtil;
import com.testehan.SpringMVCExperiments.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClubServiceImpl implements ClubService
{
    private final ClubRepository clubRepository;
    private final UserRepository userRepository;

    @Autowired
    public ClubServiceImpl(ClubRepository clubRepository,UserRepository userRepository){
         this.clubRepository = clubRepository;
         this.userRepository = userRepository;
    }

    @Override
    public List<ClubDTO> findAllClubs() {
        List<Club> allClubs =  clubRepository.findAll();
        return allClubs.stream().map((club)-> ClubMapper.mapToClubDTO(club)).collect(Collectors.toList());
    }

    @Override
    public Club save(ClubDTO clubDTO) {
        String username = SecurityUtil.getSessionUser();    // this actually returns the email from i've seen during debug
        UserEntity userEntity = userRepository.findByEmail(username);

        Club club = ClubMapper.mapToClub(clubDTO);
        club.setCreatedByUser(userEntity);
        return clubRepository.save(club);
    }

    @Override
    public ClubDTO findClubById(Long clubId) {
        return ClubMapper.mapToClubDTO(clubRepository.findById(clubId).get());
    }

    @Override
    public void updateClub(ClubDTO clubDTO) {
        String username = SecurityUtil.getSessionUser();    // this actually returns the email from i've seen during debug
        UserEntity userEntity = userRepository.findByEmail(username);

        Club club = ClubMapper.mapToClub(clubDTO);
        club.setCreatedByUser(userEntity);
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
