import java.io.*;
import java.util.*;

public class PHASE1
{
   
   char R[];
   int IC;
   char IR[];   
   char M[][];
   boolean C = false;
   int SI=3;
 
 public void init()
 {
   System.out.println("INIT");
   this.R = new char[4];
   this.IR = new char[4];   
   this.M = new char[100][4];	
 }

 public void MOS(Scanner sc,Integer address)
 {
	String line = new String();
	Integer j=0,last=4;
	switch(this.SI)
	{
		case 1:
		  //READ();
		  //System.out.println("READ " + address);
		  this.IR[3]='0';
		  line = sc.nextLine();
		  
		  while(j <= line.length())
		  {
			if(last > line.length())   last = line.length();  
		    this.M[address++] = line.substring(j,last).toCharArray();
			//System.out.println("chk"+String.valueOf(this.M[0]));
		   // System.out.println(String.valueOf(this.M[address-1]));
            j+=4;
		    last = j+4;
            
		  }	
          j=0;
		  //address = (address/10 + 1) * 10;
		  
		  break;
		case 2:
          //WRITE();
		  this.IR[3] = '0';
		 // System.out.println("WRITE" + address);
		  for(j=address;j<= address+9;j++)
		  System.out.println("PRINT: " + String.valueOf(this.M[j]));
		  
		  break;
        case 3:	
          //TERMINATE(); return;
		  break;
        default:
          System.out.println("No System Interrupts");		
	}	
 }
 
 public void exec_user_prog(Scanner sc,Integer address)
 { 
       String opcode="";
	   Integer operand=0;
       System.out.println("EXEC_USER_PROG");
	   
	   do
	   {
		   this.IR = this.M[this.IC];                                             //IR <- M[IC]
		   
		   this.IC += 1;  		                                             //IC ++
		   if(String.valueOf(this.IR).length() > 1)
		   { opcode = String.valueOf(this.IR).substring(0,2);
		     operand = Integer.parseInt(String.valueOf(this.IR).substring(2,4));
		   }
	       else
		   {opcode = String.valueOf(this.IR); }  
		   switch(opcode)
		   {
			   case "GD":
			   
			    this.SI = 1;
				this.MOS(sc,operand);
				
				break;
			   case "PD":
			   
                this.SI = 2;
				this.MOS(sc,operand);
				
				break;
			   case "H":
			    this.SI = 3;
				this.MOS(sc,operand);
				break;
			   case "LR":
			   
				this.R = this.M[operand];
				break;
				
			   case "CR":
				if(String.valueOf(this.R).equals(String.valueOf(this.M[operand])))
				 this.C = true;
				else
				 this.C = false;
				
				break;
			   case "SR":
                 this.M[operand] = this.R;
                 break;
			   case "BT":
                 if(this.C) IC = operand;
                 break;				 
				 
		   }
	  }while(this.SI != 3);
    
 }

 public static void main(String args[]) throws FileNotFoundException, StringIndexOutOfBoundsException
 {
   PHASE1 p = new PHASE1(); 
   int m=0,i=0,j=0,last=4;
   String line="";
   String sub = "";
   File file = new File("job2.txt");
   Scanner sc = new Scanner(file);
    
   while(sc.hasNextLine())
  {
    line = sc.nextLine();
	
    if(line.length() > 3) sub = line.substring(0,4);
    else   sub = line;
	switch(sub)
	{
		case "$AMJ":
		 System.out.println("Here i begin");
	     p.init();
		 break;
		case "$DTA":
		 p.IC = 0;
		 p.exec_user_prog(sc,(i/10+1)*10);
		 break;
		case "$END":
         break;
        default:
  		 
         while(j <= line.length())
		  {
		    p.M[i++] = line.substring(j,last).toCharArray();
			//System.out.println(String.valueOf(p.M[i-1]));
             j+=4;
		     last = j+4;
             if(last > line.length())   last = line.length();
		  }	
         j=0;		
          		
	}
   
  }

 System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		System.out.println(String.valueOf(p.M[0]));
	   for(j=0;j<=99;j++)
	   {
		   System.out.println("MEM: " + j + String.valueOf(p.M[j]));
	   }
 
 }
}
