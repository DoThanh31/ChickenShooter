package controller.collision;

import model.GameModel;
import model.entity.PlayerModel;
import model.entity.bullet.BulletModel;
import model.entity.chicken.ChickenModel;
import model.item.ItemModel;

import java.util.Iterator;
import java.util.List;

public class CollisionController {

    private final GameModel gameModel;
    private final PlayerModel player;

    public CollisionController(GameModel gameModel, PlayerModel player) {
        this.gameModel = gameModel;
        this.player = player;
    }

    public void checkCollisions(List<ChickenModel> chickens, List<BulletModel> bullets, List<ItemModel> items) {
        checkBulletCollisions(chickens, bullets);
        checkPlayerItemCollisions(items);
        checkPlayerChickenCollisions(chickens);
    }

    private void checkBulletCollisions(List<ChickenModel> chickens, List<BulletModel> bullets) {
        Iterator<BulletModel> bIt = bullets.iterator();
        while (bIt.hasNext()) {
            BulletModel b = bIt.next();
            boolean hit = false;

            if (b.getOwner() == BulletModel.Owner.PLAYER) {
                for (ChickenModel c : chickens) {
                    if (c.isAlive() && c.collidesWith(b)) {
                        c.takeDamage(b.getDamage());
                        hit = true;
                        if (!b.isPierce()) break;
                    }
                }
            } else {
                if (player.isAlive() && player.collidesWith(b)) {
                    player.takeDamage(b.getDamage());
                    hit = true;
                }
            }

            if (hit && !b.isPierce()) {
                bIt.remove();
            }
        }
    }

    private void checkPlayerItemCollisions(List<ItemModel> items) {
        Iterator<ItemModel> iIt = items.iterator();
        while (iIt.hasNext()) {
            ItemModel item = iIt.next();
            if (item.isAlive() && player.collidesWith(item)) {
                item.applyEffect(player);
                iIt.remove();
            }
        }
    }

    private void checkPlayerChickenCollisions(List<ChickenModel> chickens) {
        for (ChickenModel c : chickens) {
            if (c.isAlive() && player.isAlive() && c.collidesWith(player)) {
                player.takeDamage(1);
                c.takeDamage(999); 
            }
        }
    }
}
