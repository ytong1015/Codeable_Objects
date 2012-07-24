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
  int generation;
  LTurtle lturtle;
  
  private float width = 500;
  private float height= 700;
  
  
  // Construct an LSystem with a startin sentence and a ruleset
  public LSystem(String axiom, Rule[] r, int gen) {
	sentence = axiom;
    ruleset = r;
    generation = gen;
    lturtle = new LTurtle(axiom,this.width,(float)Math.PI/2,this);
    TurtleStruct.location = new Point(0,height-1);
    lturtle.render();

  }

  // Generate the next generation
  public void generate() {
	for(int g = 0; g< generation; g++){
	  
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
    
    
    //System.out.println("g="+g+","+"sentence="+sentence+"\n\n\n");///////////////////////////// 
    
    // Increment generation
    lturtle.setToDo(sentence);
    lturtle.changeLen(0.3333);   
    TurtleStruct.location = new Point(0,height-1);
    TurtleStruct.angle = 0;
    lturtle.render();
	  }
  }
  


 
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


