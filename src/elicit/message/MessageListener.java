/*
 * MessageListener.java
 *
 * Created on 05 September 2007, 21:50
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package elicit.message;

/**
 *
 * @author Marco
 */
public interface MessageListener {
    void OnStart();
    void OnNewMessage(Message message_p);
    void OnFinish();
}
