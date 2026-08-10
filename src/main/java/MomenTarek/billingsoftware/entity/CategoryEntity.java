package MomenTarek.billingsoftware.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_code", unique = true, nullable = false)
    private String categoryCode;

    @Column(nullable = false)
    private String name;

    @Column(length = 500)
    private String description;

    private String bgColor;

    @Column(name = "image_url")
    private String imageUrl;
    @Column(name="image_public_id")
    private String imagePublicId;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
