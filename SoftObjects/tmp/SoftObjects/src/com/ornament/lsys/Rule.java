package com.ornament.lsys;


/* A Class to describe an
LSystem Rule                  */

public class Rule {
 char a;
 String b;

 public Rule(char a_, String b_) {
   a = a_;
   b = b_; 
 }
 
 
 //????????????
 public void setRule(char a_, String b_) {
	   a = a_;
	   b = b_; 
	 }
	 

 public char getA() {
   return a;
 }

 public String getB() {
   return b;
 }
 


}

