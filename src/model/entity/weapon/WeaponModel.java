package model.entity.weapon;

public class WeaponModel {

    private WeaponType type;
    private int        level;      // 1 ~ 5

    // Thống số tính theo level
    private int  damage;
    private int  cooldown;
    private boolean pierce;

    public WeaponModel() {
        this.type  = WeaponType.SINGLE;
        this.level = 1;
        applyStats();
    }

    // ── Nâng cấp ─────────────────────────────────────────────

    public void levelUp() {
        if (level < 5) {
            level++;
            applyStats();
        }
    }


    public void switchType(WeaponType newType) {
        this.type = newType;
        applyStats();
    }

    private void applyStats() {
        switch (level) {
            case 1 -> { damage = 1; cooldown = 20; pierce = false; }
            case 2 -> { damage = 2; cooldown = 18; pierce = false; }
            case 3 -> { damage = 3; cooldown = 15; pierce = false; }
            case 4 -> { damage = 4; cooldown = 12; pierce = true;  }
            case 5 -> { damage = 5; cooldown = 10; pierce = true;  }
        }
    }

    public boolean isMaxLevel() { return level >= 5; }


    public WeaponType getType()     { return type; }
    public int        getLevel()    { return level; }
    public int        getDamage()   { return damage; }
    public int        getCooldown() { return cooldown; }
    public boolean    isPierce()    { return pierce; }
}
