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
package com.vuvk.consolemario.creatures;

import com.vuvk.consolemario.map.SolidObject;
import com.vuvk.consolemario.map.Map;
import com.googlecode.lanterna.TextColor;
import com.vuvk.consolemario.items.Coin;
import com.vuvk.consolemario.logic.GameObject;
import com.vuvk.consolemario.items.Item;
import java.util.Iterator;

/**
 *
 * @author Anton "Vuvk" Shcherbatykh
 */
public class Player extends Creature {  
    public Player() {
        super('\u263B', 
              TextColor.Indexed.fromRGB(255,255,0),
              TextColor.ANSI.BLACK
             );
        
        health = 1;
        moveSpeed = 5;
    }
    
    public void jump() {
        setDirY(-2 * Map.GRAVITY.getY());
    }
    
    @Override
    public void update() {
        super.update();
        
        int col = (int)getPosX();
        int row = (int)getPosY();
        
        // сбор предметов
        for (Iterator<Item> it = Map.items.iterator(); it.hasNext(); ) {
            Item item = it.next();
            if (col == item.getPosX() &&
                row == item.getPosY()
               ) {                
                if (item instanceof Coin) {
                    Map.COINS++;
                }
                
                it.remove();
            }
        }
        
        // убийство животных
        if (!onGround && 
            speed.getY() >= 0 &&                 
            row < Map.mask[col].length - 1
           ) {
            for (Iterator<Creature> it = Map.creatures.iterator(); it.hasNext(); ) {
                Creature creature = it.next();
                if (creature == this) {
                    continue;
                }
                
                if (col == (int)creature.getPosX() &&
                    row == (int)creature.getPosY()// - 1
                   ) {                
                    if (creature instanceof Enemy) {
                        creature.setHealth(0);
                        jump();
                    }
                }
            }
        }
    }

    @Override
    protected boolean checkCollision(GameObject check) {
        return (check instanceof SolidObject);
    }
}
