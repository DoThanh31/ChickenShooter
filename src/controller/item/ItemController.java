package controller.item;

import controller.Updatable;
import model.item.ItemModel;

public class ItemController implements Updatable {

    private final ItemModel model;

    public ItemController(ItemModel model) {
        this.model = model;
    }

    @Override
    public void update() {
        if (!model.isAlive()) return;
        
        model.update();
    }

    public ItemModel getModel() {
        return model;
    }
}
