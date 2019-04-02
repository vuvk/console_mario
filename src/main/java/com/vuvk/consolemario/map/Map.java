/**
    ConsoleMario
    Copyright (C) 2019 Anton "Vuvk" Shcherbatykh <vuvk69@gmail.com>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/
package com.vuvk.consolemario.map;

import com.vuvk.consolemario.items.Coin;
import com.vuvk.consolemario.creatures.Coopa;
import com.vuvk.consolemario.creatures.Creature;
import com.vuvk.consolemario.logic.GameObject;
import com.vuvk.consolemario.creatures.Goomba;
import com.vuvk.consolemario.items.Item;
import com.vuvk.consolemario.creatures.Player;
import com.vuvk.consolemario.logic.Vector2;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Anton "Vuvk" Shcherbatykh
 */
public class Map {
    static String[] level = {
        "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@",
        "#                                                                              #",
        "#                                                                              #",
        "#                                                                              #",
        "#                                                                              #",
        "#                                                                              #",
        "#  h                                        @                                  #",
        "#            pppp   c  c  c         ?     @ @ @                                #",
        "#      pppp   pp              pppp       @@@@@@@        p              c  c    #",
        "#       pp    pp    g     g    pp        @@@ @@@    g  ppp    c  c  c    t  #  #",
        "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@  #",
        "#                                           g                                  #",
        "#                                         @@@@#t                               #",
        "#                                         @@@@#@@@@#                  pppp     #",
        "#                                         @@@@#@@@@#      t            pp    t #",
        "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ @@@@@@@@@@  @@@@@@@@@@@@@@@"
    };
    
    public static final Vector2 GRAVITY = new Vector2(0, 5f);
    public static GameObject mask[][];
    public static List<SolidObject> solids;
    public static List<Item> items;
    public static List<Creature> creatures;
    
    public static Player PLAYER;
    public static int COINS;
    
    public static void load() {
        mask = new GameObject[level[0].length()][level.length];
        solids = new LinkedList<>();
        items = new LinkedList<>();
        creatures = new LinkedList<>();
        
        PLAYER = new Player();
        COINS  = 0;
                
        for (int row = 0; row < level.length; ++row) {
            for (int col = 0; col < level[row].length(); ++col) {
                GameObject element = null;
                switch (level[row].charAt(col)) {
                    // solids
                    case '@' :
                        element = new BrickWall();
                        break;
                    case '#' :
                        element = new SteelWall();
                        break;
                    case 'p' :
                        element = new Pipe();
                        break;
                        
                    // items
                    case 'c' :
                        element = new Coin();
                        break;                        
                        
                    // creatures
                    case 'g' : 
                        element = new Goomba();
                        break;  
                    case 't' : 
                        element = new Coopa();
                        break;                        
                    case 'h' :
                        element = PLAYER;
                        break;
                }
                
                if (element != null) {
                    element.setPosX(col);
                    element.setPosY(row);
                    
                    mask[col][row] = element;
                    
                    if (element instanceof SolidObject) {
                        solids.add((SolidObject)element);
                    } else if (element instanceof Item) {
                        items.add((Item)element);
                    } else if (element instanceof Creature) {
                        creatures.add((Creature)element);
                    }
                }
            }
        }
    }
    
    public static boolean isEmpty(Vector2 pos) {
        return isEmpty((int)pos.getX(), (int)pos.getY());
    }
    
    public static boolean isEmpty(int x, int y) {
        return (mask[x][y] == null);
    }
}
