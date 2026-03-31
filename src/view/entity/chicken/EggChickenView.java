package view.entity.chicken;

import model.entity.chicken.EggChickenModel;

import java.awt.*;

public class EggChickenView extends ChickenView {

    public EggChickenView(EggChickenModel model) {
        super(model, "assets/images/bird2.png");
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
    }
}
