package ru.yandex.igor;

import io.javalin.Context;

public class Rules{

    private int rightX = 0;
    private int rightY = 0;

    private static boolean turn = true;
    private boolean kingGoOn = false;
    private boolean pawnGoOn = false;
    private final int topRowBound = 0;
    private final int bottomRowBound = 7;
    private final int rightColumnBound = 7;
    private final int leftColumnBound = 0;


    public void turnToKing(){
        for(int y = 0; y < PlayBoard.getBoard().length; y++){
            if(PlayBoard.getBoard()[0][y] != Figure.EMPTYSPACE) {
                if (PlayBoard.getBoard()[0][y].ifWhite() == "B") {
                    PlayBoard.getBoard()[0][y] = Figure.BLACKKING;
                }
            }
        }
        for(int y = 0; y < PlayBoard.getBoard().length; y++){
            if(PlayBoard.getBoard()[7][y] != Figure.EMPTYSPACE) {
                if (PlayBoard.getBoard()[7][y].ifWhite() == "W") {
                    PlayBoard.getBoard()[7][y] = Figure.WHITEKING;
                }
            }
        }
    }



    public boolean ifBlackWin(){
        int whiteFigures = 0;
        for(int x = 0; x < PlayBoard.getBoard().length; x++){
            for(int y = 0; y < PlayBoard.getBoard().length; y++){
                if(PlayBoard.getBoard()[x][y] != Figure.EMPTYSPACE){
                    if(PlayBoard.getBoard()[x][y].ifWhite() == "W"){
                        whiteFigures++;
                    }
                }
            }
        }
        if(whiteFigures == 0){
            return true;
        }else {
            return false;
        }
    }

    public boolean ifWhitewin(){
        int blackFigures = 0;
        for(int x = 0; x < PlayBoard.getBoard().length; x++){
            for(int y = 0; y < PlayBoard.getBoard().length; y++){
                if(PlayBoard.getBoard()[x][y] != Figure.EMPTYSPACE){
                    if(PlayBoard.getBoard()[x][y].ifWhite() == "B"){
                        blackFigures++;
                    }
                }
            }
        }
        if(blackFigures == 0){
            return true;
        }else {
            return false;
        }
    }

    public boolean ifRightDirection(String x, int y, String z, int j){
        int rowFrom = convertInt(y);
        int columnFrom = convertChar(x);
        int rowTo = convertInt(j);
        int columnTo = convertChar(z);

        if(PlayBoard.getBoard()[rowFrom][columnFrom].ifWhite() == "B") {
            if(rowFrom < rowTo){
                return false;
            }else{
                return true;
            }
        }else if(PlayBoard.getBoard()[rowFrom][columnFrom].ifWhite() == "W") {
            if (rowFrom > rowTo) {
                return false;
            }else{
                return true;
            }
        }else{
            throw new UncheckedCheckersException();
        }
    }

    public boolean ifDiagonally(String x, int y, String z, int j){
        int rowFrom = convertInt(y);
        int columnFrom = convertChar(x);
        int rowTo = convertInt(j);
        int columnTo = convertChar(z);

        if(Math.max(rowFrom, rowTo) - Math.min(rowFrom, rowTo) == Math.max(columnFrom, columnTo) - Math.min(columnTo, columnFrom)){
            return true;
        }else {
            return false;
        }
    }

    public boolean ifEmpty(String z, int j){
        if(PlayBoard.getBoard()[convertInt(j)][convertChar(z)] == Figure.EMPTYSPACE){
            return true;
        }else {
            return false;
        }
    }

    public boolean ifExceeded(String x, int y, String z, int j){
        int rowFrom = convertInt(y);
        int columnFrom = convertChar(x);
        int rowTo = convertInt(j);
        int columnTo = convertChar(z);
        if(Math.max(rowFrom, rowTo) - Math.min(rowFrom, rowTo) > 2 || Math.max(columnFrom, columnTo) - Math.min(columnTo, columnFrom) > 2){
            return true;
        }else{
            return false;
        }
    }

