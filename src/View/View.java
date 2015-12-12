package View;

import Control.ControlMouse;
import Model.Model;

import javax.swing.*;
import java.awt.*;

/**
 * Created by molipate on 07/12/15.
 */
public class View extends JFrame{

    private Model model;
    private JPanel panel;

    // JButton for menu
    private JButton newGame;
    private JButton quitGame;

    private JButton[][] plateau;
    private JButton[][] intersection;

    private JButton verticalWall;
    private JButton horizontalWall;

    private JLabel activePlayer;
    private JLabel remainingWall;

    public View(Model model) {
        super();
        setSize(new Dimension(900, 700));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.model = model;
        panel = new JPanel();

        plateau = new JButton[9][9];
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                plateau[i][j] = new JButton();

        intersection = new JButton[8][8];
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                intersection[i][j] = new JButton();

        initView();
        makeView();
    }

    public void display(boolean a){
        setVisible(a);
    }

    private void initView(){

        newGame = new JButton("Nouvelle partie");
        quitGame = new JButton("Quitter le jeu");

        verticalWall = new JButton();
        horizontalWall = new JButton();

        activePlayer = new JLabel();
        remainingWall = new JLabel();

        verticalWall.setBounds(619,356,14,94);
        verticalWall.setIcon(model.getAssetsImage().getWallV());
        verticalWall.setBorder(BorderFactory.createEmptyBorder());
        horizontalWall.setBounds(601, 302,94,14);
        horizontalWall.setIcon(model.getAssetsImage().getWallH());
        horizontalWall.setBorder(BorderFactory.createEmptyBorder());
    }

    public void makeView(){
        System.out.println("-> Painting ...");
        if(model.getGameState() == 0){
            panel.setLayout(new GridLayout(2, 1));
            panel.add(newGame);
            panel.add(quitGame);
        }
        else if(model.getGameState() == 1){

            panel.setLayout(null);
            panel.removeAll();

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    plateau[i][j].setSize(new Dimension(40, 40));
                    plateau[i][j].setBounds(j*54+100,i*54+100, 40, 40);
                    plateau[i][j].setContentAreaFilled(false);
                    switch (model.getPlateau(i, j)){
                        case 1:
                            plateau[i][j].setIcon(model.getAssetsImage().getIconJ1());
                            break;
                        case 2:
                            plateau[i][j].setIcon(model.getAssetsImage().getIconJ2());
                            break;
                    }
                    if(model.isPlayerSelected() && model.isMoveFree(i, j))
                        plateau[i][j].setBorder(BorderFactory.createLineBorder(Color.orange, 4));
                    else
                        plateau[i][j].setBorder(BorderFactory.createEmptyBorder());
                    panel.add(plateau[i][j]);
                }
            }

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {

                    intersection[i][j].setBounds((j + 1) * 40 + j * 14 + 100, (i + 1) * 40 + i * 14 + 100, 14, 14);
                    intersection[i][j].setContentAreaFilled(false);
                    intersection[i][j].setIcon(model.getAssetsImage().getIntersection());
                    if (model.isWallSelect() && model.isWallFree(i, j))
                        intersection[i][j].setBorder(BorderFactory.createLineBorder(Color.orange, 3));
                    else if(model.isWallFree(i, j))
                        intersection[i][j].setBorder(BorderFactory.createEmptyBorder());

                    switch (model.getWall(i, j)) {
                        case 1:
                            intersection[i][j].setEnabled(false);
                            intersection[i][j].setIcon(model.getAssetsImage().getWallV());
                            intersection[i][j].setDisabledIcon(model.getAssetsImage().getWallV());
                            intersection[i][j].setBounds((j + 1)*40 + j*14+100,  i*54+100,14,94);
                            break;
                        case 2:
                            intersection[i][j].setEnabled(false);
                            intersection[i][j].setIcon(model.getAssetsImage().getWallH());
                            intersection[i][j].setDisabledIcon(model.getAssetsImage().getWallH());
                            intersection[i][j].setBounds(j*54+100, (i + 1)*40 + i * 14+100,94,14);
                            break;
                    }
                    panel.add(intersection[i][j]);
                }
            }

            activePlayer.setSize(400, 200);
            activePlayer.setBackground(Color.black);
            activePlayer.setText("Joueur " + model.getActivePlayer() + " : c'est à toi !");
            activePlayer.setBounds(700, 100, 900, 200);

            remainingWall.setSize(400, 200);
            remainingWall.setBackground(Color.black);
            remainingWall.setText("Il te reste " + model.getNbWall() + " murs !");
            remainingWall.setBounds(700, 120, 900, 200);


            panel.add(verticalWall);
            panel.add(horizontalWall);
            panel.add(activePlayer);
            panel.add(remainingWall);
        }
        setContentPane(panel);
    }

    public void startNewGame(){
        model.setGameState(1);
        makeView();
    }

    public void setMouseListener(ControlMouse mouseListener) {
        newGame.addActionListener(mouseListener);
        quitGame.addActionListener(mouseListener);

        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                plateau[i][j].addActionListener(mouseListener);

        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8 ; j++)
                intersection[i][j].addActionListener(mouseListener);

        verticalWall.addActionListener(mouseListener);
        horizontalWall.addActionListener(mouseListener);
    }

    public JButton getNewGame(){
        return newGame;
    }

    public JButton getQuitGame(){
        return quitGame;
    }

    public JButton getPlateau(int i, int j) {
        return plateau[i][j];
    }

    public JButton getIntersection(int i, int j){
        return intersection[i][j];
    }

    public JButton getVerticalWall(){
        return verticalWall;
    }

    public JButton getHorizontalWall(){
        return horizontalWall;
    }

    public void leave() {
        dispose();
    }
}
