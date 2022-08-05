// Joshua Chen
// 5/23/2022
// CSE142
// TA: Andrew Kuhn
// Take-home Assessment #7
//
// This program interprets and calculate the personality testing result to determine each person's
// personality. There are four dimensions of the test: Extrovert(E)/Introvert(I), 
// Sensation(S)/iNtuition(N), Thinking(T)/Feeling(F), and Judging(J)/Perceiving(P).

import java.util.*;
import java.io.*;

public class Personality {
   public static void main(String[] args) throws FileNotFoundException {
      Scanner console = new Scanner(System.in);
      introduction();

      //ask the user to type in input file name
      System.out.print("input file name? ");
      String inputName = console.nextLine();

      //ask the user to type in output file name
      System.out.print("output file name? ");
      String outputName = console.nextLine();

      PrintStream output = new PrintStream(new File(outputName));
      Scanner input = new Scanner(new File(inputName));
      
      while (input.hasNextLine()) {
         String nameLine = input.nextLine();
         output.print(nameLine + ": ");
         String answerLine = input.nextLine();
         int[] answerCount = new int[8];
         getAnswers(answerLine, answerCount, output);
      }
   }
   
   // introduction method generates how this program process the input file and how this program 
   // converts the answers to different personality type.
   public static void introduction() {
      System.out.println("This program processes a file of answers to the");
      System.out.println("Keirsey Temperament Sorter. It converts the");
      System.out.println("various A and B answers for each person into");
      System.out.println("a sequence of B-percentages and then into a");
      System.out.println("four-letter personality type.");
      System.out.println();
   }
   
   // getAnswers method first identifies the types of questions each answer is referring to and  
   // calls countAnswers stores each answer to its matched answerCount array. 
   // String answerLine - this parameter passes the line read in by the Scanner 
   // int[] answerCount - this parameter allows the countAnswers method to store the counts of 
   //                     answers
   // PrintStream output - this parameter allows the program to print messages at the output file
   public static void getAnswers(String answerLine, int[] answerCount, PrintStream output) {
      int type = 0;
      for (int i = 0; i < answerLine.length(); i++) {
         String answer = answerLine.charAt(i) + "";
         if (i % 7 == 0) {
            type = 0;
            countAnswers(answerCount, answer, type);
         } else if (i % 7 == 1 || i % 7 == 2) {
            type = 1;
            countAnswers(answerCount, answer, type);
         } else if (i % 7 == 3 || i % 7 == 4) {
            type = 2;
            countAnswers(answerCount, answer, type);
         } else {
            type = 3;
            countAnswers(answerCount, answer, type);
         }
      }
      getPersonality(answerCount, output);
   }
   
   // countAnswers method distinguishes the answers and store them in matched answerCount array
   // int[] answerCount - this parameter allows the method to store the counts of answers
   // String answer - this parameter passes the answer read in by the Scanner to this method 
   // int type - this parameter tell the program which dimension of question it is processing 
   public static void countAnswers(int[] answerCount, String answer, int type) {
      if (answer.equalsIgnoreCase("A")) {
         answerCount[type * 2]++;
      } else if (answer.equalsIgnoreCase("B")) {
         answerCount[type * 2 + 1]++;
      }
   }

   // getPersonality method calculate the percentage of "A" and "B" and determine the personality 
   // It also output the personality test result to the output file
   // int[] answerCount - this parameter allows the method to calculate the percentage 
   // PrintStream output - this parameter allows the program to print messages at the output file
   public static void getPersonality(int[] answerCount, PrintStream output) {
      String [] personalityCount = new String[4];
      String[] personality = {"E", "I", "S", "N", "T", "F", "J", "P"};
      int[] percentArr = new int[4];
      for (int i = 0; i < 4; i++) {
         double percent = answerCount[i * 2 + 1] / (double)(answerCount[i * 2] + 
                                                            answerCount[i * 2 + 1]) * 100;
         int roundedPercent = (int)Math.round(percent);
         percentArr[i] = roundedPercent; 
         if (roundedPercent > 50) {
            personalityCount[i] = personality[i * 2 + 1];
         } else if (roundedPercent < 50) {
            personalityCount[i] = personality[i * 2];
         } else {
            personalityCount[i] = "X";
         }
      }
      output.print(Arrays.toString(percentArr) + " = "); 
      for (int i = 0; i < 4; i++) {
         output.print(personalityCount[i]);
      }
      output.println();
   }
}