    public boolean ifJustMove(String x, int y, String z, int j){
        int rowFrom = convertInt(y);
        int columnFrom = convertChar(x);
        int rowTo = convertInt(j);
        int columnTo = convertChar(z);

        if(Math.max(rowFrom, rowTo) - Math.min(rowFrom, rowTo) == 2 && Math.max(columnFrom, columnTo) - Math.min(columnTo, columnFrom) == 2){
            return false;
        }else if(Math.max(rowFrom, rowTo) - Math.min(rowFrom, rowTo) == 1 && Math.max(columnFrom, columnTo) - Math.min(columnTo, columnFrom) == 1){
            return true;
        }else {
            throw new UncheckedCheckersException();
        }
    }


    public boolean ifKing(String x, int y){
        int rowFrom = convertInt(y);
        int columnFrom = convertChar(x);

        if(PlayBoard.getBoard()[rowFrom][columnFrom] == Figure.BLACKKING || PlayBoard.getBoard()[rowFrom][columnFrom] == Figure.WHITEKING){
            return true;
        }else{
            return false;
        }
    }

    public void changeTurn(){
            if (turn == true) {
                turn = false;
            } else if (turn == false) {
                turn = true;
            }
    }
    public boolean getTurn(){
        return turn;
    }

    public boolean pawnCanGoOn(){
        return pawnGoOn;
    }

    public void ifPawnCanGoOn(Context ctx){
        pawnGoOn = false;
        int x = convertInt(Integer.valueOf(ctx.pathParam("j")));
        int y = convertChar(ctx.pathParam("z"));
        if(x - 2 >= topRowBound && y + 2 <= rightColumnBound){
            if (PlayBoard.getBoard()[x - 1][y + 1] != Figure.EMPTYSPACE) {
                if (PlayBoard.getBoard()[x - 1][y + 1].ifWhite() != PlayBoard.getBoard()[x][y].ifWhite()) {
                    if (PlayBoard.getBoard()[x - 2][y + 2] == Figure.EMPTYSPACE) {
                        pawnGoOn = true;
                    }
                }
            }
        }

        if(x - 2 >= topRowBound && y - 2 >= leftColumnBound) {
            if (PlayBoard.getBoard()[x - 1][y - 1] != Figure.EMPTYSPACE) {
                if (PlayBoard.getBoard()[x - 1][y - 1].ifWhite() != PlayBoard.getBoard()[x][y].ifWhite()) {
                    if (PlayBoard.getBoard()[x - 2][y - 2] == Figure.EMPTYSPACE) {
                        pawnGoOn = true;
                    }
                }
            }
        }
        if(x + 2 <= bottomRowBound && y - 2 >= leftColumnBound) {
            if (PlayBoard.getBoard()[x + 1][y - 1] != Figure.EMPTYSPACE) {
                if (PlayBoard.getBoard()[x + 1][y - 1].ifWhite() != PlayBoard.getBoard()[x][y].ifWhite()) {
                    if (PlayBoard.getBoard()[x + 2][y - 2] == Figure.EMPTYSPACE) {
                        pawnGoOn = true;
                    }
                }
            }
        }
        if(x + 2 <= bottomRowBound && y + 2 <= rightColumnBound) {
            if (PlayBoard.getBoard()[x + 1][y + 1] != Figure.EMPTYSPACE) {
                if (PlayBoard.getBoard()[x + 1][y + 1].ifWhite() != PlayBoard.getBoard()[x][y].ifWhite()) {
                    if (PlayBoard.getBoard()[x + 2][y + 2] == Figure.EMPTYSPACE) {
                        pawnGoOn = true;
                    }
                }
            }
        }
    }

    public boolean kingCanGoOn(){
        return kingGoOn;
    }

    public void canNotGoOn(){
        pawnGoOn = false;
        kingGoOn = false;
    }

