package MomenTarek.billingsoftware.io;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemResponse {
    private String itemId;
    private String name;
    private BigDecimal price;
    private String categoryId;
    private String description;
    private String categoryName;
    private String imgUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
