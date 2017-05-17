
package br.paulomenezes.ufrpe.models;

import java.util.ArrayList;
import java.util.List;

public class Grades {

    private List<Table> tables = new ArrayList<Table>();
    private List<Object> warnings = new ArrayList<Object>();

    /**
     * 
     * @return
     *     The tables
     */
    public List<Table> getTables() {
        return tables;
    }

    /**
     * 
     * @param tables
     *     The tables
     */
    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

    /**
     * 
     * @return
     *     The warnings
     */
    public List<Object> getWarnings() {
        return warnings;
    }

    /**
     * 
     * @param warnings
     *     The warnings
     */
    public void setWarnings(List<Object> warnings) {
        this.warnings = warnings;
    }

}
