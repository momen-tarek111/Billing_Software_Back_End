package MomenTarek.billingsoftware.io;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponse {
    private String categoryCode;
    private String name;
    private String description;
    private String bgColor;
    private String imageUrl;
    private Integer items;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
