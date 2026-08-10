package MomenTarek.billingsoftware.io;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemRequest {
    private String name;
    private BigDecimal price;
    private String categoryId;
    private String description;
}
