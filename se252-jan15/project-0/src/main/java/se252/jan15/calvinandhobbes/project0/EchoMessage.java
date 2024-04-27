package se252.jan15.calvinandhobbes.project0;

/**
 * Java Object used to encapsulate the echo message. This is "serialized" to JSON by the web service response.
 * 
 * @author Yogesh Simmhan
 * @version 1.0
 * @see <a href="http://www.serc.iisc.ernet.in/~simmhan/SE252/">IISc SERC 'SE252:Intro to Cloud Computing' Course Webpage</a>
 * 
 * (c) Yogesh Simmhan, 2015
 * This work is licensed under a Attribution 4.0 International (CC BY 4.0).
 * http://creativecommons.org/licenses/by/4.0/
 */
public class EchoMessage {
	
	private String message;
	
	public EchoMessage() {
    }

    public EchoMessage(String message_) {
        this.setMessage(message_);
    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


}
