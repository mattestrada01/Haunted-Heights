package utilizations;

public class constants {

    public static class Directions{
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    public static class PlayerConstants {
        public static final int IDLE = 4;
        public static final int RUNNING = 6;
        public static final int JUMPING = 5;
        public static final int HIT = 3;
        public static final int ATTACK_1 = 0;
        public static final int ATTACK_2 = 1;
        public static final int DEAD = 2;

        public static int GetSpriteID(int player_action){
            switch(player_action) {
                case RUNNING:
                    return 8;
                case IDLE:
                    return 5;
                case HIT:
                    return 2;
                case JUMPING:
                    return 8;
                case ATTACK_1:
                    return 6;
                case ATTACK_2:
                    return 10;
                case DEAD:
                    return 5; 
                default:
                    return 1;
            }
        }
    }
}
