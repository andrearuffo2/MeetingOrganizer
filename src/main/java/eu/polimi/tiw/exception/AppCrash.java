package eu.polimi.tiw.exception;

/**
 * @author Andrea Ruffo
 * @since 0.0.1-SNAPSHOT
 * 
 *        This class will handle specific checked exceptions.
 */
public class AppCrash extends Exception {

	private static final long serialVersionUID = 1L;

	public AppCrash(String errorMessage) {
		super(errorMessage);
	}

}
