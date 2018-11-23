package ru.yandex.igor;
import io.javalin.Context;


public class PlayBoard {
    private static final int ROW = 8;
    private static final int COLUMN = 8;
    private static Figure board[][];

    public PlayBoard(){
        board = new Figure[ROW][COLUMN];
        placeFigure();
        fillEmptySpaces();
    }

    public static void getNewBoard(){
        board = new Figure[ROW][COLUMN];
        placeFigure();
        fillEmptySpaces();
    }

    public void showBoard(){
        for(int x = 0; x < ROW; x++){
            for(int y = 0; y < COLUMN; y++){
                System.out.print(board[x][y].getFigureUnicode());
            }
            System.out.println();
        }
    }

    public void showBoardinBrowser(Context ctx){
        StringBuilder doska = new StringBuilder();
        for(int x = 0; x < ROW; x++){
            for(int y = 0; y < COLUMN; y++){
                doska.append(getBoard()[x][y].getFigureUnicode());
            }
            doska.append("\n");
        }
        ctx.result(doska.toString());
    }

    public static Figure[][] getBoard(){
        return board;
    }

    public static void placeFigure(){
        for(int x = 0; x < ROW; x++){
            if(x == 0 || x == 2){
                for(int y = 1; y < COLUMN; y+=2){
                    board[x][y] = Figure.WHITEFIGURE;
                }
            }else if(x == 1){
                for(int y = 0; y < COLUMN; y+=2){
                    board[x][y] = Figure.WHITEFIGURE;
                }
            }else if(x == 5 || x == 7){
                for(int y = 0; y < COLUMN; y+=2){
                    board[x][y] = Figure.BLACKFIGURE;
                }
            }else if(x == 6){
                for(int y = 1; y < COLUMN; y+=2){
                    board[x][y] = Figure.BLACKFIGURE;
                }
            }
        }
    }
    public static void fillEmptySpaces(){
        for(int x = 0; x < ROW; x++){
            for(int y = 0; y < COLUMN; y++){
                if(board[x][y] != Figure.WHITEFIGURE && board[x][y] != Figure.BLACKFIGURE){
                    board[x][y] = Figure.EMPTYSPACE;
                }
            }
        }
    }


}
