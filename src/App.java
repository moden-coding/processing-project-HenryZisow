import processing.core.*;

public class App extends PApplet {
    // ADD A HIGHSCORE
    float r = 1; // color of rectangles
    float g = 1; // color of rectangles
    float b = 1; // color of rectangles
    float rectY1; // rectangle height
    float rectX1; // rectangle distance along the screen
    float rectY2; // rectangle height
    float rectX2; // rectangle distance along the screen
    float rectHeight = 150; // rectangle length
    float rectHeight2 = rectHeight + 200; // rectangle on the bottom length
    float speed = 5; // speed in which the rectangles go across the screen
    float gap; // width of gap in between the rectangles
    PImage img; // image for the character
    PVector position; // location of the character
    PVector velocity; // detects how fast and in what direction the character is moving
    PVector acceleration; // basically the gravity of the character
    boolean isJumping; // detects if the character is actively jumping or not
    float jumpPower = 8; // how far up the character goes up when jumping
    int scene = 3; // starts you on the loading screen
    int score = 0; // restarts you score to zero
    int currentColor; // used to detect if the character is touching the rectangles
    PImage ballImg; // chatgpt used to make the ball appear an an image
    int highscore = 0;

    public static void main(String[] args) { // starts the processing sketch
        PApplet.main("App");
    }

    public void setup() { // this method sets up the screen on the startup
        img = loadImage("background.jpg"); // makes an image of my choice the background of the window
        ballImg = loadImage("drmoden.png"); // chatgpt used to make the ball appear an an image
        position = new PVector(width / 2, height / 2); // characters starting position
        velocity = new PVector(0, 0); // sets the starting speed to zero
        acceleration = new PVector(0, 0.3f); // how fast the ball moves up and down
        rectX1 = width; // starts the rectangles at the edge of the screen
        rectHeight = random(50, 250); // generates a random height for the rectangle
        rectX2 = width; // starts the rectangles at the edge of the screen
        rectHeight2 = height - rectHeight - random(100, 200); // creates a height based off of the other rectangle to
                                                              // make sure its not too far apart
        stroke(255, 255, 255); // color
    }

    public void settings() { // this method creates the screen where everything shows up
        size(800, 600); // creates a 800x600 pixel screen
    }

