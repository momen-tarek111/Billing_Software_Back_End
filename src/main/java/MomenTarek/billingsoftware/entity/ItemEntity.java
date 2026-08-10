package MomenTarek.billingsoftware.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String itemId;
    private String name;
    private String description;
    private BigDecimal price;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private String imgUrl;

    @Column(name="image_public_id")
    private String imgPublicId;

    @ManyToOne
    @JoinColumn(name = "category_id",nullable = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    private CategoryEntity category;


}
