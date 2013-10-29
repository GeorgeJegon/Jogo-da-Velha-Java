package br.fmu.aula.test;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class JogoVelha {
    private JButton listButtons[];
    private JFrame Stage;
    private JPanel topPanel,centerPanel;
    private String players[] = {"x","o"};
    private String StageControl[][] = {{"","",""},{"","",""},{"","",""}};
    private int currentPlayer;
    private boolean gameRunning = false;
    
    public void startGame(){
        this.initComponents();
        this.gameRunning = true;
        this.currentPlayer = this.getRandomBinary();
    }//END FUNCTION
    
    private void restartGame(){
        this.cleanStageControl();
        this.enableAllButtons();
        this.gameRunning = true;
        this.currentPlayer = this.getRandomBinary();
    }//END FUCNTION
    
    private void endGame(){
        if(this.gameRunning){
            if(JOptionPane.showConfirmDialog(null, "Há uma partida acontecendo no momento!\nDeseja realmente encerrar?","Sair Jogo",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
                this.Stage.dispose();    
            }//END IF
        }else if(JOptionPane.showConfirmDialog(null, "Deseja realmente sair do Jogo?","Sair Jogo",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
            this.Stage.dispose();
        }//END IF
    }//END FUNCTION
    
    private void cleanStageControl(){
        for(int i=0,j=this.StageControl.length;i<j;i++){
            for(int x=0,y=this.StageControl[i].length;x<y;x++){
                this.StageControl[i][x] = "";
            }//END FOR
        }//END FOR
    }//END FUNCTION
    
    private void switchPlayer(){
        this.currentPlayer = (this.currentPlayer==0?1:0);
    }//END FUNCTION
    
    private int getRandomBinary(){
        return (int) Math.floor(Math.random()*9)%2;
    }//END FUNCTION
    
    private void initComponents(){
        this.initStage();
        this.initPanels();
        this.initButtons();
    }//END FUNCTION
    
    private void initStage(){
        this.Stage = new JFrame("Jogo da Velha - Aula Java FMU");
        this.Stage.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.Stage.setLayout(new BorderLayout());
        this.Stage.setSize(300,300);
        this.Stage.setResizable(false);
        this.Stage.setLocationRelativeTo(null);
        this.Stage.setVisible(true);
        this.Stage.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                if(JOptionPane.showConfirmDialog(null, "Deseja realmente fechar a aplicação?","Sair Jogo",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
                    e.getWindow().dispose();
                }//END IF
            }//END FUNCTION
        });
    }//END FUNCTION
    
    private void initPanels(){
        this.startTopPanel();
        this.startCenterPanel();
    }//END FUNCTION
    
    private void startTopPanel(){
        JButton newGame = new JButton("Novo Jogo"), exitGame = new JButton("Sair");
        this.topPanel = new JPanel();
        this.topPanel.setLayout(new FlowLayout());
        
        newGame.setActionCommand("newGame");
        newGame.addActionListener(this.getHandlerButton());
        
        exitGame.setActionCommand("exitGame");
        exitGame.addActionListener(this.getHandlerButton());
        
        this.topPanel.add(newGame);
        this.topPanel.add(exitGame);
        this.Stage.getContentPane().add(this.topPanel,BorderLayout.NORTH);
    }//END FUNCTION
    
    private void startCenterPanel(){
        this.centerPanel = new JPanel();
        this.centerPanel.setLayout(new GridLayout(3,3,5,5));
        this.Stage.getContentPane().add(this.centerPanel);
    }//END FUNCTION
        
    private void initButtons(){
        Dimension d = new Dimension(115,80);
        Font f = new Font("Verdana", Font.BOLD, 15);
        this.listButtons = new JButton[9];
        for(int i=0;i<9;i++){
            this.listButtons[i] = new JButton();
            this.listButtons[i].setActionCommand("playerGameButton");
            this.listButtons[i].setText("");
            this.listButtons[i].setName(""+i);
            this.listButtons[i].setFont(f);
            this.listButtons[i].setPreferredSize(d);
            this.listButtons[i].addActionListener(this.getHandlerButton());
            this.centerPanel.add(this.listButtons[i]);
        }//END FOR
    }//END FUNCTION
    
    private void pushStageControl(int i,String p){
        int x = (i/3),y = (i%3);
        if(this.StageControl[x][y].equals("")){
            this.StageControl[x][y] = p;
        }//END IF
    }//END FUNCTION
    
    private boolean checkArrayWinVertical(boolean a[]){
        for(boolean b:a){
            if(b){
                return true;
            }//END IF
        }//END FOR
        return false;
    }//END FUNCTION
    
    private boolean checkWin(String p){
        int dgP =0, dgS = 0,pos=0;
        boolean v[] = {true,true,true};
        for(int i=0,j=this.StageControl.length,k=(j-1);i<j;i++){
            boolean h = true;
            for(int x=0,y=this.StageControl[i].length;x<y;x++){
                String c = this.StageControl[i][x];
                int modResult = (pos%j);
                if(i==x && c.equals(p)){
                    dgP++;
                    if((i+x)==k){
                        dgS++;
                    }//END IF
                }else if((i+x)==k && c.equals(p)){
                    dgS++;
                }//END IF
                h  = h && c.equals(p);
                v[modResult] = v[modResult] && c.equals(p);
                pos++;
            }//END FOR
            if(h){
                return true;
            }//END IF
        }//END FOR
        return (this.checkArrayWinVertical(v) || dgS==3 || dgP==3);
    }//END FUNCTION
    
    private boolean checkComplete(){
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(!this.StageControl[i][j].equals("")){
                    return false;
                }//END IF
            }//END IF
        }//END IF
        return true;
    }//END FUNCTION
    
    private void disableAllButtons(){
        for(JButton btn:this.listButtons){
            if(btn.isEnabled()){
                btn.setEnabled(false);
            }//END IF
        }//END FOREACH
    }//END FUNCTION
    
    private void enableAllButtons(){
        for(JButton btn:this.listButtons){
            if(!btn.isEnabled()){
                btn.setText("");
                btn.setEnabled(true);
            }//END IF
        }//END FOREACH
    }//END FUNCTION
    
    private ActionListener getHandlerButton(){        
       final JogoVelha app = this;
       return new ActionListener(){
            public void actionPerformed(ActionEvent e){
                JButton btn = (JButton) e.getSource();
                String action = btn.getActionCommand();
                if(action.equals("playerGameButton")){
                    String player = app.players[app.currentPlayer];
                    btn.setText(player);
                    app.pushStageControl(Integer.parseInt(btn.getName()), player);
                    if(app.checkWin(player)){
                        JOptionPane.showMessageDialog(null,"O jogador "+player+", venceu esta partida!");
                        app.disableAllButtons();
                    }else if(app.checkComplete()){
                        JOptionPane.showMessageDialog(null,"Não houve ganhadores nesta partida!");
                        app.disableAllButtons();
                    }else{
                        app.switchPlayer();    
                        btn.setEnabled(false);
                    }//END IF   
                }else if(action.equals("newGame")){
                    app.restartGame();
                }else if(action.equals("exitGame")){
                    app.endGame();
                }//END IF
            }//END FUNCTION
        };
    }//END FUNCTION
}//END CLASS