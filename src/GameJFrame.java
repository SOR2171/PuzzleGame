import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GameJFrame extends JFrame implements KeyListener, ActionListener {

    int[][] data = new int[4][4];
    String[] imageType = {"sacabam\\", "bottle\\"};

    String path;
    int imageTypeIndex = 0;

    int x;
    int y;

    JMenuItem change0Item = new JMenuItem("萨卡板斯卡蒂");
    JMenuItem change1Item = new JMenuItem("蓝色玻璃杯");

    JMenuItem replayItem = new JMenuItem("重新游戏");
    JMenuItem closeItem = new JMenuItem("关闭游戏");

    JMenuItem helpItem = new JMenuItem("游玩方法");
    JMenuItem adItem = new JMenuItem("关于我们");

    public GameJFrame() {
        initJFrame();

        initJMenu();

        initData();

        initImage();

        this.setVisible(true);
    }

    private void initData() {
        int[] tempArr = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};

        Random r = new Random();

        for (int i = 0; i < tempArr.length; i++) {
            int index = r.nextInt(tempArr.length);

            int temp = tempArr[i];
            tempArr[i] = tempArr[index];
            tempArr[index] = temp;
        }

        for (int i = 0; i < tempArr.length; i++) {
            if (tempArr[i] == 0) {
                x = i / 4;
                y = i % 4;
            }
            data[i / 4][i % 4] = tempArr[i];
        }
    }

    private void initJFrame() {
        this.setSize(615, 660);
        this.setTitle("拼图游戏 V1.0");
        //this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.addKeyListener(this);
    }

    private void initJMenu() {
        JMenuBar jMenuBar = new JMenuBar();

        JMenu functionJMenu = new JMenu("菜单");
        JMenu helpJMenu = new JMenu("帮助");

        JMenu changeJMenu = new JMenu("更换图片");

        changeJMenu.add(change0Item);
        changeJMenu.add(change1Item);

        functionJMenu.add(changeJMenu);
        functionJMenu.add(replayItem);
        functionJMenu.add(closeItem);

        helpJMenu.add(helpItem);
        helpJMenu.add(adItem);

        jMenuBar.add(functionJMenu);
        jMenuBar.add(helpJMenu);
        this.setJMenuBar(jMenuBar);

        //绑定事件
        replayItem.addActionListener(this);
        closeItem.addActionListener(this);
        helpItem.addActionListener(this);
        adItem.addActionListener(this);

        change0Item.addActionListener(this);
        change1Item.addActionListener(this);
    }

    private void initImage() {
        this.getContentPane().removeAll();
        path = "src\\image\\" + imageType[imageTypeIndex];
        int num;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                num = data[i][j];
                JLabel jLabel = new JLabel(new ImageIcon(path + num + ".png"));
                jLabel.setBounds(150 * j, 150 * i, 150, 150);
                this.getContentPane().add(jLabel);
            }
        }
        this.getContentPane().repaint();
    }

    private void createDialog(String s) {

        JDialog jDialog = new JDialog();

        JLabel jLabel = new JLabel(s);
        jLabel.setBounds(0, 0, 256, 128);
        jDialog.setSize(256, 128);

        jDialog.getContentPane().add(jLabel);
        jDialog.setAlwaysOnTop(true);
        jDialog.setLocationRelativeTo(null);
        jDialog.setModal(true);
        jDialog.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == 70) {
            this.getContentPane().removeAll();
            JLabel all = new JLabel(new ImageIcon(path + "all.png"));
            all.setBounds(0, 0, 600, 600);
            this.getContentPane().add(all);
            this.getContentPane().repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //a w d s 65 87 68 83
        //up    38
        //down  40
        //left  37
        //right 39
        //f 70
        //l 76
        int code = e.getKeyCode();
        switch (code) {
            case 37:
            case 65:
                if (y == 3) {
                    return;
                }
                data[x][y] = data[x][y + 1];
                data[x][y + 1] = 0;
                y++;
                initImage();
                break;
            case 38:
            case 87:
                if (x == 3) {
                    return;
                }
                data[x][y] = data[x + 1][y];
                data[x + 1][y] = 0;
                x++;
                initImage();
                break;
            case 39:
            case 68:
                if (y == 0) {
                    return;
                }
                data[x][y] = data[x][y - 1];
                data[x][y - 1] = 0;
                y--;
                initImage();
                break;
            case 40:
            case 83:
                if (x == 0) {
                    return;
                }
                data[x][y] = data[x - 1][y];
                data[x - 1][y] = 0;
                x--;
                initImage();
                break;
            case 70:
                initImage();
                break;
            case 76:
                data = new int[][]{
                        {1, 2, 3, 4},
                        {5, 6, 7, 8},
                        {9, 10, 11, 12},
                        {13, 14, 15, 0}
                };
                x = 3;
                y = 3;
                initImage();
                break;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if (obj == replayItem) {
            initData();
            initImage();
        } else if (obj == closeItem) {
            System.exit(0);
        } else if (obj == helpItem) {
            createDialog("<html><body>用“上下左右”或wasd移动拼图块<br>" +
                         "按F键查看原图<br>" +
                         "按F键直接获胜<br>" +
                         "<br>" +
                         "*可能会出现无法获胜的情况</body></html>");
        } else if (obj == adItem) {
            createDialog("https://github.com/SOR2171/PuzzleGame");
        } else if (obj == change0Item) {
            imageTypeIndex = 0;
            initImage();
        } else if (obj == change1Item) {
            imageTypeIndex = 1;
            initImage();
        }
    }
}