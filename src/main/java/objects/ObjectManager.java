package objects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import gamestates.Playing;
import java.awt.geom.Rectangle2D;
import levels.Level;
import utilizations.LoadSave;
import static utilizations.constants.ObjectConstants.*;
import audio.AudioPlayer;
import entities.Player;



public class ObjectManager {
    private Playing playing;
    private BufferedImage[][] potionImages, containerImages;
    private BufferedImage spikeImage;
    private ArrayList<Potion> potions;
    private ArrayList<GameContainer> containers;
    private ArrayList<Spike> spikes;


    public ObjectManager(Playing playing) {
        this.playing = playing;
        loadImages();
    }

    public void checkTouchedSpikes(Player p) {
        for (Spike s : spikes) {
            if(s.getHitbox().intersects(p.getHitbox())){
                p.kill();
            }
        }
    }

    public void checkObjectTouched(Rectangle2D.Float hitbox) {
        for (Potion p : potions)
			if (p.isActive()) {
				if (hitbox.intersects(p.getHitbox())) {
					p.setActive(false);
					applyEffectToPlayer(p);
				}
			}
    }

    public void applyEffectToPlayer(Potion p) {
        if (p.getObjectType() == RED_POTION)
			playing.getPlayer().changeHealth(RED_POTION_VALUE);
		else
			playing.getPlayer().changePower(BLUE_POTION_VALUE);
            playing.getGame().getAudioPlayer().playEffect(AudioPlayer.TOUCH);
    }

    public void checkObjectHit(Rectangle2D.Float attackBox) {
        for (GameContainer gc : containers)
        if (gc.isActive() && !gc.doAnimation) {
            if (gc.getHitbox().intersects(attackBox)) {
                gc.setAnimation(true);
                int type = 0;
                if (gc.getObjectType() == BARREL)
                    type = 1;
                potions.add(new Potion((int) (gc.getHitbox().x + gc.getHitbox().width / 2), (int) (gc.getHitbox().y - gc.getHitbox().height / 4), type));
                return;
            }
        }
    }

    public void loadObjects(Level newLevel) {
        potions = new ArrayList<> (newLevel.getPotions());
        containers = new ArrayList<> (newLevel.getContainers());
        spikes = newLevel.getSpikes();
    }


    private void loadImages() {
        BufferedImage potionSprite = LoadSave.GetSpriteAtlas(LoadSave.POTIONS);
        potionImages = new BufferedImage[2][7];
        for (int j = 0; j < potionImages.length; j++) {
            for(int i = 0; i < potionImages[j].length; i++) {
                potionImages[j][i] = potionSprite.getSubimage(12*i, 16*j, 12, 16);
            }
        }

        BufferedImage containerSprite = LoadSave.GetSpriteAtlas(LoadSave.OBJECTS);
        containerImages = new BufferedImage[2][8];
        for (int j = 0; j < containerImages.length; j++) {
            for(int i = 0; i < containerImages[j].length; i++) {
                containerImages[j][i] = containerSprite.getSubimage(40*i, 30*j, 40, 30);
            }
        }

        spikeImage = LoadSave.GetSpriteAtlas(LoadSave.TRAPS);
    }

    public void update() {
        for(Potion p: potions) 
            if(p.isActive())
                p.update();

        for(GameContainer gc: containers)
            if(gc.isActive())
                gc.update();
    }

    public void draw(Graphics g, int xLevelOffset) {
        drawPotions(g, xLevelOffset);
        drawContainers(g, xLevelOffset);
        drawTraps(g, xLevelOffset);
    }


    private void drawTraps(Graphics g, int xLevelOffset) {
        for(Spike s : spikes) {
            g.drawImage(spikeImage, (int)(s.getHitbox().x - xLevelOffset), (int)(s.getHitbox().y - s.getyDrawoffset()), SPIKE_WIDTH, SPIKE_HEIGHT, null);
        }
    }

    private void drawContainers(Graphics g, int xLevelOffset) {
        for(GameContainer gc: containers)
            if(gc.isActive()){
                int type = 0;

                if(gc.getObjectType() == BARREL) 
                    type = 1;

                g.drawImage(containerImages[type][gc.getAnimationIndex()],
                                (int)(gc.getHitbox().x - gc.getxDrawoffset() - xLevelOffset), 
                                (int)(gc.getHitbox().y - gc.getyDrawoffset()), 
                                CONTAINER_WIDTH, 
                                CONTAINER_HEIGHT, null);
            }
               
    }


    private void drawPotions(Graphics g, int xLevelOffset) {
        for(Potion p: potions) 
            if(p.isActive()) {
                int type = 0;

                if(p.getObjectType() == RED_POTION) 
                    type = 1;

                g.drawImage(potionImages[type][p.getAnimationIndex()],
                    (int)(p.getHitbox().x - p.getxDrawoffset() - xLevelOffset), 
                    (int)(p.getHitbox().y - p.getyDrawoffset()), 
                    POTION_WIDTH, 
                    POTION_HEIGHT, null);
            }
    }


    public void resetAllObjects() {
        loadObjects(playing.getLevelManager().getCurrentLevel());

        for(Potion p: potions) 
            p.reset();

        for(GameContainer gc: containers)
            gc.reset();
    }
}
