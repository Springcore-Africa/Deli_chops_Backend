package africa.springCore.delichopsbackend.core.portfolio.order.exception;

import africa.springCore.delichopsbackend.infrastructure.exception.DeliChopsException;

public class OrderNotFoundException extends DeliChopsException {
    public OrderNotFoundException(String message) {
        super(message);
    }
}
