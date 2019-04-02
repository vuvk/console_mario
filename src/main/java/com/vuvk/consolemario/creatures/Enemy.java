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
import com.vuvk.consolemario.logic.GameObject;

/**
 *
 * @author Anton "Vuvk" Shcherbatykh
 */
public abstract class Enemy extends Creature {
    public Enemy(char character, TextColor foregroundColor, TextColor backgroundColor) {
        super(character, foregroundColor, backgroundColor);
        
        dir.setX((int)(Math.random() * 2) - 1);
    }    
    
    @Override
    protected void changeDirection() {
        super.changeDirection();
        dir.setX(-dir.getX());
    }

    @Override
    protected boolean checkCollision(GameObject check) {
        if (check instanceof SolidObject) {
            return true;
        } else if (check instanceof Enemy) {
            return true;            
        }
        
        return false;
    }    
    
    @Override
    public void update() {
        if (dir.getX() < 0) {
            dir.setX(-moveSpeed);
        } else {
            dir.setX(moveSpeed);            
        }
        
        super.update();
        
        int col = (int)getPosX();
        int row = (int)getPosY();
        
        // убийство игрока
        if (health > 0 && 
            Map.PLAYER != null &&
            col == (int)Map.PLAYER.getPosX() &&
            row == (int)Map.PLAYER.getPosY()
           ) {
            Map.PLAYER.setHealth(0);
        }
    }
}
