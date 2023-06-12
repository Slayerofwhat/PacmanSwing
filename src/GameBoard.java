import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Random;

public class GameBoard extends JFrame implements Runnable, KeyListener, ComponentListener {

    public static final String monitor = new String();
    Pacman pacman = new Pacman();
    private Ghost ghostRed;
    private Ghost ghostPink;
    private Ghost ghostCyan;
    private Ghost ghostOrange;
    private JTable gameTable = new JTable();
    private int rows;
    private int columns;
    private MyTableModel myTableModel;
    private ColorRender colorRender;
    private int health = 3;
    private int score = 0;
    private int winScore = 0;

    private int totalScore = 0;
    private int ghostsCount = 0;
    Thread pacmanThread = new Thread(this);
    Thread ghostRedThread = new Thread(ghostRed);
    Thread ghostPinkThread = new Thread(ghostPink);
    Thread ghostCyanThread = new Thread(ghostCyan);
    Thread ghostOrangeThread = new Thread(ghostOrange);
    private int time = 0;
    private JLabel livesLabel;
    private JLabel scoreLabel = new JLabel();
    private JLabel timeLabel;

    private boolean canEatWalls = false;


    public GameBoard(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        initializeBoard();
    }

    private void initializeBoard() {
        BoardRepainter repainter = new BoardRepainter(gameTable);
        repainter.start();



        colorRender = new ColorRender(pacman, ghostRed, ghostPink, ghostCyan, ghostOrange);
        this.myTableModel = new MyTableModel(rows, columns);

        gameTable.setModel(myTableModel);

        gameTable.setRowHeight(20);
        for (int i = 0; i < gameTable.getColumnCount(); i++) {
            gameTable.getColumnModel().getColumn(i).setPreferredWidth(20);
        }

        for(int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++){
                myTableModel.setValueAt("N", i, j);
            }
        }

        gameTable.setRowSelectionAllowed(false);

        createWall();
        createGhosts();
        createDots();


        System.out.println("SCORE: " + winScore);

        for(int i = 0; i < gameTable.getColumnCount(); i++){
            gameTable.getColumnModel().getColumn(i).setCellRenderer(colorRender);
        }

        addKeyListener(this);
        setFocusable(true);
        requestFocus();
        addComponentListener(this);

        pacmanThread.start();

        JPanel info = new JPanel();
        info.setBackground(Color.BLACK);

        info.setLayout(new GridLayout(1, 0));

        livesLabel = new JLabel("Lives: " + health);
        livesLabel.setForeground(Color.WHITE);
        livesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        livesLabel.setVerticalAlignment(SwingConstants.CENTER);
        scoreLabel = new JLabel("Score: " + totalScore);
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        livesLabel.setVerticalAlignment(SwingConstants.CENTER);
        timeLabel = new JLabel("Time: " + time + " seconds");
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        livesLabel.setVerticalAlignment(SwingConstants.CENTER);

        Time time = new Time(this);
        time.start();

        info.add(livesLabel);
        info.add(scoreLabel);
        info.add(timeLabel);

