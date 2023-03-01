package org.app.filtro;

import org.app.Controller;
import org.app.model.DataModel;

public class ControllerFiltro implements Controller {
    private  DataModel dataModel;

    @Override
    public void init(DataModel dataModel) {
        this.dataModel = dataModel;
    }

    @Override
    public void aggiorna() {

    }
}
