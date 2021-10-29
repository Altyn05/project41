package com.amr.project.webapp.controller;

import com.amr.project.model.dto.ShowMainPageDTO;
import com.amr.project.service.abstracts.ShowMainPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainPageController {
    private static final int[] PAGE_SIZE = new int[]{1, 2, 3, 4, 5, 6, 8, 10};

    private final ShowMainPageService showMainPageService;

    @Autowired
    public MainPageController(ShowMainPageService showMainPageService) {
        this.showMainPageService = showMainPageService;
    }

    @GetMapping
    public String showMainPage(
            Model model,
            @RequestParam(value = "searchName", defaultValue = "") String searchName,
            @RequestParam(value = "itemPage", defaultValue = "0") int itemPage,
            @RequestParam(value = "itemSize", defaultValue = "4") int itemSize,
            @RequestParam(value = "shopPage", defaultValue = "0") int shopPage,
            @RequestParam(value = "shopSize", defaultValue = "6") int shopSize
    ) {
        if (itemPage < 0) {
            itemPage = 0;
        }
        if (shopPage < 0) {
            shopPage = 0;
        }
        Pageable itemPageable = PageRequest.of(itemPage, itemSize);
        Pageable shopPageable = PageRequest.of(shopPage, shopSize);
        ShowMainPageDTO showMainPageDTO;
        
        if (searchName.isBlank()){
            showMainPageDTO = showMainPageService
                    .show(itemPageable, shopPageable);
        } else {
            showMainPageDTO = showMainPageService
                    .showSearch(searchName, itemPageable, shopPageable);
        }
        model.addAttribute("mainPageDto", showMainPageDTO)
                .addAttribute("searchName", searchName)
                .addAttribute("pageSizes", PAGE_SIZE);
        return "index";
    }

    @GetMapping("/category/{id}")
    public String showMainCategory(
            Model model,
            @PathVariable Long id,
            @RequestParam(value = "itemPage", defaultValue = "0") int itemPage,
            @RequestParam(value = "itemSize", defaultValue = "4") int itemSize,
            @RequestParam(value = "shopPage", defaultValue = "0") int shopPage,
            @RequestParam(value = "shopSize", defaultValue = "4") int shopSize
    ) {
        Pageable itemPageable = PageRequest.of(itemPage, itemSize);
        Pageable shopPageable = PageRequest.of(shopPage, shopSize);
        model.addAttribute("mainPageDto", showMainPageService.findItemsByCategory(id, itemPageable, shopPageable))
                .addAttribute("pageSizes", PAGE_SIZE);
        return "index";
    }
}
