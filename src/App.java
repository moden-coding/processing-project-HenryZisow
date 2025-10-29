import processing.core.*;
//ADD ELAPSED TIME

public class App extends PApplet {
    float r = 1; // color of rectangles
    float g = 1; // color of rectangles
    float b = 1; // color of rectangles
    float rectY1; // rectangle height
    float rectX1; // rectangle distance along the screen
    float rectY2; // rectangle height
    float rectX2; // rectangle distance along the screen
    float rectHeight = 150; // rectangle length
    float rectHeight2 = rectHeight + 200; // rectangle on the bottom length
    float speed = 3; // speed in which the rectangles go across the screen
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
    int highscore = 0; // keeps track of highscore

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
        size(1400, 600); // creates a 800x600 pixel screen
    }

    public boolean isTouchingRectangle(float playerX, float playerY, float playerSize) {
        if (playerX + playerSize / 2 > rectX1 && playerX - playerSize / 2 < rectX1 + 100 &&
                playerY - playerSize / 2 < rectHeight) {
            scene = 2;
            score = 0;
            rectX1 = width;
            rectX2 = width;
            velocity = new PVector(0, 0);
            return true;
        }
        if (playerX + playerSize / 2 > rectX2 && playerX - playerSize / 2 < rectX2 + 100 &&
                playerY + playerSize / 2 > height - rectHeight2) {
            scene = 2;
            score = 0;
            rectX1 = width;
            rectX2 = width;
            velocity = new PVector(0, 0);
            return true;
        }
        return false;

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
                r = random(0, 255);
                g = random(0, 255);
                b = random(0, 255);
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
            if (isTouchingRectangle(position.x, position.y, 40)) {
}

            if (score > highscore) {
                highscore = score;

            }

        } else if (scene == 2) { // game over screen for when you touch the rectangles
            int pointsaway = highscore - score;
            background(0);
            fill(255, 0, 0);
            textSize(150);
            text("GAME OVER", 330, 300);
            textSize(100);
            text("Press 'r' to restart", 335, 400);
            fill(0, 255, 0);
            textSize(60);
            text("Your score was " + score, 485, 500);
            fill(255);
            textSize(45);
            text("Press 'enter' to return to the main menu", 335, 100);
            if (score == highscore) {
                fill(0, 255, 120);
                textSize(60);
                text("You got the highscore!", 435, 565);
            } else if (score < highscore) {
                fill(0, 255, 120);
                textSize(32);
                text("You were " + pointsaway + " points away from the highscore", 410, 570);
            }

        } else if (scene == 3) { // loading screen to load you into the game
            background(200); // makes the background grey
            fill(0, 174, 255);
            textSize(120);
            text("Flappy Moden", 350, 250); // creates the text for the title screen
            fill(0, 0, 130);
            textSize(40);
            text("Press 'enter' to play", 530, 360); // creates the text for the title screen
            fill(255);
            textSize(60);
            text("Press 'c' for controls", 445, 450); // creates the text for the title screen
            if (highscore > 0) { // displays the highscore on the title screen if it exists
                fill(0, 178, 100);
                textSize(50);
                text("Highscore: " + highscore, 550, 100);
            }

        } else if (scene == 4) { // scene that gives you the controls
            background(200);
            fill(0);
            textSize(50);
            text("Press space to jump", 500, 300);
            fill(255);
            textSize(45);
            text("Press b to go back to the main menu", 365, 80);
        }
    }

    public void update() { // this method handles the physics of the character
        if (isJumping) { // if the character is jumping, make the velocity the negative jump power
            velocity.y = -jumpPower; // this is negative because the y value increases as you go further down the
                                     // screen, so negative makes it go up
            isJumping = false; // sets the boolean back to false
        }

        velocity.add(acceleration); // adds gravity to the velocity each frame
        position.add(velocity); // updates the characters position based off of the velocity

        if (position.y + 10 > height) { // checks if the character goes below the screen
            position.y = height - 10; // keeps the character at the bottom of the screen
            velocity.y = 0; // stops the character from falling below the screen
        }
        if (position.y - 10 < 0) { // checks if the character goes above the screen
            position.y = 10; // keeps the character at the top of the screen
            velocity.y = 0; // stops the character from going above the screen
        }
    }

    public void keyPressed() { // this method helps detect when specific keys are pressed and tells what to do
                               // when they are pressed
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
