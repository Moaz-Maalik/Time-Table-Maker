
package TimeTableMaker ;
import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;
import java.util.Random;
import java.util.Collections;
import java.util.Arrays;


public class TimeTableMaker {
    
    
    static ArrayList<String> readfile()  // filing function reades the registeration file an arraylist of string
    {
        //System.out.println("Enter file name that is to be loaded");
        //Scanner scanner = new Scanner(System.in);
        //String name = scanner.nextLine();
        ArrayList<String> roomcaps = new ArrayList<String>();

        try
        {  
            File file = new File("reg.txt"); 
            
            BufferedReader br = new BufferedReader(new FileReader(file)); 
           
            String st;
            String st2;
            while ((st = br.readLine()) != null) 
            {
                
                
                st2=st.replace("\t","");
                roomcaps.add(st2);
                
                
                
            }
            
            //System.out.println(roomcaps.size());
            
          
            
        } 
        catch (Exception ex) 
        {  
            System.out.println(ex.getMessage());  
        }
        
        return roomcaps;
        

    }
    
    
    
    static ArrayList<Integer[]> crossover(Integer[]a,Integer[]b) // crossover function generators a random point and crossesover that point and returns 2 cromosomes
    {
        
        Random rand = new Random();
        int point=rand.nextInt((222-0)+1)+0;
        
        Integer[] finalcromo1 =new Integer[223];
        Integer[] finalcromo2 =new Integer[223];
        
        ArrayList<Integer[]> aftercrossover = new ArrayList<Integer[]>();
        
        for(int i=0;i<point;i++)
        {
            finalcromo1[i]=a[i];
        }
        for(int i=point;i<223;i++)
        {
            finalcromo1[i]=b[i];
        }
        
        for(int i=0;i<point;i++)
        {
            finalcromo2[i]=b[i];
        }
        for(int i=point;i<223;i++)
        {
            finalcromo2[i]=a[i];
        }
        
        aftercrossover.add(finalcromo1);
        aftercrossover.add(finalcromo2);
        
        
        
        return aftercrossover;
    
    
    }
    
    static int givetotalstudentsinthiscourse(int course,ArrayList<String> reg)// this returns the total students in a course 
    {   
        int sum=0;
        
        //ArrayList<String> reg = new ArrayList<>();
        //reg=readfile();
        
        
        String str=reg.get(course);
        
        for (int i = 0; i <str.length(); i++) 
        {
            if(str.charAt(i)=='1')
            {
                sum++;
            }
         
            
        }
            
            
        
        
        
        return sum;
    
    }
    
    static int checkroomcaps(Integer[]a,ArrayList<String> reg)// this function gives the number of slots in which the students overflow the room capacity
    {
        int total_slots_with_overflow=0;
        int totalroomcaps=1287;
        ArrayList<Integer> slots = new ArrayList<Integer>();
        
        for(int i=0;i<223;i++)
        {
            if(!slots.contains(a[i]))
            {
                
                slots.add(a[i]);
            }
        }
        
        int [] studentcountbyslot=new int[21]; //no of slots
        
        for(int j=0;j<slots.size();j++)
        {
            for(int i=0;i<223;i++)
            {
                if(a[i]==slots.get(j))
                {
                    //System.out.println("Course "+ i+"  has "+ a[i]+"  slot");
                    int val=givetotalstudentsinthiscourse(i,reg);
                    studentcountbyslot[a[i]]=studentcountbyslot[a[i]]+val;

                }

            }
        }
        //System.out.println();
        //System.out.println("student count by slot");
//        for(int i=0;i<studentcountbyslot.length;i++)
//        {
//            System.out.print(studentcountbyslot[i]+" ");
//        
//        }
        
        for(int i=0;i<studentcountbyslot.length;i++)
        {
            if(studentcountbyslot[i]>totalroomcaps)
            {
                total_slots_with_overflow++;
            }
        
        }
        
        return total_slots_with_overflow;
        
    
    
    }
    
