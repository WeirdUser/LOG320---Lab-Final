import java.io.*;
import java.net.*;


class Client {
	public static void main(String[] args) {

        AlphaBetaAlgo AB = new AlphaBetaAlgo();
        String joueur = "2";
        String joueurEnnemy = "4";
         
	Socket MyClient;
	BufferedInputStream input;
	BufferedOutputStream output;
    String[][] board = new String[8][8];
	try {
		MyClient = new Socket("10.196.122.224", 8888);
	   	input    = new BufferedInputStream(MyClient.getInputStream());
		output   = new BufferedOutputStream(MyClient.getOutputStream());
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
	   	while(1 == 1){
			char cmd = 0;
		   	
            cmd = (char)input.read();

            if(cmd == '1'){
                joueur = "2";
                joueurEnnemy = "4";
            }
            else if(cmd =='2'){
                joueur = "4";
                joueurEnnemy = "2";
            }
            		
            // Début de la partie en joueur blanc
            if(cmd == '1'){
                byte[] aBuffer = new byte[1024];
				
				int size = input.available();
				//System.out.println("size " + size);
				input.read(aBuffer,0,size);
                String s = new String(aBuffer).trim();
                System.out.println(s);
                String[] boardValues;
                boardValues = s.split(" ");
                int x=0,y=0;
                for(int i=0; i<boardValues.length;i++){
                    board[x][y] = boardValues[i];
                    x++;
                    if(x == 8){
                        x = 0;
                        y++;
                    }
                }

                System.out.println("Nouvelle partie! Vous jouer blanc, entrez votre premier coup : ");
                String move = null;
                move = AB.getBestCurrentMove(board,joueur);
                board = AB.updateBoard(board, move, joueur);
				output.write(move.getBytes(),0,move.length());
				output.flush();
            }
            // Début de la partie en joueur Noir
            if(cmd == '2'){
                System.out.println("Nouvelle partie! Vous jouer noir, attendez le coup des blancs");
                byte[] aBuffer = new byte[1024];
				
				int size = input.available();
				//System.out.println("size " + size);
				input.read(aBuffer,0,size);
                String s = new String(aBuffer).trim();
                System.out.println(s);
                String[] boardValues;
                boardValues = s.split(" ");
                int x=0,y=0;
                for(int i=0; i<boardValues.length;i++){
                    board[x][y] = boardValues[i];
                    x++;
                    if(x == 8){
                        x = 0;
                        y++;
                    }
                }
            }


			// Le serveur demande le prochain coup
			// Le message contient aussi le dernier coup joué.
			if(cmd == '3'){
				byte[] aBuffer = new byte[16];
				
				int size = input.available();
				//System.out.println("size " + size);
				input.read(aBuffer,0,size);
				
				String s = new String(aBuffer);
				System.out.println("Dernier coup : "+ s);
				board = AB.updateBoard(board, s, joueurEnnemy);
		       	System.out.println("Entrez votre coup : ");
				String move = null;
				move = AB.getBestCurrentMove(board,joueur);
				board = AB.updateBoard(board, move, joueur);
				output.write(move.getBytes(),0,move.length());
				output.flush();
				
			}
			// Le dernier coup est invalide
			if(cmd == '4'){
				System.out.println("Coup invalide, entrez un nouveau coup : ");
		       	String move = null;
				move = console.readLine();
				output.write(move.getBytes(),0,move.length());
				output.flush();
				
			}
        }
	}
	catch (IOException e) {
   		System.out.println(e);
	}
	
    }
}
