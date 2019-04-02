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

/**
 *
 * @author Anton "Vuvk" Shcherbatykh
 */
public class Vector2 {
    private float x;
    private float y;
    
    public Vector2() {
        this(0, 0);
    }
    
    public Vector2(Vector2 other) {
        this(other.getX(), other.getY());
    }
    
    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    public void setX(float x) {
        this.x = x;
    }
    
    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
    
    public Vector2 add(Vector2 other) {
        return new Vector2(x + other.getX(), 
                           y + other.getY());
    }
    
    public Vector2 sub(Vector2 other) {
        return new Vector2(x - other.getX(), 
                           y - other.getY());
    }
    
    public Vector2 mul(float number) {
        return new Vector2(x * number, 
                           y * number);
    }
    
    public Vector2 div(float number) {
        return new Vector2(x / number, 
                           y / number);
    }
    
    public float length() {
        return (float)Math.sqrt(x*x + y*y);
    }
    
    public float distance(Vector2 other) {        
        return sub(other).length();
    }
}