    static int checkclashes(Integer[]a,ArrayList<String> reg)// this function returns number of students having more than 2 papers in a slot
    {
        int no_ofstudents=0;
        ArrayList<ArrayList<Integer> > slotswithcourses =new ArrayList<ArrayList<Integer>>();
       
        
//        for(int i=0;i<223;i++)
//        {
//            System.out.print(a[i]+ " ");
//        }
        
        
        for(int i=0;i<21;i++)
        {
            ArrayList<Integer> courses = new ArrayList<Integer>();
            slotswithcourses.add(courses);
        }
        
        for(int i=0;i<223;i++)
        {
            int slot=a[i];
            slotswithcourses.get(slot).add(i);
        
        }
        //System.out.println();
//        for(int i=0;i<slotswithcourses.size();i++)
//        {
//            System.out.print("Slot "+i+"  has courses  ");
//            for (int j=0;j<slotswithcourses.get(i).size();j++) 
//            {
//                System.out.print(slotswithcourses.get(i).get(j) + " ");
//               
//            }
//            System.out.println();
//        }
        
        for(int i=0;i<slotswithcourses.size();i++)
        {
            int coomomn=find_students_clashes_slotwise(slotswithcourses.get(i),reg);
            no_ofstudents=no_ofstudents+coomomn;
        
        }
        
        
        return no_ofstudents;
    
    }
    static Boolean checkifstudentregisted(int courseid,int studentid,ArrayList<String> reg) // this functions returns true if the given student is registered in the give course
    {
        //ArrayList<String> reg = new ArrayList<>();
        //reg=readfile();
        
        if(reg.get(courseid).charAt(studentid)=='1')
        {
            return true;
        }
        
        
        return false;
    }
    static int find_students_clashes_slotwise(ArrayList<Integer> courses,ArrayList<String> reg)//this function finds students have clashes in a given slot
    {
        int commonstudents=0;
        int temp=0;
        int totalstudents=3169;
        for(int i=0;i<totalstudents;i++)
        {
            temp=0;
            for(int j=0;j<courses.size();j++)
            {
                if(checkifstudentregisted(courses.get(j),i,reg))
                {
                    //System.out.println("First class found");
                    temp++;
                }
                if(temp==2)
                {
                    commonstudents++;
                    temp=0;
                    break;
                    
                }
            }
        }
        
        
        return commonstudents;
    
    }
    
    static int checkconsecutive(Integer[]a,ArrayList<String> reg)// this function checks how many students have papers in 2 or more than 2 consecutive slots
    {
        int no_ofstudents=0;
        ArrayList<ArrayList<Integer> > slotswithcourses =new ArrayList<ArrayList<Integer>>();
       
        
//        for(int i=0;i<223;i++)
//        {
//            System.out.print(a[i]+ " ");
//        }
        
        
        for(int i=0;i<21;i++) //check again
        {
            ArrayList<Integer> courses = new ArrayList<Integer>();
            slotswithcourses.add(courses);
        }
        
        for(int i=0;i<223;i++) //check again 
        {
            int slot=a[i];
            slotswithcourses.get(slot).add(i);
        
        }
        //System.out.println();
//        for(int i=0;i<slotswithcourses.size();i++)
//        {
//            System.out.print("Slot "+i+"  has courses  ");
//            for (int j=0;j<slotswithcourses.get(i).size();j++) 
//            {
//                System.out.print(slotswithcourses.get(i).get(j) + " ");
//               
//            }
//            System.out.println();
//        }
        
        for(int i=0;i<slotswithcourses.size()-1;i++)
        {
            ArrayList<Integer> studentsinslot=new ArrayList<Integer>();
            ArrayList<Integer> studentsinnextslot=new ArrayList<Integer>();
            
            studentsinslot=find_students_reg_slotwise(slotswithcourses.get(i),reg);
            studentsinnextslot=find_students_reg_slotwise(slotswithcourses.get(i+1),reg);
            
            no_ofstudents=no_ofstudents+tell_no_of_commonstudents(studentsinslot,studentsinnextslot);
            
            
            
        
        }
        
        return no_ofstudents;
    
    }
    
    static int tell_no_of_commonstudents(ArrayList<Integer> studentsinslot,ArrayList<Integer> studentsinnextslot)// this function tells the number of common students in given 2 slots .this is a helper function to the above function
    {
        int total=0;
        
        for (int i = 0; i < studentsinslot.size(); i++)
        {   
            if(studentsinnextslot.contains(studentsinslot.get(i)))
            {
                total++;
            
            }
            
        }
        
        
        
        return total;
    
    }
    
    static ArrayList<Integer> find_students_reg_slotwise(ArrayList<Integer> courses,ArrayList<String> reg)// returns the number of students registered in the courses in a given slot
    {
        int totalstudents=3169;
        
        ArrayList<Integer> studentsregister=new ArrayList<Integer>();
        for(int i=0;i<totalstudents;i++)
        {
            for(int j=0;j<courses.size();j++)
            {
                if(checkifstudentregisted(courses.get(j),i,reg))
                {
                    if(!studentsregister.contains(i))
                        {
                            studentsregister.add(i);
                        }
                    
                }
            }
        }
        
        return studentsregister;
        
    
    }
    
    static int checkfitness(Integer[]a,ArrayList<String> reg) // this is the main fitness function which uses the three main functions and than sums the values and return the fitnes of a given choromosome
    {
        
        
        int total_slots_with_overflow=checkroomcaps(a,reg);
        int number_students_having_two_exams_in_one_given_slot=checkclashes(a,reg);
        int number_of_students_having_exams_on_two_consecutive_slots=checkconsecutive(a,reg);
        
        int value=total_slots_with_overflow+number_students_having_two_exams_in_one_given_slot+number_of_students_having_exams_on_two_consecutive_slots;
        
        
        
        
        
        
        return value;
    
    }
    
