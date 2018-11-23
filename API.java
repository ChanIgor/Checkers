package ru.yandex.igor;

import io.javalin.Javalin;

import java.util.Scanner;


public class API {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7000);
        Scanner scanner = new Scanner(System.in);
        PlayBoard board = new PlayBoard();
        GetStarted start = new GetStarted();
        PlayerUI action = new PlayerUI();

        /*
            post two users, specify name and color "true or false" true for white
        */

        app.post("/:post/:name/:color", ctx -> {
            PlayerController.postPlayer(ctx);
        });

        

        app.get("/:new/:game/:start", ctx -> PlayBoard.getNewBoard());


        if(start.ifStarted()) {
            app.get("/:x/:y/:z/:j", ctx -> {
                action.notMoved();
                if (!action.pawnCanGoOn() && !action.kingCanGoOn()) {
                    if (action.getTurn() && PlayBoard.getBoard()[action.convertInt(Integer.valueOf(ctx.pathParam("y")))][action.convertChar(ctx.pathParam("x"))].ifWhite() == "W") {
                        action.movePiece(ctx);
                    } else if (!action.getTurn() && PlayBoard.getBoard()[action.convertInt(Integer.valueOf(ctx.pathParam("y")))][action.convertChar(ctx.pathParam("x"))].ifWhite() == "B") {
                        action.movePiece(ctx);
                    } else {
                        ctx.status(401);
                    }
                }else{
                    if (action.isMovedRightOne(ctx)) {
                        if (action.getTurn() && PlayBoard.getBoard()[action.convertInt(Integer.valueOf(ctx.pathParam("y")))][action.convertChar(ctx.pathParam("x"))].ifWhite() == "W") {
                            action.movePiece(ctx);
                        } else if (!action.getTurn() && PlayBoard.getBoard()[action.convertInt(Integer.valueOf(ctx.pathParam("y")))][action.convertChar(ctx.pathParam("x"))].ifWhite() == "B") {
                            action.movePiece(ctx);
                        } else {
                            ctx.status(401);
                        }
                    }
                }
                action.turnToKing();

                if (action.ifMoved()) {
                    action.canNotGoOn();
                    if (!action.ifAttackSuccess()){
                        action.changeTurn();
                    }else if(action.ifAttackSuccess()) {
                        if(action.ifKing(ctx.pathParam("z"), Integer.valueOf(ctx.pathParam("j")))){
                            action.ifKingCanGoOn(ctx);
                            if (!action.kingCanGoOn()) {
                                action.changeTurn();
                            } else {
                                action.ifMovedRightOne(ctx);
                            }
                        } else if (!action.ifKing(ctx.pathParam("z"), Integer.valueOf(ctx.pathParam("j")))) {
                            action.ifPawnCanGoOn(ctx);
                            if (!action.pawnCanGoOn()) {
                                action.changeTurn();
                            } else {
                                action.ifMovedRightOne(ctx);
                            }
                        }
                    }
                }
            });
            app.after(ctx -> {
                if (!action.ifWhitewin() && !action.ifBlackWin()) {
                    board.showBoardinBrowser(ctx);
                }
                if (action.ifBlackWin()) {
                    if (PlayerController.getPlayer(0).getColor() == false) {
                        PlayerController.getPlayer(0).isWin();
                    } else if (PlayerController.getPlayer(1).getColor() == false) {
                        PlayerController.getPlayer(1).isWin();
                    }
                    start.gameOver();
                    ctx.result(PlayerController.getPlayer(0).toString() + " " + PlayerController.getPlayer(1).toString());

                } else if (action.ifWhitewin()) {
                    if (PlayerController.getPlayer(0).getColor() == true) {
                        PlayerController.getPlayer(0).isWin();
                    } else if (PlayerController.getPlayer(1).getColor() == true) {
                        PlayerController.getPlayer(1).isWin();
                    }
                    start.gameOver();
                    ctx.result(PlayerController.getPlayer(0).toString() + " " + PlayerController.getPlayer(1).toString());
                }
            });
        }


    }

}
