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

import com.vuvk.consolemario.items.Item;
import com.vuvk.consolemario.creatures.Creature;
import com.vuvk.consolemario.creatures.Player;
import com.vuvk.consolemario.map.SolidObject;
import com.vuvk.consolemario.map.Map;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.vuvk.consolemario.Main;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static com.vuvk.consolemario.map.Map.PLAYER;
import java.util.Iterator;

/**
 *
 * @author Anton "Vuvk" Shcherbatykh
 */
public class Engine {    
    private static final Logger LOG = Logger.getLogger(Engine.class.getName());
    private Terminal terminal;
    private Screen screen;    
    public static boolean isRunning = false;
    
    private static float   deltaTime = 0.0f;
    private static int     fps = 0;
    public  static boolean needRedraw = true;
    
    private static long  lastTime, time;
    private static float fpsDelta = 0.0f;
    private static int   fpsCounter = 0;
        
    public Engine() {
        try {
            terminal = new DefaultTerminalFactory().createTerminal();
            screen   = new TerminalScreen(terminal);
            screen.clear();
            isRunning = true;
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        
        lastTime = time = System.currentTimeMillis();        
    }
    
    public void update() {
        time = System.currentTimeMillis(); 
        deltaTime = (time - lastTime) / 1000.0f;
        lastTime = time;
                
        ++fpsCounter;
        fpsDelta += deltaTime;
        if (fpsDelta >= 1) {
            fpsDelta -= 1;
            
            fps = fpsCounter;
            fpsCounter = 0;
        }
        
        for (Iterator<Creature> it = Map.creatures.iterator(); it.hasNext(); ) {
            Creature creature = it.next();
            creature.update();
            if (creature.getHealth() <= 0) {
                it.remove();
                print((int)creature.getPosX(), (int)creature.getPosY(), " ");
                if (creature instanceof Player) {
                    PLAYER = null;
                }
            }
        }
        
        // обработка клавиатуры
        try {
            KeyStroke key = terminal.pollInput();
            if (key != null) {
                switch (key.getKeyType()) {
                    case ArrowRight:
                        if (PLAYER != null) {
                            PLAYER.setDirX(1);
                        }
                        break;
                    case ArrowLeft:
                        if (PLAYER != null) {
                            PLAYER.setDirX(-1);
                        }
                        break;
                    // прыжок
                    case ArrowUp:
                        if (PLAYER != null && PLAYER.onGround) {
                            PLAYER.jump();
                        }
                        break;
                        
                    case Character:
                        switch (key.getCharacter()) {
                            case 'r' :
                                Main.engine.screen.clear();
                                Map.load();
                                break;
                        }
                        break;
                        
                    // выход из игры
                    case Escape:
                        isRunning = false;
                        break;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    public void draw() throws IOException {
        if (needRedraw) {
            //screen.clear();
            screen.startScreen();
            screen.setCursorPosition(null);

            // draw static objects
            for (SolidObject solid : Map.solids) {
                screen.setCharacter((int)solid.getPosX(), 
                                    (int)solid.getPosY(), 
                                    solid.getGraphic());
            }

            // draw items
            for (Item item : Map.items) {
                screen.setCharacter((int)item.getPosX(), 
                                    (int)item.getPosY(), 
                                    item.getGraphic());
            }

            // draw creatures
            for (Creature creature : Map.creatures) {
                screen.setCharacter((int)creature.getPosX(), 
                                    (int)creature.getPosY(), 
                                    creature.getGraphic());
            }
            
            needRedraw = false;
        }
        
        print(3, 1, "FPS: " + getFps());
        print(3, 2, "COINS(\u2666): " + Map.COINS);
        if (PLAYER == null) {
            print(3, 3, "PLAYER WAS DEAD"); 
            print(3, 4, "PRESS 'R' FOR RESTART");            
        }
        
        screen.refresh();        
    }
    
    public void print(int x, int y, String text) {
        for (int c = 0; c < text.length(); ++c) {
            screen.setCharacter(x + c, y, new TextCharacter(text.charAt(c)));
        } 
    }

    public static float getDeltaTime() {
        return deltaTime;
    }
    
    public static int getFps() {
        return fps;
    }
}
