package main;

import entities.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;

public class Engine extends Canvas implements Runnable, MouseListener {
    Board board;
    Hand hand;
    Game game;

    ResourceLoader resourceLoader;


    boolean running = true;

    public Engine(Board board, Hand hand, Game game) {
        this.board = board;
        this.hand = hand;
        this.game = game;

        this.resourceLoader = new ResourceLoader();
    }

    public void start() {
        addMouseListener(this);
        running = true;
        run();
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        this.requestFocus();

        long pastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - pastTime) / ns;
            pastTime = now;

            while (delta > 0) {
                render();
                frames++;

                delta--;
            }

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }

    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();

        if(bs == null) {
            createBufferStrategy(2);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.black);
        g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

        g.drawImage(resourceLoader.loadSprite("src/assets/sprites/checkerboard_yellowgreen.png"), 0, 0, Game.WIDTH, Game.HEIGHT, null);

        try {
            for(Figure figure : board.getFigures()) {
                if(figure.getVisible()) {
                    Image image = figure.getFigureType() == FigureType.Regular ? resourceLoader.loadSprite(ResourceLoader.spritePaths.get(figure.getFigureColor())) :
                            figure.getFigureColor() == FigureColor.Black ? resourceLoader.loadSprite(ResourceLoader.BLACK_KING_PATH) : resourceLoader.loadSprite(ResourceLoader.WHITE_KING_PATH);
                    g.drawImage(image, getCoordsFromRowCol(figure.getCurrentPosition().x), getCoordsFromRowCol(figure.getCurrentPosition().y), null);
                }
            }
        } catch (Exception ignored) { }

        try {
            if(hand.getFigureInHand() != null) {
                Point mp = getMousePosition();
                Image image = hand.getFigureInHand().getFigureType() == FigureType.Regular ? resourceLoader.loadSprite(ResourceLoader.spritePaths.get(hand.getFigureInHand().getFigureColor())) :
                        hand.getFigureInHand().getFigureColor() == FigureColor.Black ? resourceLoader.loadSprite(ResourceLoader.BLACK_KING_PATH) : resourceLoader.loadSprite(ResourceLoader.WHITE_KING_PATH);
                g.drawImage(image, mp.x - Game.FIGURE_SIZE/2, mp.y - Game.FIGURE_SIZE/2, null);
            }
        } catch (Exception ignored) { }

        try {
            for(Point p : board.getInvisibleFigurePositions()) {
                Image image = resourceLoader.loadSprite(ResourceLoader.INVISIBLE_PATH);
                g.drawImage(image, getCoordsFromRowCol(p.x), getCoordsFromRowCol(p.y), null);
            }
        }
        catch (Exception ignored) { }



        g.dispose();
        bs.show();
    }

    private int getCoordsFromRowCol(int rowOrCol) {
        return (rowOrCol-1)*Game.FIGURE_SIZE;
    }
    private Point convertCoordsToRowCol(Point coords) {
        return new Point(coords.x/Game.FIGURE_SIZE + 1, coords.y/Game.FIGURE_SIZE + 1);
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point rowCol = convertCoordsToRowCol(e.getPoint());
        hand.onClick(rowCol, game.getCurrentTurnColor());
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
