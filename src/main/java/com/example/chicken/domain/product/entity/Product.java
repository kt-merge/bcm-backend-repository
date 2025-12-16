package com.example.chicken.domain.product.entity;

import com.example.chicken.common.entity.BaseTimeEntity;
import com.example.chicken.common.entity.DeleteStatus;
import com.example.chicken.domain.auth.entity.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;


@Getter
@Entity
@Table(name = "products")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE products SET delete_status = 'DELETED' WHERE id = ?")
@SQLRestriction("delete_status = 'ACTIVATED'")
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 500, nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @JoinColumn(name = "category_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    private BigDecimal startPrice;

    private BigDecimal bidPrice;

    @Enumerated(EnumType.STRING)
    private BidStatus bidStatus;

    private Long bidCount;

    private LocalDateTime bidEndDate;

    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    @Enumerated(EnumType.STRING)
    private final DeleteStatus deleteStatus = DeleteStatus.ACTIVATED;

    @Column(length = 1000)
    private String thumbnail;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<ProductImage> images = new ArrayList<>();

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Builder
    private Product(String name,
                    String description,
                    Category category,
                    BigDecimal startPrice,
                    BigDecimal bidPrice,
                    Long bidCount,
                    LocalDateTime bidEndDate,
                    BidStatus bidStatus,
                    ProductStatus productStatus,
                    String thumbnail,
                    User user) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.startPrice = startPrice;
        this.bidPrice = bidPrice;
        this.bidCount = bidCount;
        this.bidStatus = bidStatus;
        this.bidEndDate = bidEndDate;
        this.productStatus = productStatus;
        this.thumbnail = thumbnail;
        this.user = user;
    }

    public boolean isBidPriceLowerThan(BigDecimal price) {
        return this.bidPrice.compareTo(price) < 0;
    }

    public boolean isBidInactive() {
        return this.bidStatus.equals(BidStatus.NOT_BIDDED);
    }

    public void updateBidPrice(BigDecimal price) {
        this.bidPrice = price;
    }

    public void updateImages(List<ProductImage> images) {
        this.images.clear();
        this.images.addAll(images);
    }

    public void updateProduct(String name,
                              String description,
                              Category category,
                              ProductStatus productStatus,
                              LocalDateTime bidEndDate,
                              String thumbnail,
                              List<ProductImage> productImages) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.bidEndDate = bidEndDate;
        this.productStatus = productStatus;
        this.thumbnail = thumbnail;
        this.updateImages(productImages);
    }

    public void incrementBidCount() {
        if (this.bidCount == null) {
            this.bidCount = 0L;
        }

        this.bidCount += 1;
    }

    public void activeBid() {
        this.bidStatus = BidStatus.BIDDED;
    }

    public void waitPayment() {
        this.bidStatus = BidStatus.PAYMENT_WAITING;
    }

    public void completeBid() {
        this.bidStatus = BidStatus.COMPLETED;
    }

}
