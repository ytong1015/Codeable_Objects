package com.ornament.lsys;


import java.util.Vector;

import processing.core.PApplet;

import com.datatype.Point;

import com.ornament.Pattern;
import com.primitive2d.Ellipse;
import com.primitive2d.Line;
import com.primitive2d.LineCollection;
import com.primitive2d.Polygon;
import com.primitive2d.Drawable;
import com.primitive2d.TurtleStruct;




/* LSystem Class                 */

// An LSystem has a starting sentence
// An a ruleset
// Each generation recursively replaces characteres in the sentence
// Based on the rulset

public class LSystem extends Pattern{

  String sentence;     // The sentence (a String)
  Rule[] ruleset;      // The ruleset (an array of Rule objects)
  int generation;      // Keeping track of the generation #
  
  //String todo;
  //double len=500;
  //double theta=Math.PI/2;
 
  //Point turtlePos;
  //double turtleRot;
  
  
  // Construct an LSystem with a startin sentence and a ruleset
  public LSystem(String axiom, Rule[] r) {
   super();
	sentence = axiom;
    ruleset = r;
    generation = 0;
    //turtleRot =0;
    //turtlePos = TurtleStruct.location;
    //todo = sentence;
  }

  // Generate the next generation
  public void generate() {
    // An empty StringBuffer that we will fill
    StringBuffer nextgen = new StringBuffer();
    // For every character in the sentence
    for (int i = 0; i < sentence.length(); i++) {
      // What is the character
      char curr = sentence.charAt(i);
      // We will replace it with itself unless it matches one of our rules
      String replace = "" + curr;
      // Check every rule
      for (int j = 0; j < ruleset.length; j++) {
        char a = ruleset[j].getA();
        // if we match the Rule, get the replacement String out of the Rule
        if (a == curr) {
          replace = ruleset[j].getB();
          break; 
        }
      }
      // Append replacement String
      nextgen.append(replace);
    }
    // Replace sentence
    sentence = nextgen.toString();
    // Increment generation
    generation++;
  }

  /*
  public void render() {
	  System.out.println("render");
	    for (int i = 0; i < todo.length(); i++) {
	      char c = todo.charAt(i);
	      if (c == 'F' || c == 'G') {
	    	TurtleStruct.pen = true;
	        forward(len);
	        TurtleStruct.location = new Point(len,0);
	        TurtleStruct.pen = false;
	        forward(len);
	        
	      } 
	      else if (c == '+') {
	    	 TurtleStruct.angle += theta;
	    	 right(Math.toDegrees(theta));
	    	 
	      } 
	      else if (c == '-') {
	    	 TurtleStruct.angle += -theta;
	    	 left(Math.toDegrees(theta));
	      } 
	      
	      else if (c == '[') {
	        turtlePos = TurtleStruct.location;
	        turtleRot = TurtleStruct.angle;
	      } 
	      else if (c == ']') {
            TurtleStruct.location = turtlePos;
            TurtleStruct.angle = turtleRot;
	      }
	    	  
	    } 
	  }
  
  */
  
  
  
  
  public String getSentence() {
    return sentence; 
  }

  public void setSentence(String sentence){
	this.sentence = sentence;  
  }
  
  public int getGeneration() {
    return generation; 
  }
  
  public void setGeneration(int generation){
  this.generation = generation;
  }
  
  public void setRuleset(){
  }
  

}


