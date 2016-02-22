package edu.duke;

import edu.duke.*;
import org.apache.commons.csv.*;
import java.util.*;

import javax.swing.JOptionPane;

import java.io.*;


public class BabyName {
	//Hàm tính tổng số nam nữ
	public static void totalBirths (FileResource fr) {
        int totalBirths = 0;
        int totalBoys = 0;
        int totalGirls = 0;
        for (CSVRecord rec : fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(rec.get(2));
            totalBirths += numBorn;
            if (rec.get(1).equals("M")) {
                totalBoys++;
            }
            else {
                totalGirls++;
            }
        }
        System.out.println("total births = " + totalBirths);
        System.out.println("female girls = " + totalGirls);
        System.out.println("male boys = " + totalBoys);
    }
	
	//Hàm lấy hạng của 1 tên bất kì
	public static Integer getRank(int year, String name, String gender){
        int count = 1;
        int rank;
        int total = 0;
        int totalGirls = 0;
       FileResource fr = new FileResource("data/yob"+Integer.toString(year)+".csv");
       for (CSVRecord rec : fr.getCSVParser(false)){
           total ++;
           if (rec.get(1).equals("F")) {
                totalGirls ++;
            }
        }
        
       for (CSVRecord rec : fr.getCSVParser(false)){ 
           if (rec.get(1).equals("F")){
               if (rec.get(1).equals(gender) && rec.get(0).equals(name)){
                   
                   break;
               }
               else count = count +1; 
           } 
           else 
           if (rec.get(1).equals("M")){
              
               if (rec.get(1).equals(gender) && rec.get(0).equals(name)){
                   count = count - totalGirls;
                   break;
               }
               else count = count +1 ;
           }
       }
       
       if (count<=total) {
           rank = count;
       } else rank = -1;
           return rank;
       }
	//Hàm lấy tên của một thứ hạng bất kì
	public static String getName(int year, int rank, String gender){
        int count = 0;
        int countM = 0;
        int total = 0;
        int totalGirls = 0;
        String nameF = "";
        String nameM = "" ;
        String name = "";
       FileResource fr = new FileResource("data/yob"+Integer.toString(year)+".csv");
       for (CSVRecord rec : fr.getCSVParser(false)){
           total ++;
           if (rec.get(1).equals("F")) {
                totalGirls ++;
            }
        }
        
       for (CSVRecord rec : fr.getCSVParser(false)){
          
                if (rec.get(1).equals("F")) {
                    count++;
                    if ((count == rank)) {
                        nameF = rec.get(0);
                       
                    }
                } else
                
                    if (rec.get(1).equals("M")){
                        count++;
                        
                        if ((count - totalGirls == rank)) {
                            nameM = rec.get(0);
                            
                        }
                    }
               
        }
         if ((rank > total) || (rank <1)) {
               name = "NO NAME";
            } else 
                if (gender.equals("F")){
                    name = nameF;
                } else if (gender.equals("M")){
                    name = nameM;
                }
        return name;
    }
	
	//Hàm hoán đổi tên cùng rank giữa hai năm
	 public static void whatIsNameInYear(String name,int year,int newYear,String gender){
	        int rank = getRank(year,name,gender);
	        String newname = getName(newYear,rank,gender);
	        String gender2;
	        if (gender.equals("F")){
	            gender2 = "she";
	        } else gender2 = "he";
	        System.out.printf("%s born in %d would be %s if %s was born in %d\n",name,year,newname,gender2,newYear);
	      
	    }
	

	
	//Hàm tìm thứ hạng trung bình của một tên trong tất cả các năm
	 public static double getAverageRank(String name,String gender){
	        String yearP = "";
	        String yearS = "";
	       
	        int begin=0;
	        int year = 0;
	        int count =0;
	        double s=0;
	       
	        DirectoryResource dr = new DirectoryResource();
	       for (File f : dr.selectedFiles()){
	           yearP = f.getPath();
	           begin = yearP.indexOf("yob",0);
	           yearS = yearP.substring(begin+3,begin+7);
	           year = Integer.parseInt(yearS); 
	           count++;
	            s = s + getRank(year,name,gender);
	        }
	        
	       return s/count;
	    }
	 
