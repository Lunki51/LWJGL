package fr.lunki.lwjgl.engine.io;

import fr.lunki.lwjgl.Main;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;

import static org.lwjgl.glfw.GLFW.*;

public class Input {

    private static GLFWKeyCallback keyCallback;
    private static GLFWMouseButtonCallback mouseButtonCallback;
    private static GLFWCursorPosCallback cursorPosCallback;
    private static GLFWScrollCallback scrollCallback;

    private static boolean[] keys = new boolean[GLFW_KEY_LAST];
    private static boolean[] mouseButtons = new boolean[GLFW_MOUSE_BUTTON_LAST];
    private static double mouseX,mouseY;
    private static double scrollX=0,scrollY=0;

    public Input(){
        keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if(action==GLFW_PRESS){
                    keys[key]=true;
                }
                if(action==GLFW_RELEASE){
                    keys[key]=false;
                }
            }
        };

        mouseButtonCallback = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                if (action == GLFW_PRESS) {
                    mouseButtons[button] = true;
                }
                if (action == GLFW_RELEASE) {
                    mouseButtons[button] = false;
                }
            }
        };

        cursorPosCallback = new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                mouseX = xpos;
                mouseY = ypos;
            }
        };

        scrollCallback = new GLFWScrollCallback() {
            @Override
            public void invoke(long window, double xoffset, double yoffset) {
                scrollX += xoffset;
                scrollY += yoffset;
            }
        };
    }

    public static boolean isMousePressed(int mouse) {
        return mouseButtons[mouse];
    }

    public static boolean isKeyPressed(int key) {
        return keys[key];
    }

    public static GLFWKeyCallback getKeyCallback() {
        return keyCallback;
    }

    public static GLFWMouseButtonCallback getMouseButtonCallback() {
        return mouseButtonCallback;
    }

    public static GLFWCursorPosCallback getCursorPosCallback() {
        return cursorPosCallback;
    }

    public static GLFWScrollCallback getScrollCallback() {
        return scrollCallback;
    }

    public static double getMouseX() {
        return mouseX;
    }

    public static double getMouseY() {
        return mouseY;
    }

    public static double getScrollX() {
        return scrollX;
    }

    public static double getScrollY() {
        return scrollY;
    }
}
