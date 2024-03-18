package org.samarBg.model.entities;


import org.samarBg.model.entities.enums.AccessoriesEnum;
import org.samarBg.model.entities.enums.CityEnum;
import org.samarBg.model.entities.enums.OfferCategoryEnum;
import javax.persistence.*;
import javax.validation.constraints.Size;
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
    @Size(max = 350)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccessoriesEnum accessoriesCategory;

    @OneToMany(mappedBy = "accessoryOffer", cascade = CascadeType.ALL)
    private List<OfferImageEntity> images;


    @Column
    private int offerViewCounter;


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
        if (images.size() > 5) {
            throw new IllegalArgumentException("Максималният брой снимки за обява е 5");
        }
        this.images = images;
        return this;
    }

    public int getOfferViewCounter() {
        return offerViewCounter;
    }

    public AccessoryOfferEntity setOfferViewCounter(int offerViewCounter) {
        this.offerViewCounter = offerViewCounter;
        return this;
    }


    @Override
    public String toString() {
        return "AccessoryOfferEntity{" +
                "offerName='" + offerName + '\'' +
                ", category=" + category +
                ", authorName='" + authorName + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", phone='" + phone + '\'' +
                ", city=" + city +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", accessoriesCategory=" + accessoriesCategory +
                ", images=" + images +
                ", offerViewCounter=" + offerViewCounter +
                ", id=" + id +
                ", created=" + created +
                ", modified=" + modified +
                '}';
    }
}
