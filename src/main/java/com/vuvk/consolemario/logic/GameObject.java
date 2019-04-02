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
package com.vuvk.consolemario.logic;

import com.vuvk.consolemario.map.Map;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.vuvk.consolemario.Main;

/**
 *
 * @author Anton "Vuvk" Shcherbatykh
 */
public abstract class GameObject {
    protected Vector2 pos;
    
    protected char character;
    protected TextColor foregroundColor;
    protected TextColor backgroundColor;
    protected TextCharacter graphic;
    
    protected GameObject(char character, TextColor foregroundColor, TextColor backgroundColor) {
        this.character = character;
        this.foregroundColor = foregroundColor;
        this.backgroundColor = backgroundColor;
        
        pos = new Vector2();
        this.graphic = new TextCharacter(character, foregroundColor, backgroundColor);
    }
    
    public void setPos(Vector2 pos) {
        this.pos = pos;
    }

    public void setPosX(float x) {
        pos.setX(x);
    }

    public void setPosY(float y) {
        pos.setY(y);
    }       
    
    public Vector2 getPos() {
        return pos;
    }
        
    public float getPosX() {
        return pos.getX();
    }
    
    public float getPosY() {
        return pos.getY();
    }
    
    /**
     * Переместить объект на новую позицию с проверкой маски
     * @param newPos Новая позиция
     */
    public void move(Vector2 newPos) {
        int oldCol = (int)getPosX();
        int oldRow = (int)getPosY();
        int newCol = (int)newPos.getX();
        int newRow = (int)newPos.getY();
        
        pos = newPos;
        
        if (oldCol != newCol || oldRow != newRow) {
            Main.engine.print(oldCol, oldRow, " "); // закрасить старую позицию
            Engine.needRedraw = true;
            
            Map.mask[oldCol][oldRow] = null;
            Map.mask[newCol][newRow] = this;
        }
    }
    
    public TextCharacter getGraphic() {
        return graphic;
    }
}
