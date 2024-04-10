package org.samarBg.controllers;

import org.samarBg.models.UserEntity;
import org.samarBg.service.serviceImpl.OfferServiceImpl;
import org.samarBg.service.serviceImpl.UserServiceImpl;
import org.samarBg.views.OfferViewModel;
import org.samarBg.views.UserProfileViewModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
@Controller
public class UserProfileController {

    private final UserServiceImpl userService;
    private final OfferServiceImpl offerService;



    public UserProfileController(UserServiceImpl userService,
                                 OfferServiceImpl offerService) {
        this.userService = userService;
        this.offerService = offerService;
    }

    @GetMapping("/user-detail")
    public String showUserDetail(Model model) {
        String username = userService.getCurrentUser().getUsername();
        List<OfferViewModel> userOffersPage = offerService.getAllOffersForUser(username);

        UserProfileViewModel userProfileViewModel = new UserProfileViewModel();
        userProfileViewModel.setUserOffers(userOffersPage); // Получаваме списъка с обяви от текущата страница

        model.addAttribute("userProfile", userProfileViewModel);
        return "user-detail";
    }


    @GetMapping("/userProfileDetail")
    public String showUserProfile(Model model) {
        // Извличане на атрибута "user" от модела
        UserProfileViewModel userProfileViewModel = (UserProfileViewModel) model.getAttribute("user");
        model.addAttribute("user",userProfileViewModel);
        return "userProfileDetail";
    }

    @GetMapping("/getProfileDetail/{username}")
    public String viewUserProfileDetail(@PathVariable String username,
                                        RedirectAttributes redirectAttributes) {
        // Намираме потребителя по потребителското име
        UserEntity user = userService.findUserByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Потребителят не е намерен."));

        UserProfileViewModel userProfileViewModel = new UserProfileViewModel();
        userProfileViewModel.setUserName(user.getUsername());
        userProfileViewModel.setRealName(user.getRealName());
        userProfileViewModel.setEmail(user.getEmail());
        userProfileViewModel.setPhone(user.getPhone());
        userProfileViewModel.setCity(user.getCity());
        userProfileViewModel.setProfileImageUrl(user.getImageUrl());
        userProfileViewModel.setLastOnline(user.getLastOnline());

        List<OfferViewModel> userOffers = offerService.getAllOffersForUser(username);
        userProfileViewModel.setUserOffers(userOffers);

        // Пренасочваме с добавяне на атрибут "user"
        redirectAttributes.addFlashAttribute("user", userProfileViewModel);
        return "redirect:/userProfileDetail";
    }

}
