package africa.springCore.delichopsbackend.core.portfolio.product.exception;

import africa.springCore.delichopsbackend.infrastructure.exception.DeliChopsException;

public class ProductCategoryNotFoundException extends DeliChopsException {
    public ProductCategoryNotFoundException(String message) {
        super(message);
    }
}