	 //Hàm tìm tổng số baby sinh ra từ rank của 1 tên bất kì
	 public static int getTotalBirthsRankedHigher(int year,String name,String gender){
	       int totalGirls=0;
	       int totalBoys=0;
	       int s = 0;
	       
	       FileResource fr = new FileResource("data/yob"+Integer.toString(year)+".csv");
	      for (CSVRecord rec : fr.getCSVParser(false)) {
	            int numBorn = Integer.parseInt(rec.get(2));
	           // totalBirths += numBorn;
	           if (rec.get(1).equals("F")){
	               if ((rec.get(1).equals(gender) && (rec.get(0).equals(name)))) {
	                   break;
	            
	                } else totalGirls = totalGirls + numBorn;
	        
	           } else if (rec.get(1).equals("M")){
	               if ((rec.get(1).equals(gender) && (rec.get(0).equals(name)))){
	                   break;
	                } else totalBoys = totalBoys + numBorn;
	            }
	        }
	        if (gender.equals("F")) {
	            s = totalGirls;
	        } else s = totalBoys;
	        return s;
	}
	
	 
	 
	public static void main(String[] args){
		//test hàm totalBirth
		FileResource fr = new FileResource();
        totalBirths(fr);
        
        //test hàm getRank
        int yearRank;
        System.out.println("*********************************");
        String nameRank, genderRank;
        Scanner n1 = new Scanner(System.in);
        Scanner y1 = new Scanner(System.in);
        Scanner g1 = new Scanner(System.in);
        System.out.print("Nhập năm : ");
        yearRank = y1.nextInt();
        System.out.print("Nhập tên: ");
        nameRank = n1.nextLine();
        System.out.print("Nhập giới tính: ");
        genderRank = g1.nextLine();
        System.out.println ("Mức độ phổ biến của tên này đứng thứ "+getRank(yearRank,nameRank,genderRank));
      
		//test hàm getName
        int yearName,rankName;
        String genderName;
        System.out.println("*********************************");
        Scanner r2 = new Scanner(System.in);
        Scanner y2 = new Scanner(System.in);
        Scanner g2 = new Scanner(System.in);
        System.out.print("Nhập năm : ");
        yearName = y2.nextInt();
        System.out.print("Nhập hạng: ");
        rankName = r2.nextInt();
        System.out.print("Nhập giới tính: ");
        genderName = g2.nextLine();
        System.out.printf("Tên của giới tính %s phổ biến đứng thứ %d là %s\n",genderName,rankName,getName(yearName,rankName,genderName));
	
        //test hàm whatisNameinYear
        int yearW, newYear;
        String genderW,nameW;
        System.out.println("*********************************");
        Scanner n3 = new Scanner(System.in);
        Scanner y3 = new Scanner(System.in);
        Scanner ny3 = new Scanner(System.in);
        Scanner g3 = new Scanner(System.in);
        System.out.print("Nhập tên: ");
        nameW = n3.nextLine();
        System.out.print("Nhập năm: ");
        yearW = y3.nextInt();
        System.out.print("Nhập năm mới: ");
        newYear = ny3.nextInt();
        System.out.print("Nhập giới tính: ");
        genderW = g3.nextLine();
        whatIsNameInYear(nameW,yearW,newYear,genderW);
        
        //test hàm getAverageRank
        String nameA,genderA;
        System.out.println("*********************************");
        Scanner n4 = new Scanner(System.in);
        Scanner g4 = new Scanner(System.in);
        System.out.print("Nhập tên: ");
        nameA = n4.nextLine();
        System.out.print("Nhập giới tính: ");
        genderA = g4.nextLine();
        System.out.printf("Average Rank = %f",getAverageRank(nameA,genderA));
        
        
        //test hàm getTotalBirthsRankedHigher
        int yearT;
        String nameT, genderT;
        System.out.println("*********************************");
        Scanner n5 = new Scanner(System.in);
        Scanner y5 = new Scanner(System.in);
        Scanner g5 = new Scanner(System.in);
        System.out.print("Nhập tên: ");
        nameT = n5.nextLine();
        System.out.print("Nhập giới tính: ");
        genderT = g5.nextLine();
        System.out.print("Nhập năm: ");
        yearT = y5.nextInt();
        System.out.printf("Total = %d",getTotalBirthsRankedHigher(yearT,nameT,genderT));
        
        

	
	}
	

}
