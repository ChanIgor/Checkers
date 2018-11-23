package ru.yandex.igor;

import io.javalin.Context;

public class PlayerUI extends Rules{

    private boolean moved;
    private boolean successfullAttack;

    public void movePiece(Context ctx){
        String x = ctx.pathParam("x");
        Integer y = Integer.valueOf(ctx.pathParam("y"));
        String z = ctx.pathParam("z");
        Integer j = Integer.valueOf(ctx.pathParam("j"));
       if(ifEmpty(z, j) && !ifEmpty(x, y) && ifDiagonally(x, y, z, j)){
           if (!ifKing(x, y)){
               if(!ifExceeded(x, y, z, j)){
                   if (ifJustMove(x, y, z, j)) {
                       if (ifRightDirection(x, y, z, j)){
                           PlayBoard.getBoard()[convertInt(j)][convertChar(z)] = PlayBoard.getBoard()[convertInt(y)][convertChar(x)];
                           PlayBoard.getBoard()[convertInt(y)][convertChar(x)] = Figure.EMPTYSPACE;
                           moved = true;
                           successfullAttack = false;
                       }else{
                           ctx.status(401);
                       }
                   }else if(!ifJustMove(x, y, z, j)) {
                       if (movePawn(x, y, z, j)) {
                           PlayBoard.getBoard()[convertInt(j)][convertChar(z)] = PlayBoard.getBoard()[convertInt(y)][convertChar(x)];
                           PlayBoard.getBoard()[convertInt(y)][convertChar(x)] = Figure.EMPTYSPACE;
                           moved = true;
                           successfullAttack = true;
                       }else{
                           ctx.status(401);
                       }
                   }
               }else{
                   ctx.status(401);
               }
           }else if(ifKing(x, y)){
               moveKing(x, y, z, j);
               moved = true;
           }
       }else{
           ctx.status(401);
       }
   }
   public void notMoved(){
        moved = false;
   }

    public boolean ifMoved(){
        return moved;
    }

    public boolean ifAttackSuccess(){
        return successfullAttack;
    }


    public boolean movePawn(String x, int y, String z, int j){
       int rowFrom = convertInt(y);
       int columnFrom = convertChar(x);
       int rowTo = convertInt(j);
       int columnTo = convertChar(z);
       if((rowFrom > rowTo && columnFrom < columnTo) || (rowFrom < rowTo && columnFrom > columnTo)){
           if(PlayBoard.getBoard()[Math.min(rowFrom, rowTo) + 1][Math.max(columnFrom, columnTo) - 1] != Figure.EMPTYSPACE){
               if(PlayBoard.getBoard()[Math.min(rowFrom, rowTo) + 1][Math.max(columnFrom, columnTo) - 1].ifWhite() == PlayBoard.getBoard()[rowFrom][columnFrom].ifWhite()){
                   return false;
               }else{
                   PlayBoard.getBoard()[Math.min(rowFrom, rowTo) + 1][Math.max(columnFrom, columnTo) - 1] = Figure.EMPTYSPACE;
                   return true;
               }
           }else{
               return false;
           }
       }else if((rowFrom > rowTo && columnFrom > columnTo) || (rowFrom < rowTo && columnFrom < columnTo)){
           if(PlayBoard.getBoard()[Math.min(rowFrom, rowTo) + 1][Math.min(columnFrom, columnTo) + 1] != Figure.EMPTYSPACE){
               if(PlayBoard.getBoard()[Math.min(rowFrom, rowTo) + 1][Math.min(columnFrom, columnTo) + 1].ifWhite() == PlayBoard.getBoard()[rowFrom][columnFrom].ifWhite()){
                   return false;
               }else{
                   PlayBoard.getBoard()[Math.min(rowFrom, rowTo) + 1][Math.min(columnFrom, columnTo) + 1] = Figure.EMPTYSPACE;
                   return true;
               }
           }else{
               return false;
           }
       }else {
           throw new UncheckedCheckersException();
       }
    }



    public void moveKing(String x, int y, String z, int j){
        int rowFrom = convertInt(y);
        int columnFrom = convertChar(x);
        int rowTo = convertInt(j);
        int columnTo = convertChar(z);
        int counter = 0;
        int rowBuff = 0;
        int columnBuff = 0;

        if((rowFrom > rowTo && columnFrom < columnTo) || (rowFrom < rowTo && columnFrom > columnTo)){
            int i = Math.min(rowFrom, rowTo) + 1;
            int n = Math.max(columnFrom, columnTo) - 1;
            while(i < Math.max(rowFrom, rowTo) && n > Math.min(columnFrom, columnTo)){
                if(PlayBoard.getBoard()[i][n] != Figure.EMPTYSPACE){
                    if(PlayBoard.getBoard()[i][n].ifWhite() != PlayBoard.getBoard()[rowFrom][columnFrom].ifWhite()){
                        counter++;
                        rowBuff = i;
                        columnBuff = n;
                    }else if(PlayBoard.getBoard()[i][n].ifWhite() == PlayBoard.getBoard()[rowFrom][columnFrom].ifWhite()){
                        throw new UncheckedCheckersException();
                    }
                }
                i++;
                n--;
            }
        }else if((rowFrom > rowTo && columnFrom > columnTo) || (rowFrom < rowTo && columnFrom < columnTo)){
            int i = Math.min(rowFrom, rowTo) + 1;
            int n = Math.min(columnFrom, columnTo) + 1;
            while(i < Math.max(rowFrom, rowTo) && n < Math.max(columnFrom, columnTo)){
                if(PlayBoard.getBoard()[i][n] != Figure.EMPTYSPACE){
                    if(PlayBoard.getBoard()[i][n].ifWhite() != PlayBoard.getBoard()[rowFrom][columnFrom].ifWhite()){
                        counter++;
                        rowBuff = i;
                        columnBuff = n;
                    }else if(PlayBoard.getBoard()[i][n].ifWhite() == PlayBoard.getBoard()[rowFrom][columnFrom].ifWhite()){
                        throw new UncheckedCheckersException();
                    }
                }
                i++;
                n++;
            }
        }

        if(counter == 1){
            PlayBoard.getBoard()[rowTo][columnTo] = PlayBoard.getBoard()[rowFrom][columnFrom];
            PlayBoard.getBoard()[rowFrom][columnFrom] = Figure.EMPTYSPACE;
            PlayBoard.getBoard()[rowBuff][columnBuff] = Figure.EMPTYSPACE;
            successfullAttack = true;
        }else if(counter == 0){
            PlayBoard.getBoard()[rowTo][columnTo] = PlayBoard.getBoard()[rowFrom][columnFrom];
            PlayBoard.getBoard()[rowFrom][columnFrom] = Figure.EMPTYSPACE;
            successfullAttack = false;
        }else{
            throw new UncheckedCheckersException();
        }
    }






}
