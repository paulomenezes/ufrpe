
package br.deinfo.ufrpe.models;


public class Range {

    private String _class;
    private String content;
    private String headers;

    /**
     * 
     * @return
     *     The _class
     */
    public String getClass_() {
        return _class;
    }

    /**
     * 
     * @param _class
     *     The class
     */
    public void setClass_(String _class) {
        this._class = _class;
    }

    /**
     * 
     * @return
     *     The content
     */
    public String getContent() {
        return content;
    }

    /**
     * 
     * @param content
     *     The content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 
     * @return
     *     The headers
     */
    public String getHeaders() {
        return headers;
    }

    /**
     * 
     * @param headers
     *     The headers
     */
    public void setHeaders(String headers) {
        this.headers = headers;
    }

}
