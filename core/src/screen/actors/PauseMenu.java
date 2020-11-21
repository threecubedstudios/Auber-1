package screen.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


public class PauseMenu {
    private Window pauseWindow;
    private Skin skin = new Skin(Gdx.files.internal("skin/hudskin/comic-ui.json"));
    private boolean paused;
    private boolean exiting;
    TextButton resumeButton = new TextButton("Resume", skin);
    TextButton exitButton = new TextButton("Exit", skin);
    Label titleLabel = new Label("Paused", skin);

    public PauseMenu() {
        this.pauseWindow = new Window("Pause", this.skin);
        pauseWindow.setMovable(false);        

        pauseWindow.setSize(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight() * 2/3);
        pauseWindow.setPosition(Gdx.graphics.getWidth()/2 - pauseWindow.getWidth()/2,Gdx.graphics.getHeight()/2 - pauseWindow.getHeight()/2);
        pauseWindow.setVisible(false);
        pauseWindow.setLayoutEnabled(false);        

        //Adds actors to the menu
        resumeButton.setName("Resume");
        exitButton.setName("Exit");
        titleLabel.setName("Title");

        pauseWindow.add(resumeButton);
        pauseWindow.add(exitButton);
        pauseWindow.add(titleLabel);

        pauseWindow.findActor("Resume").setPosition(this.pauseWindow.getWidth()/2 - resumeButton.getWidth()/2, this.pauseWindow.getHeight() * 7/10 - resumeButton.getHeight()/2);
        pauseWindow.findActor("Exit").setPosition(this.pauseWindow.getWidth()/2 - exitButton.getWidth()/2, this.pauseWindow.getHeight() * 5/10 - exitButton.getHeight()/2);
        pauseWindow.findActor("Title").setPosition(this.pauseWindow.getWidth()/2 - titleLabel.getWidth()/2, this.pauseWindow.getHeight() * 9/10 - titleLabel.getHeight()/2);

        pauseWindow.setColor(Color.BLACK);

        paused = false;
    }

    /**
     * shows the menu
     */
    public void show() {
        pauseWindow.setVisible(true);
        paused = true;

        //Event listeners for buttons
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                paused = false;
            }
        });
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                exiting = true;
            }
        });
    }

    /**
     * hides the menu
     */
    public void hide() { 
        pauseWindow.setVisible(false);
        paused = false;
    }

    public Window pauseWindow() {
        return this.pauseWindow;
    }

    /**
     * 
     * @return whether or not gameplay should resume
     */
    public boolean resume() {
        return (!paused) || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE);           
       
    }

    /**
     * 
     * @return whether or not the game should end
     */
    public boolean exit() {
        return exiting;
    }

}