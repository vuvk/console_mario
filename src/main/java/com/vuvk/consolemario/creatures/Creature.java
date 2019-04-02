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
import com.vuvk.consolemario.logic.Engine;
import com.vuvk.consolemario.logic.GameObject;
import com.vuvk.consolemario.logic.Vector2;

/**
 *
 * @author Anton "Vuvk" Shcherbatykh
 */
public abstract class Creature extends GameObject {  
    protected int health;
    
    protected Vector2 speed;  
    protected float moveSpeed;
    protected Vector2 dir;
    
    public boolean onGround = false;
    
    protected Creature(char character, TextColor foregroundColor, TextColor backgroundColor) {
        super(character, foregroundColor, backgroundColor);
        speed = new Vector2();
        dir   = new Vector2();
    }
    
    public void setHealth(int health) {
        this.health = health;
    }

    public void setSpeed(Vector2 speed) {
        this.speed = speed;
    }
    
    public void setSpeedX(float speed) {
        this.speed.setX(speed);
    }
    
    public void setSpeedY(float speed) {
        this.speed.setY(speed);
    }

    public void setDir(Vector2 dir) {
        this.dir = dir;
    }  
    
    public void setDirX(float dir) {
        this.dir.setX(dir);
    }
    
    public void setDirY(float dir) {
        this.dir.setY(dir);
    }

    public int getHealth() {
        return health;
    }

    public Vector2 getSpeed() {
        return speed;
    }
    
    public float getSpeedX() {
        return speed.getX();
    }
    
    public float getSpeedY() {
        return speed.getY();
    }

    public Vector2 getDir() {
        return dir;
    }
    
    public float getDirX() {
        return dir.getX();
    }
    
    public float getDirY() {
        return dir.getY();
    }
    
    protected void changeDirection() {
        speed.setX(0);        
    }
    
    protected abstract boolean checkCollision(GameObject check);
    
    public void update() {
        speed.setX(dir.getX() * moveSpeed);
        speed.setY(dir.getY());
        speed = speed.add(Map.GRAVITY);
        speed = speed.mul(Engine.getDeltaTime());
        
        Vector2 newPos = pos.add(speed);               
        
        int col = (int)getPosX();
        int row = (int)getPosY();
        int newCol = (int)newPos.getX();
        int newRow = (int)newPos.getY();
        
        if (row != newRow) {
            if (newRow < Map.mask[col].length) {
                GameObject under = Map.mask[col][newRow];
                if (under != null && 
                    under instanceof SolidObject) {
                    speed.setY(0);
                    dir.setY(0);
                    newPos.setY(row);
                    newRow = row;
                    onGround = true;
                } else {
                    onGround = false;
                }
            } else {
                newRow = row;
                newPos.setY(row);
                setHealth(0);
            }
        }
                
        if (col != newCol) {
            if (newCol >= 0 && newCol < Map.mask.length) {
                GameObject check = Map.mask[newCol][newRow];
                if (check != null && 
                    checkCollision(check)) {
                    newPos.setX(col);
                    newCol = col;
                    changeDirection();
                }
            } else {
                newCol = col;
                newPos.setX(col);
                setHealth(0);                
            }        
        }
        
        move(newPos);
        
        // трение
        if (dir.getX() > 0) {
            dir.setX(dir.getX() - moveSpeed / 2 * Engine.getDeltaTime());
        } else if (dir.getX() < 0) {
            dir.setX(dir.getX() + moveSpeed / 2 * Engine.getDeltaTime());            
        }
        
        // уменьшение подбрасывания
        if (dir.getY() < 0) {
            dir.setY(dir.getY() + Map.GRAVITY.getY() * Engine.getDeltaTime());            
        } else if (dir.getY() > 0) {
            dir.setY(0);
        }
    }
}
