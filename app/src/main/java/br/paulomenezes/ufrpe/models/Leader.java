
package br.paulomenezes.ufrpe.models;

import org.parceler.Parcel;

@Parcel
public class Leader {

    private String _class;
    private int rowspan;

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
     *     The rowspan
     */
    public int getRowspan() {
        return rowspan;
    }

    /**
     * 
     * @param rowspan
     *     The rowspan
     */
    public void setRowspan(int rowspan) {
        this.rowspan = rowspan;
    }

}
