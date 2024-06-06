package africa.springCore.delichopsbackend.infrastructure.exception;

public class UserAlreadyExistsException extends DeliChopsException {
	public UserAlreadyExistsException(String message) {
		super(message);
	}
}
