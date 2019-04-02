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
package com.vuvk.consolemario;
import com.vuvk.consolemario.map.Map;
import com.vuvk.consolemario.logic.Engine;
import java.io.IOException;

/**
 *
 * @author Anton "Vuvk" Shcherbatykh
 */
public class Main {    
    public static final Engine engine = new Engine();  
    
    public static void main(String ... args) throws IOException {        
        Map.load();  
        
        while (Engine.isRunning) {
            engine.update();
            engine.draw();
        }
    }    
}
