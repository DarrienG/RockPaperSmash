#Introduction

Rock Paper Smash is a fighting game that pushes the rock, paper, scissors meta of the Super Smash Brother series to forefront.

In standard Super Smash Bros. during a match, the player has three options, to attack, to grab, or to shield. Attack beats grab, grab beats shield, and shield beats attack. However, unlike standard rock, paper, scissors, in Smash Bros. picking a move is not as cut and dry as picking any of the three options. Each character has different reasons to want to choose to attack, or grab, or shield. For example, Falco's raw attack game is incredibly powerful in Smash Bros. and as such, it is often in a Falco player's best interests to choose jump in and attack, as they know it is their strongest option, even if it isn't necessarily the best option for them to choose in that situation. In short, while each player has the same moves, each character has their own strengths and weaknesses when it comes to each move.

Also similar is that if you win a round in the combat triangle in Smash Bros. you don't immediately defeat your foe. The opponent gets percentage tacked on, and the higher their percentage goes, the greater the likelihood they will be knocked out. 

In Rock Paper Smash, both of these are idealized in what would normally be the standard rock, paper, scissors metagame. Each character has different reasons to want to choose each move, and there are different advantages and disadvantages to each move. When beating a foe in a single round, percentage is tacked on, but the other player does not immediately win. Your opponent could have a higher damage percent than you, and still win depending on that character's strengths and weaknesses.

#Setup                                  

To play, you need to have Java installed. Your command prompt or terminal will also need to be maximized in order to fully view the text art. Being able to fully view the art is not required to play, but makes the experience much better.

Click on *Releases* in the top bar, and download the zip. 

Extract all of the files from the archive.

If you're on Windows, open up the Command Prompt (search for cmd, press ENTER). If you're on OS X, or Linux, open up the terminal.

Navigate to the directory you unzipped in your command prompt.


*If you're on Windows 8.1 or older, type in*

    wmic

Everyone continues from here: 

Then press the maximize button on your terminal.

Finally, type this command:

    java -jar RPS_V1.jar
    
And you can begin the game. Please note that all of the included directories must be in the same place as the .jar file for the game to run. 

If you would like to move the jar file, or keep it in a directory on its own, you'll need to make a shortcut to it, and then place that elsewhere. 

#Troubleshooting

##Graphics not displayed properly?

Your screen's density likely isn't high enough to display all assets normally. Unfortunately because of the nature of how blocky text is when making pictures using it, the pictures had to be a bit bigger than I initially wanted. In some cases, screens with a resolution below 1080p will erroneously display pictures.

To fix:

For Windows -

    1. Right click on the top bar
    2. Click on properties
    3. Click on Font
    4. Choose a smaller size for your current font
    5. Hit OK
    
For Linux and OS X -

    1. Right click on your terminal
    2. Click "zoom out" until the graphics are displayed correctly 
    
In future releases, there will be a script that does this automatically, so no configuration will need to be done by the user. 
    
##On Windows?

The default command prompt font makes the game look as if it were crushed a little bit. I guess Ganon just couldn't resist another victim.

If you want the game to look more normal, change your command prompt font to Consolas for best results. 

    1. Right click on the top bar
    2. Click on properties
    3. Click on Font
    4. Choose Consolas
    5. Hit OK
    
If you happen to be the adventurous kind of fellow, or you know how to install fonts, Ubuntu Mono makes the game look great. Consider downloading it, and using that for the best experience.

http://font.ubuntu.com/
