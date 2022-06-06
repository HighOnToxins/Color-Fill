# Color-Fill
This is a simple program that slowly colors the screen with interesting patterns.



## Fill pattern

To alter the pattern that appears and how pixels form and grow, and what pattern they create, you will need to look into the Cell.java file. On lines 86 to 91 you will find lines describing different patterns, simply comment out the current and uncomment the desired pattern. Alternatively, you can create your own pattern.

### Creating your own pattern

This is relatively simple to do, yet getting the desired shape might not be easy. The weight variable determines the importance of the current cell (pixel). This can be determined based on a random number and the weight of the parent cell (the cell that created it).



## Altering color pattern

To alter the color of the given pattern you will also need to look at the Cell.java file, now on the lines 13 and 14.

### Variation

Variation determines the general change of color between each pixel. (255 is the total change of color, and 0 is no change) A normal value for this would be between 1 and 0.02. This is because if each pixel changes a little, then that change is magnified between each side of the screen.

### Detail

Detail determines the amount of noise of the colors, such that a high value (1) yields a noisy image and a lower value (0.01) yield less noise (large areas with the exact same color). Though the average change of color (variation) remains unchanged.


## Direction

To alter the direction in which new cells are formed, you must look at lines 73 to 75 of the Cell.java file. 

### Favoured Path

The favored path indicated which path the cell will move if it has the option, if this path is not possible the closest path to that is chosen. On lines, 73 to 75 of the Cell.java file are a few examples of choosing a favored path. Alternatively, you could create your own code for choosing a favored path.

### Manhatten vs. euclidean 

On lines 22 to 31, you will see code that determines the possible paths that a cell can take. If the commented lines become uncommented the pattern of the fill will change to become square instead of diamond-shaped.

## Update Speed

The update speed on line 20 determines the percentage of cells computed before drawing the screen, meaning that the higher the value the faster the process will be.
