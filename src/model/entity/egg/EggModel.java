package model.entity.egg;
/**
 * EggModel - Trứng được thả bởi EggChicken
 * Rơi xuống → đếm hatchTimer → nở ra BabyChicken
 * crackStage: 0(nguyên) → 1 → 2 → 3(nở)
 * ke thua thu entityModel
 */
import model.entity.EntityModel;

public class EggModel extends EntityModel {

    private double speedY = 2;   // tốc độ rơi

    private int hatchTimer = 0;  // thời gian đếm
    private int hatchTime = 300; // thời gian để nở

    private int crackStage = 0;  // 0 -> 1 -> 2 -> 3
    private boolean hatched = false;

    public EggModel(double x, double y) {
        super(x, y);

        this.width = 20;
        this.height = 20;
    }

    @Override
    public void update() {

        // trứng rơi xuống
        y += speedY;

        // bắt đầu đếm thời gian nở
        hatchTimer++;

        // cập nhật trạng thái nứt
        if (hatchTimer > hatchTime * 0.25) crackStage = 1;
        if (hatchTimer > hatchTime * 0.5) crackStage = 2;
        if (hatchTimer > hatchTime * 0.75) crackStage = 3;

        // khi đủ thời gian thì nở
        if (hatchTimer >= hatchTime) {
            hatched = true;
        }
    }

    public boolean isHatched() {
        return hatched;
    }

    public int getCrackStage() {
        return crackStage;
    }

    public BabyChickenModel hatch() {
        if (hatched) {
            return new BabyChickenModel(x, y);
        }
        return null;
    }
}
public class EggModel {
}
