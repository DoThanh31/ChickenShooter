package model.entity;

    public abstract class EntityModel {

        protected float x, y;
        protected int   w, h;
        protected int   hp;
        protected int   maxHp;
        protected boolean alive;

        public EntityModel(float v, float x, float y, int w, int h) {
            this.x     = x;
            this.y     = y;
            this.w     = w;
            this.h     = h;
            this.maxHp = maxHp;
            this.hp    = maxHp;
            this.alive = true;
        }

        public abstract void reset();

        public void takeDamage(int damage) {
            if (!alive) return;
            hp -= damage;
            if (hp <= 0) {
                hp    = 0;
                alive = false;
            }
        }

        public void heal(int amount) {
            hp = Math.min(hp + amount, maxHp);
        }

        public float getHpRatio() {
            return (float) hp / maxHp;
        }

        public boolean collidesWith(EntityModel other) {
            return alive && other.alive
                    && x < other.x + other.w
                    && x + w > other.x
                    && y < other.y + other.h
                    && y + h > other.y;
        }

        public float getCenterX() { return x + w / 2f; }
        public float getCenterY() { return y + h / 2f; }

        public float   getX()     { return x; }
        public float   getY()     { return y; }
        public int     getW()     { return w; }
        public int     getH()     { return h; }
        public int     getHp()    { return hp; }
        public int     getMaxHp() { return maxHp; }
        public boolean isAlive()  { return alive; }

        public void setX(float x)         { this.x = x; }
        public void setY(float y)         { this.y = y; }
        public void setAlive(boolean alive){ this.alive = alive; }
    }

