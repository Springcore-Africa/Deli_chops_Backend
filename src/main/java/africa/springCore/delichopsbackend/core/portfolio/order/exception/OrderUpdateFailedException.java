package africa.springCore.delichopsbackend.core.portfolio.order.exception;

import africa.springCore.delichopsbackend.infrastructure.exception.DeliChopsException;

public class OrderUpdateFailedException extends DeliChopsException {
    public OrderUpdateFailedException(String message) {
        super(message);
    }
}
