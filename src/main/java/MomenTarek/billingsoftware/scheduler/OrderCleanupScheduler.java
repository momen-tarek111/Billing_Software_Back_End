package MomenTarek.billingsoftware.scheduler;

import java.time.LocalDateTime;

import MomenTarek.billingsoftware.io.PaymentStatus;
import MomenTarek.billingsoftware.repository.OrderEntityRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class OrderCleanupScheduler {

    private final OrderEntityRepository orderRepository;

    @Scheduled(fixedRate = 600000) // every 10 minutes
    @Transactional
    public void deleteExpiredOrders() {

        LocalDateTime tenMinutesAgo = LocalDateTime.now().minusMinutes(10);

        orderRepository.deleteByPaymentDetails_PaymentStatusAndCreatedAtBefore(
                PaymentStatus.PENDING,
                tenMinutesAgo
        );
        System.out.println("Expired pending orders deleted");
    }
}