    public void ifKingCanGoOn(Context ctx){
        kingGoOn = false;
        int x = convertInt(Integer.valueOf(ctx.pathParam("j")));
        int y = convertChar(ctx.pathParam("z"));
        int i = x - 1;
        int n = y + 1;
        while(i > topRowBound && n < rightColumnBound){
            if(PlayBoard.getBoard()[i][n] != Figure.EMPTYSPACE){
                if(PlayBoard.getBoard()[i][n].ifWhite() != PlayBoard.getBoard()[x][y].ifWhite()){
                    if(PlayBoard.getBoard()[i - 1][n + 1] == Figure.EMPTYSPACE){
                        kingGoOn = true;
                        break;
                    }else{
                        kingGoOn = false;
                        break;
                    }
                }
            }
            n++;
        }
        i--;
        if(kingGoOn == false) {
            i = x - 1;
            n = y - 1;
            while (i > topRowBound && n > leftColumnBound) {
                if (PlayBoard.getBoard()[i][n] != Figure.EMPTYSPACE) {
                    if (PlayBoard.getBoard()[i][n].ifWhite() != PlayBoard.getBoard()[x][y].ifWhite()) {
                        if (PlayBoard.getBoard()[i - 1][n - 1] == Figure.EMPTYSPACE) {
                            kingGoOn = true;
                            break;
                        } else {
                            kingGoOn = false;
                            break;
                        }
                    }
                }
                i--;
                n--;
            }
        }
        if(kingGoOn == false) {
            i = x + 1;
            n = y + 1;
            while (i < bottomRowBound && n < rightColumnBound) {
                if (PlayBoard.getBoard()[i][n] != Figure.EMPTYSPACE) {
                    if (PlayBoard.getBoard()[i][n].ifWhite() != PlayBoard.getBoard()[x][y].ifWhite()) {
                        if (PlayBoard.getBoard()[i + 1][n + 1] == Figure.EMPTYSPACE) {
                            kingGoOn = true;
                            break;
                        } else {
                            kingGoOn = false;
                            break;
                        }
                    }
                }
                i++;
                n++;
            }
        }
        if(kingGoOn == false) {
            i = x + 1;
            n = y - 1;
            while (i < bottomRowBound && n > leftColumnBound) {
                if (PlayBoard.getBoard()[i][n] != Figure.EMPTYSPACE) {
                    if (PlayBoard.getBoard()[i][n].ifWhite() != PlayBoard.getBoard()[x][y].ifWhite()) {
                        if (PlayBoard.getBoard()[i + 1][n - 1] == Figure.EMPTYSPACE) {
                            kingGoOn = true;
                            break;
                        }else {
                            kingGoOn = false;
                            break;
                        }
                    }
                }
                i++;
                n--;
            }
        }
    }

    public void ifMovedRightOne(Context ctx){
        rightX = convertInt(Integer.valueOf(ctx.pathParam("j")));
        rightY = convertChar(ctx.pathParam("z"));
    }

    public boolean isMovedRightOne(Context ctx){
        int row =  convertInt(Integer.valueOf(ctx.pathParam("y")));
        int column = convertChar(ctx.pathParam("x"));

        if((row == rightX) && (column == rightY)){
            return true;
        }else{
            return false;
        }
    }

    public int convertChar(String x){
        switch (x) {
            case "a": case "A": return 0;
            case "b": case "B": return 1;
            case "c": case "C": return 2;
            case "d": case "D": return 3;
            case "e": case "E": return 4;
            case "f": case "F": return 5;
            case "g": case "G": return 6;
            case "h": case "H": return 7;
            default: throw new UncheckedCheckersException();
        }
    }
    public int convertInt(int y){
        switch (y) {
            case 1: return 7;
            case 2: return 6;
            case 3: return 5;
            case 4: return 4;
            case 5: return 3;
            case 6: return 2;
            case 7: return 1;
            case 8: return 0;
            default: throw new UncheckedCheckersException();
        }
    }
}
