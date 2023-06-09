package com.testehan.SpringMVCExperiments.controller;

import com.testehan.SpringMVCExperiments.dto.ClubDTO;
import com.testehan.SpringMVCExperiments.model.Club;
import com.testehan.SpringMVCExperiments.model.UserEntity;
import com.testehan.SpringMVCExperiments.security.SecurityUtil;
import com.testehan.SpringMVCExperiments.service.ClubService;
import com.testehan.SpringMVCExperiments.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ClubController {

    private final ClubService  clubService;
    private final UserService userService;

    @Autowired
    public ClubController(ClubService clubService, UserService userService){

        this.clubService = clubService;
        this.userService = userService;
    }

    @GetMapping("/clubs")
    public String getClubs(Model model)
    {
        UserEntity userEntity = new UserEntity();
        List<ClubDTO> clubs = clubService.findAllClubs();
        String userName = SecurityUtil.getSessionUser();
        if (userName !=null){
            userEntity = userService.findByEmail(userName);
        }

        model.addAttribute("user",userEntity);
        model.addAttribute("clubs",clubs);
        return "clubs-list";
    }

    @GetMapping("/clubs/new")
    public String getClubForm(Model model)
    {
        Club club = new Club();
        model.addAttribute("club",club);
        return "clubs-create";
    }

    @PostMapping("/clubs/new")
    public String postClub(@Valid @ModelAttribute("club") ClubDTO clubDto,
                           BindingResult result,
                           Model model)
    {
        if (result.hasErrors()){
            model.addAttribute("club",clubDto);
            return "clubs-create";
        }
        clubService.save(clubDto);

        return "redirect:/clubs";
    }

    @GetMapping("/clubs/{clubId}/delete")
    public String deleteClub(@PathVariable("clubId")Long clubId) {
        clubService.delete(clubId);
        return "redirect:/clubs";
    }

    @GetMapping("/clubs/{clubId}")
    public String clubDetail(@PathVariable("clubId") long clubId, Model model) {
        UserEntity userEntity = new UserEntity();
        ClubDTO clubDto = clubService.findClubById(clubId);
        String userName = SecurityUtil.getSessionUser();
        if (userName !=null){
            userEntity = userService.findByEmail(userName);
        }

        model.addAttribute("user",userEntity);
        model.addAttribute("club", clubDto);
        return "clubs-detail";
    }

    @GetMapping("/clubs/{clubId}/edit")
    public String getEditClubForm(@PathVariable("clubId")Long clubId,
                                    Model model)
    {
        ClubDTO club = clubService.findClubById(clubId);
        model.addAttribute("club",club);

        return "clubs-edit";
    }

    @PostMapping("/clubs/{clubId}/edit")
    public String postEditClubForm(@PathVariable("clubId")Long clubId,
                                   @Valid @ModelAttribute("club") ClubDTO clubDTO,  // @Valid will validate object based on annotations from ClubDTO class
                                   BindingResult result,Model model)
    {
        if (result.hasErrors()){
            model.addAttribute("club", clubDTO);
            return "clubs-edit";
        }
        clubDTO.setId(clubId);
        clubService.updateClub(clubDTO);

        return "redirect:/clubs";
    }

    @GetMapping("/clubs/search")
    public String searchClub(@RequestParam(value = "query") String keyword, Model model) {
        List<ClubDTO> clubs = clubService.searchClubs(keyword);
        model.addAttribute("clubs", clubs);
        return "clubs-list";
    }
}
