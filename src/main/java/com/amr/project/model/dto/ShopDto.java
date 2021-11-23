package com.amr.project.model.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ShopDto {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String description;
    private CountryDto location;
    private CityDto cityDto;
    private List<ItemDto> items;
    private ImageDto logo;
    private double rating;
    private String username;
    private boolean isModerated;
    private boolean isModerateAccept;
    private String moderatedRejectReason;
    private boolean isPretendentToBeDeleted;
    private List<ReviewDto> reviews;

}
