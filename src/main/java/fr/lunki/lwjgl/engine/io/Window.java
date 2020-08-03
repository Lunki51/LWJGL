package fr.lunki.lwjgl.engine.io;

import fr.lunki.lwjgl.Main;
import fr.lunki.lwjgl.engine.maths.Matrix4f;
import fr.lunki.lwjgl.engine.maths.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import static fr.lunki.lwjgl.Main.window;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;

public class Window {

    private int width,height;
    private String title;
    private long window;
    private Vector3f color;
    private Matrix4f projection;
    private GLFWWindowSizeCallback sizeCallback;
    private boolean isResized;
    private float fov = 70f;
    private float far =1000;
    private float near =0.1f;
    private float delta=0;
    private double currentTime;
    private double mouseXDelta=0,mouseYDelta=0;
    private double mouseX,mouseY;
    public final Vector3f BACKGROUND = new Vector3f(255,0,0);

    public Window(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
        this.projection = Matrix4f.projection(this.fov,(float) width / (float) height,this.near,this.far);
        this.color = new Vector3f(1,1,1);
    }

    public void create(){

        if(!glfwInit()){
            System.err.println("ERROR : CANT INITIALIZE GLFW");
            return;
        }

        this.window = glfwCreateWindow(width,height,title,0,0);
        if(window==0){
            System.err.println("ERROR WITH CREATING WINDOW");
            return;
        }

        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window,(vidMode.width()-width)/2,(vidMode.height()-height)/2);
        glfwMakeContextCurrent(window);
        GL.createCapabilities();
        glEnable(GL11.GL_DEPTH_TEST);


        createCallbacks();

        glfwShowWindow(window);
        glfwSwapInterval(1);

    }

    public float getDelta() {
        return delta;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void update(){
        mouseX = Input.getMouseX();
        mouseY = Input.getMouseY();
        currentTime = glfwGetTime();
        if (isResized) {
            glViewport(0, 0, width, height);
            projection = Matrix4f.projection(this.fov,(float) width / (float) height,this.near,this.far);
            isResized = false;
        }
        if(Input.isMousePressed(GLFW_MOUSE_BUTTON_LEFT))mouseState(true);
        if(Input.isKeyPressed(GLFW_KEY_ESCAPE))glfwSetWindowShouldClose(window,true);
        glClearColor(getColor().getX(),getColor().getY(),getColor().getZ(),1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glfwPollEvents();

    }

    public void mouseState(boolean lock) {
        glfwSetInputMode(window, GLFW_CURSOR, lock ? GLFW_CURSOR_DISABLED : GLFW_CURSOR_NORMAL);
    }

    public double getMouseXDelta() {
        return mouseXDelta;
    }

    public double getMouseYDelta() {
        return mouseYDelta;
    }

    public void swapBuffers(){
        glfwSwapBuffers(window);
        delta = (float) (glfwGetTime()-currentTime);
        mouseXDelta = Input.getMouseX() - mouseX;
        mouseYDelta = Input.getMouseY() - mouseY;
    }

    public boolean shouldClose(){
        return glfwWindowShouldClose(this.window);
    }

    public void destroy(){
        glfwWindowShouldClose(window);
        glfwDestroyWindow(window);
        glfwTerminate();
    }

    public void createCallbacks() {
        new Input();
        sizeCallback = new GLFWWindowSizeCallback() {
            public void invoke(long window, int w, int h) {
                width = w;
                height = h;
                isResized = true;
            }
        };

        org.lwjgl.glfw.GLFW.glfwSetKeyCallback(window, Input.getKeyCallback());
        GLFW.glfwSetCursorPosCallback(window, Input.getCursorPosCallback());
        GLFW.glfwSetMouseButtonCallback(window, Input.getMouseButtonCallback());
        GLFW.glfwSetScrollCallback(window, Input.getScrollCallback());
        GLFW.glfwSetWindowSizeCallback(window, sizeCallback);
    }

    public long getWindow() {
        return window;
    }

    public Vector3f getColor() {
        return color;
    }

    public void changeFOV(float fov){
        this.fov=fov;
        projection = Matrix4f.projection(this.fov,(float) width / (float) height,this.near,this.far);
    }

    public Matrix4f getProjection() {
        return projection;
    }
}
