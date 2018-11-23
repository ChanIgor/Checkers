package ru.yandex.igor;

import io.javalin.Context;

import java.util.ArrayList;
import java.util.List;

public class PlayerController {

    private static List<Player> players = new ArrayList();

    public static void postPlayer(Context ctx){
        String name = ctx.pathParam("name");
        boolean color = Boolean.valueOf(ctx.pathParam("color"));

        Player player = new Player(name, color);
        players.add(player);
        ctx.result(player.toString());
        ctx.status(201);

    }
    public static void showPlayer(Context ctx){
        int index = Integer.valueOf(ctx.pathParam("index"));
        ctx.result(getPlayer(index).toString());
    }

    public static Player getPlayer(int index){
        return players.get(index);
    }
}
