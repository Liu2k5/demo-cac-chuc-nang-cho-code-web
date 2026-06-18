package liu.democacchucnangchocodeweb.api;

import java.util.concurrent.CompletableFuture;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import liu.democacchucnangchocodeweb.entity.Order;
import liu.democacchucnangchocodeweb.service.EmailService;
import liu.democacchucnangchocodeweb.service.OrderService;
import lombok.RequiredArgsConstructor;
import vn.payos.PayOS;
import vn.payos.model.webhooks.WebhookData;

@RequiredArgsConstructor
@RestController
public class PayOsWebhookApi {

    private final PayOS payOS;
    private final OrderService orderService;
    private final EmailService emailService;

    private void orderConfirmed(WebhookData paymentData) {
        Long orderId = paymentData.getOrderCode();
        Order order = orderService.getOrderById(orderId);
        // orderService.doWhenQrOrderConfirmed(order);
        // orderService.createBillForOrder(order);
        emailService.sendSimpleEmail(order.getCustomer().getEmailAddress(),
                "Xác nhận thanh toán cho đơn hàng " + orderId + " thành công",
                "Đơn hàng " + orderId + " đã được thanh toán thành công với số tiền "
                        + paymentData.getAmount() + paymentData.getCurrency() + ".\n" +
                        "Thời gian thanh toán thành công: " + paymentData.getTransactionDateTime() + "\n" +
                        "Cảm ơn bạn đã tin tưởng và sử dụng dịch vụ của chúng tôi!");
    }

    @PostMapping(path = "/webhook")
    public void payosTransferHandler(@RequestBody Object body) {
        WebhookData data = payOS.webhooks().verify(body);
        CompletableFuture.runAsync(() -> orderConfirmed(data));
    }
}