    public void draw() { // this method draws everything and updates 60 times per second
        background(255); // background color
        imageMode(CORNER); // chatgpt used to make the ball appear an an image
        image(img, 0, 0, width, height); // creates the image in the background
        update(); // calls the update method
        currentColor = get((int) position.x, (int) position.y); // gets the color of where the character is to make sure
                                                                // it isn't over one of the rectangles
        imageMode(CENTER); // chatgpt used to make the ball appear an an image
        image(ballImg, position.x, position.y, 40, 40); // chatgpt used to make the ball an image of my choice

        if (scene == 1) { // main game scene

            rectX1 -= speed; // makes the rectangles move from right to left across the screen
            rectX2 -= speed; // makes the rectangles move from right to left across the screen
            stroke(255, 255, 255); // color
            fill(r, g, b); // color of the rectangles
            rect(rectX1, 0, 100, rectHeight); // creates the rectangle
            rect(rectX2, height - rectHeight2, 100, rectHeight2); // creates the rectangle

            fill(255); // score text on the top left of the screen
            textSize(42); // size of the text
            text("Score: " + score, 20, 50); // creates a scoreboard on the screen
            fill(255); // score text on the top left of the screen
            textSize(42); // size of the text
            text("Highscore: " + highscore, 20, 90); // creates a scoreboard on the screen

            if (rectX1 + 100 < position.x && rectX2 + 100 < position.x) { // adds points to the score after the
                                                                          // character gets past the right edge of the
                                                                          // rectangles
                score++; // adds one to the score
                rectX1 = width; // puts rectangle back to the edge of the screen
                rectX2 = width; // puts rectangle back to the edge of the screen
                rectHeight = random(50, 250); // creates a new height for the rectangle and randomizes the height
                rectHeight2 = height - rectHeight - random(100, 200); // creates a new height for the rectangle and
                                                                      // randomizes the height
            }
            speed = 5 + (score * 0.3f); // slowly increases the speed as your score increases

            currentColor = get((int) (position.x), (int) (position.y - 10)); // detects if the character touches the
                                                                             // rectangles through color
            if (red(currentColor) == r && green(currentColor) == g && blue(currentColor) == b) {
                scene = 2;
                rectX1 = 0;
                rectX2 = 0;
                velocity = new PVector(0, 0);
            }
            currentColor = get((int) (position.x), (int) (position.y + 10)); // detects if the character touches the
                                                                             // rectangles through color
            if (red(currentColor) == r && green(currentColor) == g && blue(currentColor) == b) {
                scene = 2;
                rectX1 = 0;
                rectX2 = 0;
                velocity = new PVector(0, 0);
            }

            currentColor = get((int) (position.x - 10), (int) (position.y)); // detects if the character touches the
                                                                             // rectangles through color
            if (red(currentColor) == r && green(currentColor) == g && blue(currentColor) == b) {
                scene = 2;
                rectX1 = 0;
                rectX2 = 0;
                velocity = new PVector(0, 0);
            }
            currentColor = get((int) (position.x + 10), (int) (position.y)); // detects if the character touches the
                                                                             // rectangles through color
            if (red(currentColor) == r && green(currentColor) == g && blue(currentColor) == b) {
                scene = 2;
                rectX1 = 0;
                rectX2 = 0;
                velocity = new PVector(0, 0);
            }
            if (score > highscore) {
                highscore = score;

            }

        } else if (scene == 2) { // game over screen for when you touch the rectangles
            background(0);
            fill(255, 0, 0);
            textSize(150);
            text("GAME OVER", 30, 300);
            textSize(100);
            text("Press 'r' to restart", 35, 400);
            fill(0, 255, 0);
            textSize(60);
            text("Your score was " + score, 185, 500);
            fill(255);
            textSize(45);
            text("Press 'enter' to return to the main menu", 35, 100);

        } else if (scene == 3) { // loading screen to load you into the game
            background(200); // makes the background grey
            fill(0, 174, 255);
            textSize(120);
            text("Flappy Moden", 50, 250); // creates the text for the title screen
            fill(0, 0, 130);
            textSize(40);
            text("Press 'enter' to play", 230, 360); // creates the text for the title screen
            fill(255);
            textSize(60);
            text("Press 'c' for controls", 145, 450); // creates the text for the title screen
            if (highscore > 0) { // displays the highscore on the title screen if it exists
                fill(0, 178, 100);
                textSize(50);
                text("Highscore: " + highscore + "!", 250, 100);
            }

        } else if (scene == 4) { // scene that gives you the controls
            background(200);
            fill(0);
            textSize(50);
            text("Press space to jump", 200, 300);
            fill(255);
            textSize(45);
            text("Press b to go back to the main menu", 65, 80);
        }
    }

    public void update() { // this method handles the physics of the character
        if (isJumping) {
            velocity.y = -jumpPower;
            isJumping = false;
        }

        velocity.add(acceleration);
        position.add(velocity);

        if (position.y + 10 > height) {
            position.y = height - 10;
            velocity.y = 0;
        }
        if (position.y - 10 < 0) {
            position.y = 10;
            velocity.y = 0;
        }
    }

    public void keyPressed() { // this method helps detect when specific keys are pressed
        if (key == ' ') {
            velocity.y = -jumpPower;
        }
        if (key == 'r' && scene == 2) {
            scene = 1;
            score = 0;
            rectX1 = width;
            rectX2 = width;

        }
        if (keyCode == ENTER && scene == 3) {
            score = 0;
            rectX1 = width;
            rectX2 = width;
            scene = 1;
        }
        if (keyCode == ENTER && scene == 2) {
            scene = 3;
            if (score == 1) {
                score = 0;
            }
            score = 0;
        }
        if (key == 'c' && scene == 3) {
            scene = 4;
        }
        if (key == 'b' && scene == 4) {
            scene = 3;
        }
    }
}
