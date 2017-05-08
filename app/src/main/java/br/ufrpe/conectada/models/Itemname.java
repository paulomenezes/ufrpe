
package br.ufrpe.conectada.models;

import org.parceler.Parcel;

@Parcel
public class Itemname {

    private String _class;
    private int colspan;
    private String content;
    private String celltype;
    private String id;

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
     *     The colspan
     */
    public int getColspan() {
        return colspan;
    }

    /**
     * 
     * @param colspan
     *     The colspan
     */
    public void setColspan(int colspan) {
        this.colspan = colspan;
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
     *     The celltype
     */
    public String getCelltype() {
        return celltype;
    }

    /**
     * 
     * @param celltype
     *     The celltype
     */
    public void setCelltype(String celltype) {
        this.celltype = celltype;
    }

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

}
