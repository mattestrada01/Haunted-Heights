package utilizations;

import com.example.Game;

public class constants {

    public static final int ANI_SPEED = 25;

    public static class ObjectConstants {

		public static final int RED_POTION = 0;
		public static final int BLUE_POTION = 1;
		public static final int BARREL = 2;
		public static final int BOX = 3;

		public static final int RED_POTION_VALUE = 15;
		public static final int BLUE_POTION_VALUE = 30;

		public static final int CONTAINER_WIDTH_DEFAULT = 40;
		public static final int CONTAINER_HEIGHT_DEFAULT = 30;
		public static final int CONTAINER_WIDTH = (int) (Game.SCALE * CONTAINER_WIDTH_DEFAULT);
		public static final int CONTAINER_HEIGHT = (int) (Game.SCALE * CONTAINER_HEIGHT_DEFAULT);

		public static final int POTION_WIDTH_DEFAULT = 12;
		public static final int POTION_HEIGHT_DEFAULT = 16;
		public static final int POTION_WIDTH = (int) (Game.SCALE * POTION_WIDTH_DEFAULT);
		public static final int POTION_HEIGHT = (int) (Game.SCALE * POTION_HEIGHT_DEFAULT);

		public static int GetSpriteID(int object_type) {
			switch (object_type) {
			case RED_POTION: 
				return 7;
            case BLUE_POTION:
                return 7;
			case BARREL:
                return 8; 
            case BOX:
                return 8;
			}
			return 1;
		}
	}

    public static class EnemyConstants{
        public static final int ENEMY1 = 0;

        public static final int IDLE = 0;
		public static final int RUNNING = 1;
		public static final int ATTACK = 2;
		public static final int HIT = 3;
		public static final int DEAD = 4;

        public static final int ENEMY1_WIDTH_DEFAULT = 128;
        public static final int ENEMY1_HEIGHT_DEFAULT = 128;

        public static final int ENEMY1_WIDTH = (int)(ENEMY1_WIDTH_DEFAULT * Game.SCALE/2);
        public static final int ENEMY1_HEIGHT = (int)(ENEMY1_HEIGHT_DEFAULT * Game.SCALE/2);

        public static final int ENEMY1_DRAWOFFSET_X = (int)(15*Game.SCALE);
        public static final int ENEMY1_DRAWOFFSET_Y = (int)(33*Game.SCALE);


        public static int GetSpriteID(int type, int state) {
            switch (type) {
                case ENEMY1:
                    switch(state) {
                        case IDLE:
                            return 6;
                        case RUNNING:
                            return 7;
                        case ATTACK:
                            return 5;
                        case HIT:
                            return 3;
                        case DEAD:
                            return 6;
                    }
            }

            return 0;
        }

        public static int GetMaxHealth(int type) {
            switch(type) {
                case ENEMY1:
                    return 10;
                default:
                    return 1;
            }
        }

        public static int GetDamage(int type) {
            switch(type) {
                case ENEMY1:
                    return 25;
                default:
                    return 1;
            }
        }
    }

    public static class Environments {
        public static final int BIG_CLOUD_WIDTH_DEFAULT = 448;
        public static final int BIG_CLOUD_HEIGHT_DEFAULT = 101;
        public static final int BIG_CLOUD_HEIGHT2_DEFAULT = 120;
        public static final int BIG_CLOUD_WIDTH = (int)(BIG_CLOUD_WIDTH_DEFAULT * Game.SCALE);
        public static final int BIG_CLOUD_HEIGHT = (int)(BIG_CLOUD_HEIGHT_DEFAULT * Game.SCALE);
        public static final int BIG_CLOUD_HEIGHT2 = (int)(BIG_CLOUD_HEIGHT2_DEFAULT * Game.SCALE);

        public static final int SMALL_CLOUD_WIDTH_DEFAULT = 74;
        public static final int SMALL_CLOUD_HEIGHT_DEFAULT = 24;
        public static final int SMALL_CLOUD_WIDTH = (int)(SMALL_CLOUD_WIDTH_DEFAULT * Game.SCALE);
        public static final int SMALL_CLOUD_HEIGHT = (int)(SMALL_CLOUD_HEIGHT_DEFAULT * Game.SCALE);
        public static final int CLOUD_Y_REPEAT_DEFAULT = 8;
        public static final int CLOUD_Y_REPEAT = (int)(CLOUD_Y_REPEAT_DEFAULT * Game.SCALE);

        public static final int HANDS_DEFAULT_WIDTH = 120;
        public static final int HANDS_DEFAULT_HEIGHT = 75;
        public static final int HANDS_WIDTH = (int)(HANDS_DEFAULT_WIDTH * Game.SCALE);
        public static final int HANDS_HEIGHT = (int)(HANDS_DEFAULT_HEIGHT * Game.SCALE);

        public static final int HANDS_X_DEFAULT = 487;
        public static final int HANDS_X = (int)(HANDS_X_DEFAULT * Game.SCALE);
        public static final int HANDS_Y_DEFAULT = 360;
        public static final int HANDS_Y = (int)(HANDS_Y_DEFAULT * Game.SCALE);

        public static final int GROUND_X_DEFAULT = 425;
        public static final int GROUND_X = (int)(GROUND_X_DEFAULT * Game.SCALE);
        public static final int GROUND_Y_DEFAULT = 299;
        public static final int GROUND_Y = (int)(GROUND_Y_DEFAULT * Game.SCALE);
    }

    public static class UI {
        public static class Buttons {
            public static final int B_WIDTH_DEFAULT = 140;
            public static final int B_HEIGHT_DEFAULT = 56;
            public static final int B_WIDTH = (int)(B_WIDTH_DEFAULT * Game.SCALE);
            public static final int B_HEIGHT = (int)(B_HEIGHT_DEFAULT * Game.SCALE);
        }

        public static class PauseButtons {
            public static final int SOUND_DIMENSION_DEFAULT = 42;
            public static final int SOUND_DIMENSION = (int)(SOUND_DIMENSION_DEFAULT * Game.SCALE);
        }

        public static class URMButtons {
			public static final int URM_DEFAULT_SIZE = 56;
			public static final int URM_SIZE = (int) (URM_DEFAULT_SIZE * Game.SCALE);
		}

        public static class VolumeButtons {
            public static final int VOLUME_DEFAULT_WIDTH = 28;
            public static final int VOLUME_DEFAULT_HEIGHT = 44;
            public static final int SLIDER_DEFAULT_WIDTH = 215;

            public static final int SLIDER_WIDTH = (int)(SLIDER_DEFAULT_WIDTH * Game.SCALE);
            public static final int VOLUME_WIDTH = (int)(VOLUME_DEFAULT_WIDTH * Game.SCALE);
            public static final int VOLUME_HEIGHT = (int)(VOLUME_DEFAULT_HEIGHT * Game.SCALE);
        }
    }

    public static class Directions{
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    public static class PlayerConstants {
        public static final int ATTACK_1 = 0;
        public static final int ATTACK_2 = 1;
        public static final int DEAD = 2;
        public static final int HIT = 3;
        public static final int IDLE = 4;
        public static final int JUMPING = 5;
        public static final int RUNNING = 6;
        public static final int FALLING = 7;

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
                case FALLING:
                default:
                    return 1;
            }
        }
    }
}
