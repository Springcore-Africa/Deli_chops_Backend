package africa.springCore.delichopsbackend.core.portfolio.order.exception;

import africa.springCore.delichopsbackend.infrastructure.exception.DeliChopsException;

public class OrderCreationFailedException extends DeliChopsException {
    public OrderCreationFailedException(String message) {
        super(message);
    }
}
