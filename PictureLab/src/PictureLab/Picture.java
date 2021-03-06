package PictureLab;
import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.*;
import java.util.List; // resolves problem with java.awt.List and java.util.List

/**
 * A class that represents a picture.  This class inherits from 
 * SimplePicture and allows the student to add functionality to
 * the Picture class.  
 * 
 * @author Barbara Ericson ericson@cc.gatech.edu
 */
public class Picture extends SimplePicture 
{
  ///////////////////// constructors //////////////////////////////////
  
  /**
   * Constructor that takes no arguments 
   */
  public Picture ()
  {
    /* not needed but use it to show students the implicit call to super()
     * child constructors always call a parent constructor 
     */
    super();  
  }
  
  /**
   * Constructor that takes a file name and creates the picture 
   * @param fileName the name of the file to create the picture from
   */
  public Picture(String fileName)
  {
    // let the parent class handle this fileName
    super(fileName);
  }
  
  /**
   * Constructor that takes the width and height
   * @param height the height of the desired picture
   * @param width the width of the desired picture
   */
  public Picture(int height, int width)
  {
    // let the parent class handle this width and height
    super(width,height);
  }
  
  /**
   * Constructor that takes a picture and creates a 
   * copy of that picture
   * @param copyPicture the picture to copy
   */
  public Picture(Picture copyPicture)
  {
    // let the parent class do the copy
    super(copyPicture);
  }
  
  /**
   * Constructor that takes a buffered image
   * @param image the buffered image to use
   */
  public Picture(BufferedImage image)
  {
    super(image);
  }
  
  ////////////////////// methods ///////////////////////////////////////
  
  /**
   * Method to return a string with information about this picture.
   * @return a string with information about the picture such as fileName,
   * height and width.
   */
  public String toString()
  {
    String output = "Picture, filename " + getFileName() + 
      " height " + getHeight() 
      + " width " + getWidth();
    return output;
    
  }
  
