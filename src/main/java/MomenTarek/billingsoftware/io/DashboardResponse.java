package MomenTarek.billingsoftware.io;

import lombok.*;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardResponse {
    private Double todaySales;

    private Long todayOrderCount;
    private List<OrderResponse> recentOrders;
}
