package br.deinfo.ufrpe.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by paulo on 31/10/2016.
 */

public class Messages {
    private List<Message> messages = new ArrayList<Message>();
    private List<Object> warnings = new ArrayList<Object>();

    /**
     *
     * @return
     * The messages
     */
    public List<Message> getMessages() {
        return messages;
    }

    /**
     *
     * @param messages
     * The messages
     */
    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    /**
     *
     * @return
     * The warnings
     */
    public List<Object> getWarnings() {
        return warnings;
    }

    /**
     *
     * @param warnings
     * The warnings
     */
    public void setWarnings(List<Object> warnings) {
        this.warnings = warnings;
    }

}
