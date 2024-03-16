package objects;

import com.example.Game;

public class Potion extends GameObject{
    private float hoverOffset;
	private int maxHoverOffset, hoverDir = 1;

	public Potion(int x, int y, int objType) {
		super(x, y, objType);
		doAnimation = true;

		initHitbox(7, 14);

		xDrawoffset = (int) (3 * Game.SCALE);
		yDrawoffset = (int) (2 * Game.SCALE);

		maxHoverOffset = (int) (8 * Game.SCALE);
	}

	public void update() {
		updateAnimationTick();
		updateHover();
	}

	private void updateHover() {
		hoverOffset += (0.05f * Game.SCALE * hoverDir);

		if (hoverOffset >= maxHoverOffset)
			hoverDir = -1;
		else if (hoverOffset < 0)
			hoverDir = 1;

		hitbox.y = y + hoverOffset;
	}
}