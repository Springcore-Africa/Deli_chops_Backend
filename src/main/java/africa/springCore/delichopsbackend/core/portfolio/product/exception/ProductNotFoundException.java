package africa.springCore.delichopsbackend.core.portfolio.product.exception;

import africa.springCore.delichopsbackend.infrastructure.exception.DeliChopsException;

public class ProductNotFoundException extends DeliChopsException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
