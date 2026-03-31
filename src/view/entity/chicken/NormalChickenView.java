package view.entity.chicken;

import model.entity.chicken.NormalChickenModel;

import java.awt.*;

public class NormalChickenView extends ChickenView {

    public NormalChickenView(NormalChickenModel model) {
        super(model, "assets/images/bird1.png");
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
    }
}
