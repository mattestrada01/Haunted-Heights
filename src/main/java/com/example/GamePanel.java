package com.example;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import com.example.inputs.KeyboardInputs;
import com.example.inputs.MouseInputs;



public class GamePanel extends JPanel{

    private MouseInputs mouseInputs;
    private Game game;

    public GamePanel(Game game) {
        mouseInputs = new MouseInputs(this);
        this.game = game;
        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
        setFocusable(true);
        requestFocusInWindow();
    }

    private void setPanelSize() {
        Dimension dimension = new Dimension(1280, 800);
        setMinimumSize(dimension);
        setMaximumSize(dimension);
        setPreferredSize(dimension);
    }

    public synchronized void updateGame() {

    }

    public synchronized void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render(g);
    }

    public Game getGame() {
        return game;
    }
}
