package africa.springCore.delichopsbackend.infrastructure.exception;

public class UserNotFoundException extends DeliChopsException {
	public UserNotFoundException(String message) {
		super(message);
	}
}
