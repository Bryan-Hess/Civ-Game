package hotciv.stub;

import hotciv.framework.*;
import hotciv.visual.*;
import java.util.*;

public class ObserverStub implements GameObserver {

    GameObserver observer;
    public void worldChangedAt(Position pos){
        System.out.println("World changed at: "+pos +".\n");
    }

    public void turnEnds(Player nextPlayer, int age){
        System.out.println("Turn ended. Next player is: " + nextPlayer + ". World age: " + age + ".\n");
    }

    public void tileFocusChangedAt(Position position){
        System.out.println("Tile focus changed at tile: " + position + ".\n");
    }
}