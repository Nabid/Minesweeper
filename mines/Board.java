package mines;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.*; // ImageIcon, JPanel, JFrame

public class Board extends JPanel {

    private final int images = 13; // total images
    private final int cellSize = 15; // cell sizes or pixels :D
    private final int coverCell = 10; // primarily covered with 10
    private final int markCell = 10; 
    private final int emptyCell = 0; // empty cell with 0
    private final int mineCell = 9; // mine cell with 9
    private final int coveredMineCell = mineCell + coverCell; // sandwitch
    private final int mineCellMarked = coveredMineCell + markCell; // sandwitch
    private final int paintMine = 9;
    private final int paintCover = 10;
    private final int paintMark = 11;
    private final int paintWrongMark = 12;

    private int[] field;
    private boolean inGame;
    private int mines_left;
    private Image[] img;
    private int mines = 40;
    private int all_cells;
    private JLabel statusbar;
	private int rows;
    private int cols;

    public Board( JLabel statusbar, int rw, int cl ) {
        rows = rw;
    	cols = cl;
        this.statusbar = statusbar;
        img = new Image[images];
        for (int i=0; i<images; i++) {
            img[i] = (new ImageIcon(this.getClass().getResource((i) + ".png"))).getImage();
        }
        setDoubleBuffered(true);
        addMouseListener(new MinesAdapter());
        newGame();
    }

    public void newGame() {
        Random random;
        int currentCol;
        int i = 0;
        int position = 0;
        int cell = 0;

        random = new Random();
        inGame = true;
        mines_left = mines;

        all_cells = rows * cols;
        field = new int[all_cells];

        for (i=0; i<all_cells; i++)
            field[i] = coverCell;

        statusbar.setText(Integer.toString(mines_left));
        i = 0;
        while (i < mines) {
            position = (int) (all_cells * random.nextDouble());
            if ((position < all_cells) &&
                (field[position] != coveredMineCell)) {
                currentCol = position % cols;
                field[position] = coveredMineCell;
                i++;
                if (currentCol > 0) {
                    cell = position - 1 - cols;
                    if (cell >= 0)
                        if (field[cell] != coveredMineCell)
                            field[cell] += 1;
                    cell = position - 1;
                    if (cell >= 0)
                        if (field[cell] != coveredMineCell)
                            field[cell] += 1;

                    cell = position + cols - 1;
                    if (cell < all_cells)
                        if (field[cell] != coveredMineCell)
                            field[cell] += 1;
                }

                cell = position - cols;
                if (cell >= 0)
                    if (field[cell] != coveredMineCell)
                        field[cell] += 1;
                cell = position + cols;
                if (cell < all_cells)
                    if (field[cell] != coveredMineCell)
                        field[cell] += 1;

                if (currentCol < (cols - 1)) {
                    cell = position - cols + 1;
                    if (cell >= 0)
                        if (field[cell] != coveredMineCell)
                            field[cell] += 1;
                    cell = position + cols + 1;
                    if (cell < all_cells)
                        if (field[cell] != coveredMineCell)
                            field[cell] += 1;
                    cell = position + 1;
                    if (cell < all_cells)
                        if (field[cell] != coveredMineCell)
                            field[cell] += 1;
                }
            }
        }
    }

    public void findEmptyCells(int j) {

        int currentCol = j % cols;
        int cell;

        if (currentCol > 0) {
            cell = j - cols - 1;
            if (cell >= 0)
                if (field[cell] > mineCell) {
                    field[cell] -= coverCell;
                    if (field[cell] == emptyCell)
                        findEmptyCells(cell);
                }

            cell = j - 1;
            if (cell >= 0)
                if (field[cell] > mineCell) {
                    field[cell] -= coverCell;
                    if (field[cell] == emptyCell)
                        findEmptyCells(cell);
                }

            cell = j + cols - 1;
            if (cell < all_cells)
                if (field[cell] > mineCell) {
                    field[cell] -= coverCell;
                    if (field[cell] == emptyCell)
                        findEmptyCells(cell);
                }
        }

        cell = j - cols;
        if (cell >= 0)
            if (field[cell] > mineCell) {
                field[cell] -= coverCell;
                if (field[cell] == emptyCell)
                    findEmptyCells(cell);
            }

        cell = j + cols;
        if (cell < all_cells)
            if (field[cell] > mineCell) {
                field[cell] -= coverCell;
                if (field[cell] == emptyCell)
                    findEmptyCells(cell);
            }

        if (currentCol < (cols - 1)) {
            cell = j - cols + 1;
            if (cell >= 0)
                if (field[cell] > mineCell) {
                    field[cell] -= coverCell;
                    if (field[cell] == emptyCell)
                        findEmptyCells(cell);
                }

            cell = j + cols + 1;
            if (cell < all_cells)
                if (field[cell] > mineCell) {
                    field[cell] -= coverCell;
                    if (field[cell] == emptyCell)
                        findEmptyCells(cell);
                }

            cell = j + 1;
            if (cell < all_cells)
                if (field[cell] > mineCell) {
                    field[cell] -= coverCell;
                    if (field[cell] == emptyCell)
                        findEmptyCells(cell);
                }
        }

    }

    public void paint(Graphics g) {
        int cell = 0;
        int uncover = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                cell = field[(i * cols) + j];
                if (inGame && cell == mineCell)
                    inGame = false;
                if (!inGame) {
                    if (cell == coveredMineCell) {
                        cell = paintMine;
                    } else if (cell == mineCellMarked) {
                        cell = paintMark;
                    } else if (cell > coveredMineCell) {
                        cell = paintWrongMark;
                    } else if (cell > mineCell) {
                        cell = paintCover;
                    }
                } else {
                    if (cell > coveredMineCell)
                        cell = paintMark;
                    else if (cell > mineCell) {
                        cell = paintCover;
                        uncover++;
                    }
                }
                g.drawImage(img[cell], (j * cellSize),
                    (i * cellSize), this);
            }
        }

        if (uncover == 0 && inGame) {
            inGame = false;
            statusbar.setText("Game won");
        } else if (!inGame)
            statusbar.setText("Game lost");
    }

    class MinesAdapter extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            int cCol = x / cellSize;
            int cRow = y / cellSize;
            boolean rep = false;

            if (!inGame) {
                newGame();
                repaint();
            }
            if ((x < cols * cellSize) && (y < rows * cellSize)) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    if (field[(cRow * cols) + cCol] > mineCell) {
                        rep = true;
                        if (field[(cRow * cols) + cCol] <= coveredMineCell) {
                            if (mines_left > 0) {
                                field[(cRow * cols) + cCol] += markCell;
                                mines_left--;
                                statusbar.setText(Integer.toString(mines_left));
                            } else
                                statusbar.setText("No marks left");
                        } else {

                            field[(cRow * cols) + cCol] -= markCell;
                            mines_left++;
                            statusbar.setText(Integer.toString(mines_left));
                        }
                    }
                } else {
                    if (field[(cRow * cols) + cCol] > coveredMineCell) {
                        return;
                    }
                    if ((field[(cRow * cols) + cCol] > mineCell) &&
                        (field[(cRow * cols) + cCol] < mineCellMarked)) {

                        field[(cRow * cols) + cCol] -= coverCell;
                        rep = true;

                        if (field[(cRow * cols) + cCol] == mineCell)
                            inGame = false;
                        if (field[(cRow * cols) + cCol] == emptyCell)
                            findEmptyCells((cRow * cols) + cCol);
                    }
                }
                if (rep)
                    repaint();
            }
        }
    }
}