        //add(gameTable);

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(0, 1));
        jPanel.add(gameTable);
        //jPanel.add(info);
        //info.setPreferredSize(new Dimension(200, 200));
        //info.setSize(200, 100);
        //add(jPanel);
        JSplitPane jSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, gameTable, info);
        jSplitPane.setUI(new BasicSplitPaneUI()
        {
            @Override
            public BasicSplitPaneDivider createDefaultDivider()
            {
                return new BasicSplitPaneDivider(this)
                {
                    public void setBorder(Border b) {}

                    @Override
                    public void paint(Graphics g)
                    {
                        g.setColor(Color.BLACK);
                        g.fillRect(0, 0, getSize().width, getSize().height);
                        super.paint(g);
                    }
                };
            }
        });
        jSplitPane.setBorder(null);
        jSplitPane.setEnabled(false);
        jSplitPane.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY,
                new PropertyChangeListener() {
                    @Override
                    public void propertyChange(PropertyChangeEvent pce) {
                        int rowSize = gameTable.getHeight() / rows;
                        gameTable.setRowHeight(rowSize);
                        int columnSize = gameTable.getWidth() / columns;
                        for (int i = 0; i < gameTable.getColumnCount(); i++) {
                            gameTable.getColumnModel().getColumn(i).setPreferredWidth(columnSize);
                        }
                        repaint();
                    }
                });

        add(jSplitPane);


        // Set JFrame properties
        setTitle("Pac-Man Game Board");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon("img/Pacman-1.png");
        setIconImage(icon.getImage());
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        jSplitPane.setDividerLocation(getHeight() - 80);

    }

    private void createWall() {
        for (int j = 2; j < columns/2 - 1; j+=4){
            for (int i = 2; i <= rows - 3; i+=4){
                Random random = new Random();
                switch (random.nextInt(8)) {
                    case 0 -> {
                        //Horizontal tunnel
                        myTableModel.setValueAt("X", i, j);
                        myTableModel.setValueAt("X", i, j + 1);
                        myTableModel.setValueAt("X", i, j + 2);
                        myTableModel.setValueAt("X", i + 2, j);
                        myTableModel.setValueAt("X", i + 2, j + 1);
                        myTableModel.setValueAt("X", i + 2, j + 2);
                        break;
                    }
                    case 1 -> {
                        //Vertical tunnel
                        myTableModel.setValueAt("X", i, j);
                        myTableModel.setValueAt("X", i + 1, j);
                        myTableModel.setValueAt("X", i + 2, j);
                        myTableModel.setValueAt("X", i, j + 2);
                        myTableModel.setValueAt("X", i + 1, j + 2);
                        myTableModel.setValueAt("X", i + 2, j + 2);
                        break;
                    }
                    case 2 -> {
                        //T-up
                        myTableModel.setValueAt("X", i, j);
                        myTableModel.setValueAt("X", i, j + 1);
                        myTableModel.setValueAt("X", i, j + 2);
                        myTableModel.setValueAt("X", i + 1, j + 1);
                        myTableModel.setValueAt("X", i + 2, j + 1);
                        break;
                    }
                    case 3 -> {
                        //T-down
                        myTableModel.setValueAt("X", i, j + 1);
                        myTableModel.setValueAt("X", i + 1, j + 1);
                        myTableModel.setValueAt("X", i + 2, j + 1);
                        myTableModel.setValueAt("X", i + 2, j);
                        myTableModel.setValueAt("X", i + 2, j + 2);
                        break;
                    }
                    case 4 -> {
                        //Right-down corner
                        myTableModel.setValueAt("X", i, j);
                        myTableModel.setValueAt("X", i + 2, j);
                        myTableModel.setValueAt("X", i + 2, j + 1);
                        myTableModel.setValueAt("X", i + 2, j + 2);
                        myTableModel.setValueAt("X", i + 1, j + 2);
                        myTableModel.setValueAt("X", i, j + 2);
                        break;
                    }
                    case 5 -> {
                        //Left-down corner
                        myTableModel.setValueAt("X", i, j + 2);
                        myTableModel.setValueAt("X", i, j);
                        myTableModel.setValueAt("X", i + 1, j);
                        myTableModel.setValueAt("X", i + 2, j);
                        myTableModel.setValueAt("X", i + 2, j + 1);
                        myTableModel.setValueAt("X", i + 2, j + 2);
                        break;
                    }
                    case 6 -> {
                        //Left-up corner
                        myTableModel.setValueAt("X", i, j + 2);
                        myTableModel.setValueAt("X", i, j + 1);
                        myTableModel.setValueAt("X", i, j);
                        myTableModel.setValueAt("X", i + 1, j);
                        myTableModel.setValueAt("X", i + 2, j);
                        myTableModel.setValueAt("X", i + 2, j + 2);
                        break;
                    }
                    case 7 -> {
                        //Right-up corner
                        myTableModel.setValueAt("X", i, j);
                        myTableModel.setValueAt("X", i, j + 1);
                        myTableModel.setValueAt("X", i, j + 2);
                        myTableModel.setValueAt("X", i + 1, j + 2);
                        myTableModel.setValueAt("X", i + 2, j + 2);
                        myTableModel.setValueAt("X", i + 2, j);
                        break;
                    }
                    /*case 8 -> {
                        //H
                        myTableModel.setValueAt("X", i, j);
                        myTableModel.setValueAt("X", i + 1, j);
                        myTableModel.setValueAt("X", i + 2, j);
                        myTableModel.setValueAt("X", i + 1, j + 1);
                        myTableModel.setValueAt("X", i + 1, j + 2);
                        myTableModel.setValueAt("X", i + 2, j + 2);
                        myTableModel.setValueAt("X", i, j + 2);
                    }*/
                }
            }
        }
        System.out.println("Columns: " + columns);
        if (columns == 10){
            for (int i = rows / 2 + 1; i < rows; i++) {
                    myTableModel.setValueAt("N", i, columns / 2 - 1);
            }
        }

        int center = columns / 2;
        if (!(rows%5 == 0)) {
            for (int i = 0; i < rows - 2; i++) {
                if (myTableModel.getValueAt(i, center - 1) == "N" && myTableModel.getValueAt(i, center + 1) == "N") {
                    if (i % 4 == 0) {
                        myTableModel.setValueAt("X", i, center - 1);
                        myTableModel.setValueAt("X", i, center);
                    }
                }
            }
        }

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns / 2; col++) {
                int rightCol = columns - col - 1;
                myTableModel.setValueAt(myTableModel.getValueAt(row, col), row, rightCol);
            }
        }

        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++){
                if (i == 0 || j == 0 || i == rows - 1 || j == columns - 1){
                    myTableModel.setValueAt("X", i, j);
                }
            }
        }

    }

    public void createDots(){
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++){
                if (myTableModel.getValueAt(i,j) == "N"){
                    myTableModel.setValueAt(".", i, j);
                    winScore++;
                }
            }
        }
        myTableModel.setValueAt("N", rows - 1, columns - 1);
        myTableModel.setValueAt("N", 0, 0);
        myTableModel.setValueAt("N", 0, columns - 1);
        myTableModel.setValueAt("N", rows - 1, 0);
        //winScore-=3;
    }

    public void createGhosts(){
        if (rows*columns < 625){
            int[] coordinates = findPlace();
            myTableModel.setValueAt("GR", coordinates[0], coordinates[1]);
            ghostRed = new Ghost(coordinates[0], coordinates[1], myTableModel, this);
            ghostRedThread = new Thread(ghostRed);
            ghostRedThread.start();
            ghostsCount = 1;
            colorRender.setGhostRed(ghostRed);
            Ghost.setCount(0);
        }
        else if (rows*columns < 2500){
            int[] coordinates = findPlace();
            myTableModel.setValueAt("GR", coordinates[0], coordinates[1]);
            ghostRed = new Ghost(coordinates[0], coordinates[1], myTableModel, this);
            ghostRedThread = new Thread(ghostRed);
            ghostRedThread.start();
            colorRender.setGhostRed(ghostRed);

            coordinates = findPlace();
            myTableModel.setValueAt("GP", coordinates[0], coordinates[1]);
            ghostPink = new Ghost(coordinates[0], coordinates[1], myTableModel, this);
            ghostPinkThread = new Thread(ghostPink);
            ghostPinkThread.start();
            colorRender.setGhostPink(ghostPink);
            ghostsCount = 2;
            Ghost.setCount(0);
        }
        else if (rows*columns < 5625){
            int[] coordinates = findPlace();
            myTableModel.setValueAt("GR", coordinates[0], coordinates[1]);
            ghostRed = new Ghost(coordinates[0], coordinates[1], myTableModel, this);
            ghostRedThread = new Thread(ghostRed);
            ghostRedThread.start();
            colorRender.setGhostRed(ghostRed);

            coordinates = findPlace();
            myTableModel.setValueAt("GP", coordinates[0], coordinates[1]);
            ghostPink = new Ghost(coordinates[0], coordinates[1], myTableModel, this);
            ghostPinkThread = new Thread(ghostPink);
            ghostPinkThread.start();
            colorRender.setGhostPink(ghostPink);

            coordinates = findPlace();
            myTableModel.setValueAt("GC", coordinates[0], coordinates[1]);
            ghostCyan = new Ghost(coordinates[0], coordinates[1], myTableModel, this);
            ghostCyanThread = new Thread(ghostCyan);
            ghostCyanThread.start();
            colorRender.setGhostCyan(ghostCyan);
            ghostsCount = 3;
            Ghost.setCount(0);
        }
        else {
            int[] coordinates = findPlace();
            myTableModel.setValueAt("GR", coordinates[0], coordinates[1]);
            ghostRed = new Ghost(coordinates[0], coordinates[1], myTableModel, this);
            ghostRedThread = new Thread(ghostRed);
            ghostRedThread.start();
            colorRender.setGhostRed(ghostRed);

            coordinates = findPlace();
            myTableModel.setValueAt("GP", coordinates[0], coordinates[1]);
            ghostPink = new Ghost(coordinates[0], coordinates[1], myTableModel, this);
            ghostPinkThread = new Thread(ghostPink);
            ghostPinkThread.start();
            colorRender.setGhostPink(ghostPink);

            coordinates = findPlace();
            myTableModel.setValueAt("GC", coordinates[0], coordinates[1]);
            ghostCyan = new Ghost(coordinates[0], coordinates[1], myTableModel, this);
            ghostCyanThread = new Thread(ghostCyan);
            ghostCyanThread.start();
            colorRender.setGhostCyan(ghostCyan);

            coordinates = findPlace();
            myTableModel.setValueAt("GO", coordinates[0], coordinates[1]);
            ghostOrange = new Ghost(coordinates[0], coordinates[1], myTableModel, this);
            ghostOrangeThread = new Thread(ghostOrange);
            ghostOrangeThread.start();
            colorRender.setGhostOrange(ghostOrange);
            ghostsCount = 4;
            Ghost.setCount(0);
        }
    }

    public int[] findPlace(){
        for (int i = rows / 2; i < rows; i++){
            for (int j = columns / 2; j < columns; j++){
                if (myTableModel.getValueAt(i, j) == "N"){
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {

            pacmanMove(pacman.getDirection());

            //gameTable.repaint();



            if (health <= 0){
                System.out.println("LOSE!!!");
                SwingUtilities.invokeLater(() -> new LoseWindow());
                dispose();
                pacmanThread.interrupt();
                ghostRedThread.interrupt();
                ghostOrangeThread.interrupt();
                ghostCyanThread.interrupt();
                ghostPinkThread.interrupt();
            }

            if (score == winScore) {
                System.out.println("WIN!!!");
                SwingUtilities.invokeLater(() -> new PlayerSave(score));
                dispose();
                pacmanThread.interrupt();
                ghostRedThread.interrupt();
                ghostOrangeThread.interrupt();
                ghostCyanThread.interrupt();
                ghostPinkThread.interrupt();
            }

            try {

                Thread.sleep(250);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
            pacman.setDirection("LEFT");
        } else if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
            pacman.setDirection("RIGHT");
        } else if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
            pacman.setDirection("UP");
        } else if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
            pacman.setDirection("DOWN");
        }
        pacman.repaint();
        int modifiers = e.getModifiersEx();
        if (modifiers == (KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK) && e.getKeyCode() == KeyEvent.VK_Q) {
            SwingUtilities.invokeLater(() -> new MyWindow());
            ghostRedThread.interrupt();
            ghostOrangeThread.interrupt();
            ghostCyanThread.interrupt();
            ghostPinkThread.interrupt();
            pacmanThread.interrupt();
            dispose();


        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void pacmanMove(String direction){
        /*switch (direction){
            case "RIGHT" -> {
                if (pacman.getColumn() < columns - 1 && !(myTableModel.getValueAt(pacman.getRow(), pacman.getColumn() + 1) == "X")){
                    if (myTableModel.getValueAt(pacman.getRow(), pacman.getColumn() + 1) == "."){
                        score++;
                        System.out.println(score);
                    }
                    myTableModel.setValueAt("N", pacman.getRow(), pacman.getColumn());
                    pacman.setColumn(pacman.getColumn() + 1);
                    myTableModel.setValueAt("O", pacman.getRow(), pacman.getColumn());
                }
            }
            case "LEFT" -> {
                if (pacman.getColumn() > 0  && !(myTableModel.getValueAt(pacman.getRow(), pacman.getColumn() - 1) == "X")){
                    if (myTableModel.getValueAt(pacman.getRow(), pacman.getColumn() - 1) == "."){
                        score++;
                        System.out.println(score);
                    }
                    myTableModel.setValueAt("N", pacman.getRow(), pacman.getColumn());
                    pacman.setColumn(pacman.getColumn() - 1);
                    myTableModel.setValueAt("O", pacman.getRow(), pacman.getColumn());
                }
            }
            case "UP" -> {
                if (pacman.getRow() > 0  && !(myTableModel.getValueAt(pacman.getRow() - 1, pacman.getColumn()) == "X")){
                    if (myTableModel.getValueAt(pacman.getRow() - 1, pacman.getColumn()) == "."){
                        score++;
                        System.out.println(score);
                    }
                    myTableModel.setValueAt("N", pacman.getRow(), pacman.getColumn());
                    pacman.setRow(pacman.getRow() - 1);
                    myTableModel.setValueAt("O", pacman.getRow(), pacman.getColumn());
                }
            }
            case "DOWN" -> {
                if (pacman.getRow() < rows - 1  && !(myTableModel.getValueAt(pacman.getRow() + 1, pacman.getColumn()) == "X")){
                    if (myTableModel.getValueAt(pacman.getRow() + 1, pacman.getColumn()) == "."){
                        score++;
                        System.out.println(score);
                    }
                    myTableModel.setValueAt("N", pacman.getRow(), pacman.getColumn());
                    pacman.setRow(pacman.getRow() + 1);
                    myTableModel.setValueAt("O", pacman.getRow(), pacman.getColumn());
                }
            }
        }*/
        pacman.changeAnimation();
        boolean dummy = moveObject(pacman, direction);
    }

    public synchronized boolean moveObject(Object object, String direction) {
        synchronized (monitor) {
            boolean isMoved = false;
            if (object == pacman) {
                if (canEatWalls) {
                    switch (direction) {
                        case "RIGHT" -> {
                            if (pacman.getColumn() < columns - 1) {
                                if (myTableModel.getValueAt(pacman.getRow(), pacman.getColumn() + 1) == ".") {
                                    score++;
                                    totalScore++;
                                    scoreLabel.setText("Score: " + totalScore);
                                    System.out.println(score);
                                } else if (myTableModel.getValueAt(pacman.getRow(), pacman.getColumn() + 1) == "B") {
                                    Bonus bonus = new Bonus(this);
                                }
                                myTableModel.setValueAt("N", pacman.getRow(), pacman.getColumn());
                                pacman.setColumn(pacman.getColumn() + 1);
                                myTableModel.setValueAt("O", pacman.getRow(), pacman.getColumn());
                            }
                        }
                        case "LEFT" -> {
                            if (pacman.getColumn() > 0) {
                                if (myTableModel.getValueAt(pacman.getRow(), pacman.getColumn() - 1) == ".") {
                                    score++;
                                    totalScore++;
                                    System.out.println(score);
                                    scoreLabel.setText("Score: " + totalScore);
                                }
                                else if (myTableModel.getValueAt(pacman.getRow(), pacman.getColumn() - 1) == "B") {
                                    Bonus bonus = new Bonus(this);
                                }
                                myTableModel.setValueAt("N", pacman.getRow(), pacman.getColumn());
                                pacman.setColumn(pacman.getColumn() - 1);
                                myTableModel.setValueAt("O", pacman.getRow(), pacman.getColumn());
                            }
                        }
                        case "UP" -> {
                            if (pacman.getRow() > 0) {
                                if (myTableModel.getValueAt(pacman.getRow() - 1, pacman.getColumn()) == ".") {
                                    score++;
                                    totalScore++;
                                    System.out.println(score);
                                    scoreLabel.setText("Score: " + totalScore);
                                }
                                else if (myTableModel.getValueAt(pacman.getRow() - 1, pacman.getColumn()) == "B") {
                                    Bonus bonus = new Bonus(this);
                                }
                                myTableModel.setValueAt("N", pacman.getRow(), pacman.getColumn());
                                pacman.setRow(pacman.getRow() - 1);
                                myTableModel.setValueAt("O", pacman.getRow(), pacman.getColumn());
                            }
                        }
                        case "DOWN" -> {
                            if (pacman.getRow() < rows - 1) {
                                if (myTableModel.getValueAt(pacman.getRow() + 1, pacman.getColumn()) == ".") {
                                    score++;
                                    totalScore++;
                                    System.out.println(score);
                                    scoreLabel.setText("Score: " + totalScore);
                                }
                                else if (myTableModel.getValueAt(pacman.getRow() + 1, pacman.getColumn()) == "B") {
                                    Bonus bonus = new Bonus(this);
                                }
                                myTableModel.setValueAt("N", pacman.getRow(), pacman.getColumn());
                                pacman.setRow(pacman.getRow() + 1);
                                myTableModel.setValueAt("O", pacman.getRow(), pacman.getColumn());
                            }
                        }
                    }
                }
                else {
                    switch (direction) {
                        case "RIGHT" -> {
                            if (pacman.getColumn() < columns - 1 && !(myTableModel.getValueAt(pacman.getRow(), pacman.getColumn() + 1) == "X")) {
                                if (myTableModel.getValueAt(pacman.getRow(), pacman.getColumn() + 1) == ".") {
                                    score++;
                                    totalScore++;
                                    scoreLabel.setText("Score: " + totalScore);
                                    System.out.println(score);
                                }
                                else if (myTableModel.getValueAt(pacman.getRow(), pacman.getColumn() + 1) == "B") {
                                    Bonus bonus = new Bonus(this);
                                }
                                myTableModel.setValueAt("N", pacman.getRow(), pacman.getColumn());
                                pacman.setColumn(pacman.getColumn() + 1);
                                myTableModel.setValueAt("O", pacman.getRow(), pacman.getColumn());
                            }
                        }
                        case "LEFT" -> {
                            if (pacman.getColumn() > 0 && !(myTableModel.getValueAt(pacman.getRow(), pacman.getColumn() - 1) == "X")) {
                                if (myTableModel.getValueAt(pacman.getRow(), pacman.getColumn() - 1) == ".") {
                                    score++;
                                    totalScore++;
                                    System.out.println(score);
                                    scoreLabel.setText("Score: " + totalScore);
                                }
                                else if (myTableModel.getValueAt(pacman.getRow(), pacman.getColumn() - 1) == "B") {
                                    Bonus bonus = new Bonus(this);
                                }
                                myTableModel.setValueAt("N", pacman.getRow(), pacman.getColumn());
                                pacman.setColumn(pacman.getColumn() - 1);
                                myTableModel.setValueAt("O", pacman.getRow(), pacman.getColumn());
                            }
                        }
                        case "UP" -> {
                            if (pacman.getRow() > 0 && !(myTableModel.getValueAt(pacman.getRow() - 1, pacman.getColumn()) == "X")) {
                                if (myTableModel.getValueAt(pacman.getRow() - 1, pacman.getColumn()) == ".") {
                                    score++;
                                    totalScore++;
                                    System.out.println(score);
                                    scoreLabel.setText("Score: " + totalScore);
                                }
                                else if (myTableModel.getValueAt(pacman.getRow() - 1, pacman.getColumn()) == "B") {
                                    Bonus bonus = new Bonus(this);
                                }
                                myTableModel.setValueAt("N", pacman.getRow(), pacman.getColumn());
                                pacman.setRow(pacman.getRow() - 1);
                                myTableModel.setValueAt("O", pacman.getRow(), pacman.getColumn());
                            }
                        }
                        case "DOWN" -> {
                            if (pacman.getRow() < rows - 1 && !(myTableModel.getValueAt(pacman.getRow() + 1, pacman.getColumn()) == "X")) {
                                if (myTableModel.getValueAt(pacman.getRow() + 1, pacman.getColumn()) == ".") {
                                    score++;
                                    totalScore++;
                                    System.out.println(score);
                                    scoreLabel.setText("Score: " + totalScore);
                                }
                                else if (myTableModel.getValueAt(pacman.getRow() + 1, pacman.getColumn()) == "B") {
                                    Bonus bonus = new Bonus(this);
                                }
                                myTableModel.setValueAt("N", pacman.getRow(), pacman.getColumn());
                                pacman.setRow(pacman.getRow() + 1);
                                myTableModel.setValueAt("O", pacman.getRow(), pacman.getColumn());
                            }
                        }
                    }
                }
            }else {
                Ghost ghost = (Ghost) object;
                switch (direction) {
                    case "RIGHT" -> {
                        if (ghost.getColumn() < myTableModel.getColumnCount() - 1 && !(myTableModel.getValueAt(ghost.getRow(), ghost.getColumn() + 1) == "X")) {
                            isMoved = true;
                            if (ghost.isPoint()) {
                                myTableModel.setValueAt(".", ghost.getRow(), ghost.getColumn());
                                ghost.setPoint(false);
                            }
                            else if (ghost.isBonus()){
                                myTableModel.setValueAt("B", ghost.getRow(), ghost.getColumn());
                                ghost.setBonus(false);
                            }
                            else {
                                Random random = new Random();

                                int randomNumber = random.nextInt(101);

                                if (randomNumber <= 5) {
                                    myTableModel.setValueAt("B", ghost.getRow(), ghost.getColumn());
                                }
                                else {
                                    myTableModel.setValueAt("N", ghost.getRow(), ghost.getColumn());
                                }
                            }
                            if (myTableModel.getValueAt(ghost.getRow(), ghost.getColumn() + 1) == ".") {
                                ghost.setPoint(true);
                            }
                            else if (myTableModel.getValueAt(ghost.getRow(), ghost.getColumn() + 1) == "B"){
                                ghost.setBonus(true);
                            }
                            ghost.setColumn(ghost.getColumn() + 1);
                        }
                    }
                    case "LEFT" -> {
                        if (ghost.getColumn() > 0 && !(myTableModel.getValueAt(ghost.getRow(), ghost.getColumn() - 1) == "X")) {
                            isMoved = true;
                            if (ghost.isPoint()) {
                                myTableModel.setValueAt(".", ghost.getRow(), ghost.getColumn());
                                ghost.setPoint(false);
                            } else {
                                myTableModel.setValueAt("N", ghost.getRow(), ghost.getColumn());
                            }
                            if (myTableModel.getValueAt(ghost.getRow(), ghost.getColumn() - 1) == ".") {
                                ghost.setPoint(true);
                            }
                            else if (myTableModel.getValueAt(ghost.getRow(), ghost.getColumn() - 1) == "B"){
                                ghost.setBonus(true);
                            }
                            ghost.setColumn(ghost.getColumn() - 1);
                        }
                    }
                    case "UP" -> {
                        if (ghost.getRow() > 0 && !(myTableModel.getValueAt(ghost.getRow() - 1, ghost.getColumn()) == "X")) {
                            isMoved = true;
                            if (ghost.isPoint()) {
                                myTableModel.setValueAt(".", ghost.getRow(), ghost.getColumn());
                                ghost.setPoint(false);
                            } else {
                                myTableModel.setValueAt("N", ghost.getRow(), ghost.getColumn());
                            }
                            if (myTableModel.getValueAt(ghost.getRow() - 1, ghost.getColumn()) == ".") {
                                ghost.setPoint(true);
                            }
                            else if (myTableModel.getValueAt(ghost.getRow() - 1, ghost.getColumn()) == "B"){
                                ghost.setBonus(true);
                            }
                            ghost.setRow(ghost.getRow() - 1);
                        }
                    }
                    case "DOWN" -> {
                        if (ghost.getRow() < myTableModel.getRowCount() - 1 && !(myTableModel.getValueAt(ghost.getRow() + 1, ghost.getColumn()) == "X")) {
                            isMoved = true;
                            if (ghost.isPoint()) {
                                myTableModel.setValueAt(".", ghost.getRow(), ghost.getColumn());
                                ghost.setPoint(false);
                            } else {
                                myTableModel.setValueAt("N", ghost.getRow(), ghost.getColumn());
                            }
                            if (myTableModel.getValueAt(ghost.getRow() + 1, ghost.getColumn()) == ".") {
                                ghost.setPoint(true);
                            }
                            else if (myTableModel.getValueAt(ghost.getRow() + 1, ghost.getColumn()) == "B"){
                                ghost.setBonus(true);
                            }
                            ghost.setRow(ghost.getRow() + 1);
                        }
                    }
                }
            }
            if (ghostsCount == 4) {
                if (pacman.getRow() == ghostRed.getRow() && pacman.getColumn() == ghostRed.getColumn()) {
                    health--;
                    System.out.println(health);
                    livesLabel.setText("Lives: " + health);
                } else if (pacman.getRow() == ghostPink.getRow() && pacman.getColumn() == ghostPink.getColumn()) {
                    health--;
                    System.out.println(health);
                    livesLabel.setText("Lives: " + health);
                } else if (pacman.getRow() == ghostCyan.getRow() && pacman.getColumn() == ghostCyan.getColumn()) {
                    health--;
                    System.out.println(health);
                    livesLabel.setText("Lives: " + health);
                } else if (pacman.getRow() == ghostOrange.getRow() && pacman.getColumn() == ghostOrange.getColumn())
                    health--;
                    System.out.println(health);
                    livesLabel.setText("Lives: " + health);
            }
            else if (ghostsCount == 3){
                if (pacman.getRow() == ghostRed.getRow() && pacman.getColumn() == ghostRed.getColumn()) {
                    health--;
                    System.out.println(health);
                    livesLabel.setText("Lives: " + health);
                } else if (pacman.getRow() == ghostPink.getRow() && pacman.getColumn() == ghostPink.getColumn()) {
                    health--;
                    System.out.println(health);
                    livesLabel.setText("Lives: " + health);
                } else if (pacman.getRow() == ghostCyan.getRow() && pacman.getColumn() == ghostCyan.getColumn()) {
                    health--;
                    System.out.println(health);
                    livesLabel.setText("Lives: " + health);
                }
            }
            else if (ghostsCount == 2){
                if (pacman.getRow() == ghostRed.getRow() && pacman.getColumn() == ghostRed.getColumn()) {
                    health--;
                    System.out.println(health);
                    livesLabel.setText("Lives: " + health);
                } else if (pacman.getRow() == ghostPink.getRow() && pacman.getColumn() == ghostPink.getColumn()) {
                    health--;
                    System.out.println(health);
                    livesLabel.setText("Lives: " + health);
                }
            }
            else if (ghostsCount == 1){
                if (pacman.getRow() == ghostRed.getRow() && pacman.getColumn() == ghostRed.getColumn()) {
                    health--;
                    System.out.println(health);
                    livesLabel.setText("Lives: " + health);
                }
            }
            return isMoved;
        }
    }

    public JLabel getTimeLabel() {
        return timeLabel;
    }

    public int getTime(){
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void doublePoints(){
        totalScore = totalScore * 2;
    }

    public void addHealth(){
        health++;
        livesLabel.setText("Lives: " + health);
    }

    public void setCanEatWalls(boolean canEatWalls) {
        this.canEatWalls = canEatWalls;
    }

    @Override
    public void componentResized(ComponentEvent e) {
        int rowSize = gameTable.getHeight() / rows;
        gameTable.setRowHeight(rowSize);
        int columnSize = gameTable.getWidth() / columns;
        for (int i = 0; i < gameTable.getColumnCount(); i++) {
            gameTable.getColumnModel().getColumn(i).setPreferredWidth(columnSize);
        }
        repaint();
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }
}
