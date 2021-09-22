/**
 * Thrown when a fish is positioned in a way that doesn't make sense (either
 * above another fish, or over a rock.)
 * 
 * @author Fawzi Emad
 */
public class IllegalEnemyPositionException extends RuntimeException {

	/* Field required for any Serializable class */
	private static final long serialVersionUID = 0;
	
	/* Codes used to distinguish which type of error has occurred */
	public static final int TWO_FISH_IN_ONE_PLACE = 0;
	public static final int FISH_OVER_ROCK = 1;
	
	private String message;
	private int code;
	
	/**
	 * @param code either TWO_FISH_IN_ONE_PLACE, or FISH_OVER_ROCK
	 */
	public IllegalEnemyPositionException(int code) {
		this.code = code;
		if (code == TWO_FISH_IN_ONE_PLACE)
			message = "You're trying to put two fish in the same place!";
		else// if (code == FISH_OVER_ROCK)
			message = "You're trying to put a fish over landscape that is not water.";
	}
	
	public String getMessage() {
		return message;
	}
	
	public int getCode() {
		return code;
	}
}