    static ArrayList<Integer[]> populate()// this is the function to generate initial population of 10 choromosome with random filling of slots 
    {
        ArrayList<Integer[]> population = new ArrayList<Integer[]>();
        Random rand = new Random();
       
        for(int i=0;i<10;i++)
        {
            Integer[] a = new Integer[223];
            for(int j=0;j<223;j++)
            {
                a[j]=rand.nextInt((20-0)+1)+0;
            
            }
            
            population.add(a);
            
        
        }
        return population;
    }
    
    static Integer[] mutation(Integer[]a)// this is the mutaion function generates a random slot and places the slot at a random course index of the choromosome
    {
        Integer[] finalcromo1 =new Integer[223];
        Random rand = new Random();
        int point=rand.nextInt((222-0)+1)+0;
        int randomslot=rand.nextInt((20-0)+1)+0;
        a[point]=randomslot;
        //finalcromo1[point]=randomslot;
        
        return a;
        
    
    }
    
    static int bestfitness(ArrayList<Integer[]> population,ArrayList<String> reg)// this function returns the best fitnes the population 
    {
        int mini=checkfitness(population.get(0),reg);
        for(int i=0;i<population.size();i++)
        {
            int temp=checkfitness(population.get(i),reg);
            if(temp<mini)
            {
                
                mini=temp;
                
            }
        
        }
        
        return mini;
    
    }
    
    static ArrayList<Integer[]> givetop5(ArrayList<Integer[]> population,ArrayList<String> reg)//returns top5 chromosomes accordings to there scores
    {
        
        ArrayList<Integer[]> returnlist=new ArrayList<Integer[]>();
        
        //Integer[] temp=new Integer[223];
        for (int i = 1; i < population.size(); i++) 
        {
            for (int j = i; j > 0; j--) 
            {
               if (checkfitness(population.get(j),reg) < checkfitness(population.get(j-1),reg))
               {
                   Collections.swap(population,j,j-1);
//                 temp = population.get(j);
//                 population.get(j)= population.get(j-1);
//                 population.get(j-1) = temp;
               }
            }
        }
        
        for(int i=0;i<5;i++)
        {
            returnlist.add(population.get(i));
        }
        
        
        
        return returnlist;
    
    }
            
    
    public static void main(String[] args) 
    {
                
        ArrayList<Integer[]> population = new ArrayList<Integer[]>();
        
        population=populate();
        

        
        ArrayList<String> reg = new ArrayList<>();
        reg=readfile();
        
        
                
        
       
        
        
        
        
        
        int generation=0;
        
        
        while(true)
        {
            generation++;
            
            ArrayList<Integer[]> top5=givetop5(population,reg);
            
            population = new ArrayList<Integer[]>();
            for (int i = 0; i < 5; i++)
            {
                ArrayList<Integer[]> crossed=new ArrayList<Integer[]>();
                
                crossed=crossover(top5.get(0),top5.get(i));
                Integer[] temp=new Integer[223];
                Integer[] temp2=new Integer[223];
                temp=crossed.get(0);
                temp2=crossed.get(1);
                
                Random rand = new Random();
                int point=rand.nextInt((10-0)+1)+1;
                int point2=rand.nextInt((10-0)+1)+1;
                
                double randx=point/10;
                double randy=point2/10;
                
                
                if(randx<0.5)// probability of mutation is 0.5
                {
                    temp=mutation(crossed.get(0));
                    
                }
                
                
                if(randy<0.5)
                {
                    temp2=mutation(crossed.get(1));
                    
                }
                population.add(temp);
                population.add(temp2);
                
                
            }
            int bestfit=bestfitness(population,reg);
            
            System.out.println("Best fit of generation "+generation+" is "+bestfit);
            
            if(bestfit==0)
            {
                break;
            }
        
        }
//        
//        System.out.println(bestfitness(population,reg));
//        
//        
//        
//        
//        
//      
//       
////        System.out.println("Total slots with over flow = "+checkroomcaps(population.get(0),reg));
////        
////        System.out.println();
////        System.out.println();
////        
////        System.out.println("Total clashes = "+checkclashes(population.get(0),reg));
////        
////        System.out.println();
////        System.out.println();
////        
////        System.out.println("Total number_of_students_having_exams_on_two_consecutive_slots = "+checkconsecutive(population.get(0),reg));
////        
////        
////        
////        System.out.println("Fitness is   "+ checkfitness(population.get(0),reg));
////        
//        
//          
        
        
        
        
        
        
        
        
    }
    
}