  /** Method to set the blue to 0 */
  public void zeroBlue()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        pixelObj.setBlue(0);
      }
    }
  }
  
  /** Method that mirrors the picture around a 
    * vertical mirror in the center of the picture
    * from left to right */
  public void mirrorVertical()
  {
    Pixel[][] pixels = this.getPixels2D();
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int width = pixels[0].length;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; col < width / 2; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][width - 1 - col];
        rightPixel.setColor(leftPixel.getColor());
      }
    } 
  }
  
  /** Mirror just part of a picture of a temple */
  public void mirrorTemple()
  {
    int mirrorPoint = 276;
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int count = 0;
    Pixel[][] pixels = this.getPixels2D();
    
    // loop through the rows
    for (int row = 27; row < 97; row++)
    {
      // loop from 13 to just before the mirror point
      for (int col = 13; col < mirrorPoint; col++)
      {
        count++;
        leftPixel = pixels[row][col];      
        rightPixel = pixels[row]                       
                         [mirrorPoint - col + mirrorPoint];
        rightPixel.setColor(leftPixel.getColor());
      }
    }
    
    System.out.println(count);
  }
  
  /** copy from the passed fromPic to the
    * specified startRow and startCol in the
    * current picture
    * @param fromPic the picture to copy from
    * @param startRow the start row to copy to
    * @param startCol the start col to copy to
    */
  public void copy(Picture fromPic, 
                 int startRow, int startCol)
  {
    Pixel fromPixel = null;
    Pixel toPixel = null;
    Pixel[][] toPixels = this.getPixels2D();
    Pixel[][] fromPixels = fromPic.getPixels2D();
    for (int fromRow = 0, toRow = startRow; 
         fromRow < fromPixels.length &&
         toRow < toPixels.length; 
         fromRow++, toRow++)
    {
      for (int fromCol = 0, toCol = startCol; 
           fromCol < fromPixels[0].length &&
           toCol < toPixels[0].length;  
           fromCol++, toCol++)
      {
        fromPixel = fromPixels[fromRow][fromCol];
        toPixel = toPixels[toRow][toCol];
        toPixel.setColor(fromPixel.getColor());
      }
    }   
  }

  /** Method to create a collage of several pictures */
  public void createCollage()
  {
    Picture flower1 = new Picture("images/flower1.jpg");
    Picture flower2 = new Picture("images/flower2.jpg");
    this.copy(flower1,0,0);
    this.copy(flower2,100,0);
    this.copy(flower1,200,0);
    Picture flowerNoBlue = new Picture(flower2);
    flowerNoBlue.zeroBlue();
    this.copy(flowerNoBlue,300,0);
    this.copy(flower1,400,0);
    this.copy(flower2,500,0);
    this.mirrorVertical();
    this.write("images/collage.jpg");
  }
  
  
  /** Method to show large changes in color 
    * @param edgeDist the distance for finding edges
    */
  public void edgeDetection(int edgeDist)
  {
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    Pixel topPixel = null;
    Pixel bottomPixel = null;
    Pixel[][] pixels = this.getPixels2D();
    Color rightColor = null;
    Color topColor = null;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; 
           col < pixels[0].length-1; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][col+1];
        rightColor = rightPixel.getColor();
        if (leftPixel.colorDistance(rightColor) > 
            edgeDist)
          leftPixel.setColor(Color.BLACK);
        else
          leftPixel.setColor(Color.WHITE);
        
        bottomPixel = pixels[row][col];
        topPixel = pixels[row][col+1];
        topColor = topPixel.getColor();
        if (bottomPixel.colorDistance(topColor) > edgeDist)
        	bottomPixel.setColor(Color.BLACK);
        else
        	bottomPixel.setColor(Color.WHITE);
      }
    }
  }
  
  
  /* Main method for testing - each class in Java can have a main 
   * method 
   */
  public static void main(String[] args) 
  {
    Picture beach = new Picture("images/beach.jpg");
    beach.explore();
    beach.zeroBlue();
    beach.explore();
  }
  
  public void keepOnlyBlue()
  {
	  Pixel[][] pixels = this.getPixels2D();
	  for (Pixel[] rowArray : pixels)
	  {
		  for (Pixel pixelObj : rowArray)
		  {
			  pixelObj.setRed(0);
			  pixelObj.setGreen(0);
		  }
	  }
  }
  
  public void negate()
  {
	  Pixel[][] pixels = this.getPixels2D();
	  for (Pixel[] rowArray : pixels)
	  {
		  for (Pixel pixelObj : rowArray)
		  {
			  pixelObj.setRed(255 - pixelObj.getRed());
		      pixelObj.setBlue(255 - pixelObj.getBlue());
		      pixelObj.setGreen(255 - pixelObj.getGreen());
		  }
	  }
  }
  
  public void grayscale()
  {
	  Pixel[][] pixels = this.getPixels2D();
	  for (Pixel[] rowArray : pixels)
	  {
		  for (Pixel pixelObj : rowArray)
		  {
			  pixelObj.setRed((pixelObj.getRed() + pixelObj.getBlue() + pixelObj.getGreen()) / 3);
		      pixelObj.setBlue((pixelObj.getRed() + pixelObj.getBlue() + pixelObj.getGreen()) / 3);
		      pixelObj.setGreen((pixelObj.getRed() + pixelObj.getBlue() + pixelObj.getGreen()) / 3);
		  }
	  }
  }
  
  public void mirrorVerticalRightToLeft()
  {
    Pixel[][] pixels = this.getPixels2D();
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int width = pixels[0].length;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; col < width / 2; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][width - 1 - col];
        leftPixel.setColor(rightPixel.getColor());
      }
    } 
  }
  
  public void mirrorHorizontal()
  {
    Pixel[][] pixels = this.getPixels2D();
    Pixel topPixel = null;
    Pixel bottomPixel = null;
    int height = pixels.length;
    for (int row = 0; row < height/2; row++)
    {
      for (int col = 0; col < pixels[0].length; col++)
      {
        topPixel = pixels[row][col];
        bottomPixel = pixels[row - height - 1][col];
        bottomPixel.setColor(topPixel.getColor());
      }
    } 
  }
  
  public void mirrorHorizontalBotToTop()
  {
	  Pixel[][] pixels = this.getPixels2D();
	  Pixel topPixel = null;
	  Pixel bottomPixel = null;
	  int height = pixels.length -1 ;
	  int width = pixels[0].length - 1;
	  for (int row = 0; row < height / 2; row++)
	  {
	    for (int col = 0; col < width; col++) 
	    {
	      bottomPixel = pixels[row][col];
	      topPixel = pixels[height - row][col];
	      bottomPixel.setColor(topPixel.getColor());
	    }
	  }
  }
  
  public void mirrorArms()
  {
	  Pixel leftPixel = null;
	  Pixel rightPixel = null;
	    
	  int rightBound = 196, leftBound = 170, bottomBound = 293, topBound = 119;
	  int mirrorPoint = rightBound;
	    
	  int count = 0;
	  Pixel[][] pixels = this.getPixels2D();
	    
	  for (int col = 106; col < bottomBound; col++)
	  {
	    for (int row = topBound; row < mirrorPoint; row++)
	    {
	        
	      leftPixel = pixels[row][col];      
	      rightPixel = pixels[mirrorPoint - row + mirrorPoint][col];
	      rightPixel.setColor(leftPixel.getColor());
	    }
	  }
  }
  
  public void mirrorGull()
  {
	  int mirrorPoint = 354;
	  Pixel leftPixel;
	  Pixel rightPixel;
	  int count = 0;
	  Pixel[][] pixels = this.getPixels2D();
	    
	  for (int row = 231; row < 322; row++)
	  {
	    for (int col = 237; col < mirrorPoint; col++)
	    {
	        
	      leftPixel = pixels[row][col];      
	      rightPixel = pixels[row][mirrorPoint - col + mirrorPoint];
	      rightPixel.setColor(leftPixel.getColor());
	    }
	  }
  }
  
  public void copyTwo(Picture fromPic, 
          int startRow, int startCol, int sRow, int eRow, int sCol, int eCol)
  {
	  Pixel fromPixel = null;
	  Pixel toPixel = null;
	  Pixel[][] toPixels = this.getPixels2D();
	  Pixel[][] fromPixels = fromPic.getPixels2D();
	  for (int fromRow = sRow, toRow = startRow; 
			  fromRow < eRow &&
			  toRow < toPixels.length; 
			  fromRow++, toRow++)
	  {
		  for (int fromCol = sCol, toCol = startCol; 
				  fromCol < eCol &&
				  toCol < toPixels[0].length;  
				  fromCol++, toCol++)
		  {
			  fromPixel = fromPixels[fromRow][fromCol];
			  toPixel = toPixels[toRow][toCol];
			  toPixel.setColor(fromPixel.getColor());
		  }
	  }   
	}
  
  public void myCollage()
  {
	  Picture kitten2 = new Picture("images/kitten2.jpg");
	  Picture snowman = new Picture("images/snowman.jpg");
	  this.copy(kitten2,0,200);
	  this.copyTwo(snowman,120,50,50, 90, 20, 60);
	  Picture g = new Picture(kitten2);
	  g.grayscale();
	  this.copy(g,100,0);
	  
	  this.write("collage.jpg");
  }
  
  public void blur(int x, int y, int w, int h)
  {
	  Pixel[][] pixels = this.getPixels2D();
	  Color averageColor = null;
	  
	  for (int r = y; r < h + y; r++)
	  {
		  for (int c = x; c < w + x; c++)
		  {
			  Pixel pix = pixels[r][c];
			  int averageRed = 0;
			  int averageGreen = 0;
			  int averageBlue = 0;
			  int count = 0;
			  
			  for (int tempR = r - h/2; tempR <= r + h/2 && tempR < pixels.length; tempR++)
			  {
				  for (int tempC = c - w/2; tempC <= c + w/2 && tempC < pixels[r].length; c++)
				  {
					  if (tempR < 0)
						  tempR = 0;
					  if (tempC < 0)
						  tempC = 0;
					  
					  averageRed += pixels[tempR][tempC].getRed();
					  averageGreen += pixels[tempR][tempC].getGreen();
					  averageBlue += pixels[tempR][tempC].getBlue();
					  count++;
				  }
			  }
			  averageRed /= count;
			  averageBlue /= count;
			  averageGreen /= count;
			  
			  averageColor = new Color(averageRed, averageBlue, averageGreen);
			  
			  pix.setColor(averageColor);
		  }
	  }
	  
  }
  
} // this } is the end of class Picture, put all new methods before this
