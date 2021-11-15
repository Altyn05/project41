package com.amr.project.webapp.controller;

import com.amr.project.converter.ShopMapper;
import com.amr.project.model.entity.City;
import com.amr.project.model.entity.Country;
import com.amr.project.model.entity.Shop;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.CityService;
import com.amr.project.service.abstracts.CountryService;
import com.amr.project.service.abstracts.ShopService;
import com.amr.project.service.abstracts.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@RestController
public class NewShopController {
    private final ShopService shopService;
    private final UserService userService;
    private final CountryService countryService;
    private final CityService cityService;
    private final ShopMapper shopMapper;

    public NewShopController(ShopService shopService, UserService userService, CountryService countryService, CityService cityService, ShopMapper shopMapper) {
        this.shopService = shopService;
        this.userService = userService;
        this.countryService = countryService;
        this.cityService = cityService;
        this.shopMapper = shopMapper;

    }

    @PostMapping("/user/newShop")
    public ModelAndView createNewShop(Principal principal,
                                      @ModelAttribute("country") String location,
                                      @ModelAttribute("cityLocation") String cityLocation,
                                      @ModelAttribute("name") String name,
                                      @ModelAttribute("phone") String phone,
                                      @ModelAttribute("email") String email,
                                      @ModelAttribute("description") String description) {
        Shop shop = new Shop();
        shop.setEmail(email);
        shop.setDescription(description);
        shop.setPhone(phone);
        shop.setName(name);
        countryService.addNewCountry(new Country(location));
        cityService.addNewCity(new City(cityLocation,
                countryService.findByName(location)));
        shop.setLocation(countryService.findByName(location));
        shop.setCity(cityService.findByName(cityLocation));
        User user = userService.findUserByUsername(principal.getName());
        shop.setUser(user);
        shopService.addNewShop(shop);
        return new ModelAndView("redirect:/user");
    }




    @RequestMapping(value = "/updateShop", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView updateShop(Principal principal,
                                   @ModelAttribute("location") String location,
                                   @ModelAttribute("cityLocation") String cityLocation,
                                   @ModelAttribute("name") String name,
                                   @ModelAttribute("email") String email,
                                   @ModelAttribute("phone") String phone,
                                   @ModelAttribute("description") String description,
                                   @ModelAttribute("id") Long id) {

        Shop shop = shopService.findById(id);
        Country country = countryService.getByName(location);
        shop.setLocation(country);
        City city = cityService.getByName(cityLocation);
        shop.setCity(city);
        shop.setName(name);
        shop.setPhone(phone);
        shop.setEmail(email);
        shop.setDescription(description);
        User user = userService.findUserByUsername(principal.getName());
        user.addShop(shop);
        shopService.update(shop);

        return new ModelAndView( "redirect:/user");
    }
    @GetMapping( "/getOneNew/{id}")
    public String getOneNew(@PathVariable("id") Long id) {
       // System.out.println("work method GETONE");
        Shop shop = shopService.findById(id);
        System.out.println("getOne " + shop.toString());

        String var = String.format(
                "{ \"id\":\"%s\", \"name\":\"%s\", \"email\":\"%s\", \"phone\":\"%s\",\"location\":\"%s\",\"city\":\"%s\",\"description\":\"%s\"}",
                shop.getId(), shop.getName(), shop.getEmail(), shop.getPhone(),
                String.valueOf(shop.getLocation().getName()),String.valueOf(shop.getCity().getName()), shop.getDescription());
        return var;
    }

    @GetMapping("/deleteUserShop/{id}")
    public ModelAndView deleteShop(@PathVariable("id") Long id) {
        Shop shopDb = shopService.findById(id);
        shopService.deleteUserShop(shopDb);
        System.out.println(
                "work method delete"
        );
        return new ModelAndView("redirect:/user");
    }


}
