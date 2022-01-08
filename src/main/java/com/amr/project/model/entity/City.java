package com.amr.project.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@ToString
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "cities")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    @ToString.Exclude
    private Country country;

    @OneToMany(
            mappedBy = "city",
            cascade = CascadeType.ALL
    )
    @JsonIgnore
    @ToString.Exclude
    private List<Address> addresses;

    @OneToMany(mappedBy = "city", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<Shop> shops;

    public City(String name, Country country) {
        this.name = name;
        this.country = country;
    }

    public City(String name) {
        this.name = name;
    }

    public City(City byName) {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return Objects.equals(name, city.name) && Objects.equals(country, city.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, country);
    }
}
