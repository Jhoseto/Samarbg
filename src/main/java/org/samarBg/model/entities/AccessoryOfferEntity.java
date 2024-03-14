package org.samarBg.model.entities;

import jakarta.persistence.*;
import org.samarBg.model.entities.enums.AccessoriesEnum;
import org.samarBg.model.entities.enums.CityEnum;
import org.samarBg.model.entities.enums.OfferCategoryEnum;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "accessory_offers")
public class AccessoryOfferEntity extends BaseEntity {

    @Column(nullable = false)
    private String offerName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OfferCategoryEnum category;

    @Column(nullable = false)
    private String authorName;

    @Column
    private String imageUrl;
    @Column(nullable = false)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CityEnum city;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccessoriesEnum accessoriesCategory;

    @OneToMany(mappedBy = "accessoryOffer", cascade = CascadeType.ALL)
    private List<OfferImageEntity> images;

    public String getOfferName() {
        return offerName;
    }

    public AccessoryOfferEntity setOfferName(String offerName) {
        this.offerName = offerName;
        return this;
    }

    public OfferCategoryEnum getCategory() {
        return category;
    }

    public AccessoryOfferEntity setCategory(OfferCategoryEnum category) {
        this.category = category;
        return this;
    }

    public String getAuthorName() {
        return authorName;
    }

    public AccessoryOfferEntity setAuthorName(String authorName) {
        this.authorName = authorName;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public AccessoryOfferEntity setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public AccessoryOfferEntity setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public CityEnum getCity() {
        return city;
    }

    public AccessoryOfferEntity setCity(CityEnum city) {
        this.city = city;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public AccessoryOfferEntity setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public AccessoryOfferEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public AccessoriesEnum getAccessoriesCategory() {
        return accessoriesCategory;
    }

    public AccessoryOfferEntity setAccessoriesCategory(AccessoriesEnum accessoriesCategory) {
        this.accessoriesCategory = accessoriesCategory;
        return this;
    }

    public List<OfferImageEntity> getImages() {
        return images;
    }

    public AccessoryOfferEntity setImages(List<OfferImageEntity> images) {
        this.images = images;
        return this;
    }
}
