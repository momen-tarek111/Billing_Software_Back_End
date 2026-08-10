package MomenTarek.billingsoftware.controller;


import MomenTarek.billingsoftware.io.DashboardResponse;
import MomenTarek.billingsoftware.io.OrderResponse;
import MomenTarek.billingsoftware.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class DashboardController {

    private final OrderService orderService;

    @GetMapping
    public DashboardResponse getDashboardDate(){
        LocalDate today=LocalDate.now();

        Double todaySale=orderService.sumSalesByDate(today);
        Long toadyOrderCount=orderService.countByOrderDate(today);
        List<OrderResponse> recentOrders=orderService.findRecentOrders();

        return new DashboardResponse(
            todaySale!=null?todaySale:0.0,
            toadyOrderCount!=null?toadyOrderCount:0,
            recentOrders
        );
    }
}
