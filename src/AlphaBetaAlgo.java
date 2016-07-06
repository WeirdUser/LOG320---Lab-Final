/**
 * Created by Robin on 2016-07-05.
 */
public class AlphaBetaAlgo {

    char precisionLvl = 3;

    // instance used to find all the possibles boards
    PossibleBoard boards = null;

    // tableau alphabetiqur
    char[] lettres = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q',
            'R','S','T','U','V','W','X','Y','Z'};

    // base constructor
    public AlphaBetaAlgo(){

    }

    public String getBestCurrentMove(String[][] board, String player){
        // function called by the AI to get the best possible move to make against the other Ai
        // at the current location and with the current information available

        // priority
        int highestPriority = -1;
        // best board
        String[][] bestBoard = null;

        // arrival position
        String coord1 = "";
        // starting position
        String coord2 = "";

        // create the object and generate all the next possible boards
        boards = new PossibleBoard(board,player);
        boards.generateNextBoards();

        // pass in all the possible moves(boards) (up to a selected level of precision. here: 3)
        for (PossibleBoard item : boards.nextBoards) {
            int boardPriority = determineMovePriority();
            if(boardPriority > highestPriority){
                highestPriority = boardPriority;
                bestBoard = item.currentBoard;
            }
        }

        // get the actual move made in the best board and format it to send it back to the server
        for(int i = 0; i < board.length; ++i){
            for(int j = 0; j < board[i].length; ++j){
                if(board[i][j] != bestBoard[i][j]){
                    if(board[i][j] == "0"){
                        coord2 = lettres[j] + Integer.toString(i);
                    }
                    else {
                        coord1 = lettres[j] + Integer.toString(i);
                    }
                }
            }
        }

        // send back the String
        if(coord1 != "" && coord2 != ""){
            return coord1 + " - " + coord2;
        }
        else{
            return "1 - 1";
        }

    }

    private int determineMovePriority(){
        //TODO: calculate the priority level of the movement depending on a lot of differents points
        //TODO: to take into consideration. * this is your part Marc-Olivier

        return 1;
    }
}
