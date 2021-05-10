
import java.io.File;  
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Ex1 {


	public static void main(String[] args) {

		// reading from file java https://www.w3schools.com/java/java_files_read.asp
		Boolean time= false;
		Boolean open = false;
		String algorithm="";
		int n = 0;
		int m = 0;
		Integer[][] start=new Integer [n][m];
		Integer[][] goal=new Integer [n][m];


		try {

			File myObj = new File("input2.txt");
			Scanner myReader = new Scanner(myObj);

			//while (myReader.hasNextLine()) {

			//first line - save the algorithm name 
			String data = myReader.nextLine();
			algorithm=data;


			//second line 
			data = myReader.nextLine();

			switch(data) {
			case "with time":
				time=true;
				break;
			case "no time":
				time=false;
				break;
			}


			//third line
			data = myReader.nextLine();
			//print the open list to the screen
			switch(data) {
			case "with open":
				open=true;
				break;
			case "no open":
				open=false;
				break;
			}


			//fourth line
			data = myReader.nextLine();
			//	NxM
			String N="";
			String M="";
			int i=0;
			while(i<data.length()) {
				if(data.charAt(i)!='x') {
					N+=data.charAt(i);
					i++;
				}else {
					M=data.substring(i+1,data.length());
					i=data.length();
				}
			}
			n=Integer.parseInt(N);
			m=Integer.parseInt(M);
			
			//gets the matrix of the puzzle
			//it is integer and not int so it can be null
			start=new Integer [n][m];

			for (int j=0;j<n;j++) {
				data = myReader.nextLine();
				int h=0;
				int p1=0;
				int p2=0;
				while(h<m) {
					//gets all the number till ','
					while(p2<data.length() && data.charAt(p2)!=',' ) {
						p2++;
					}
					if(p1==p2) {
						break;
					}
					if(!data.substring(p1,p2).contentEquals("_")) {
						start[j][h]=Integer.parseInt(data.substring(p1,p2));
					}
					h++;
					p1=p2;
					p2++;
					p1++;

				}

			}

		
			//gets "Goal state:" and then another matrix

			data = myReader.nextLine();
			if(data.equalsIgnoreCase("Goal state:")) {

				goal=new Integer [n][m];

				for (int j=0;j<n;j++) {
					data = myReader.nextLine();
					int h=0;
					int p1=0;
					int p2=0;
					while(h<m) {
						//gets all the number till ','
						while(p2<data.length() && data.charAt(p2)!=',' ) {
							p2++;
						}
						if(p1==p2) {
							break;
						}
						if(!data.substring(p1,p2).contentEquals("_")) {
							goal[j][h]=Integer.parseInt(data.substring(p1,p2));
						}
						h++;
						p1=p2;
						p2++;
						p1++;

					}

				}
				

			} 
			
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}


		//after we got the matrix we will create an puzzle
		Node p=new Node(start);
		puzzle p1=new puzzle(p,goal); 

		//withopen
		if(open==true) {
			p1.setWithOpen(true);
		}
		
		//calling the right function: BFS,DFIDF,A*,IDA*, DFBnB
		String ans="";

		if(time==true ) {
			long startTime = System.currentTimeMillis();

			switch(algorithm) {
				case "BFS":
					ans=p1.BFS();
					break;
				case "DFID":
					ans=p1.DFID();//infinity
					break;
				case "A*":
					ans=p1.A_Star();
					break;
				case "IDA*":
					ans=p1.IDA_Star();
					break;
				case "DFBnB":
					ans=p1.DFBnB();
					break;
			}

		
		long end = System.currentTimeMillis();
		float sec = (end - startTime) / 1000F; 
		ans+="\n"+sec + " seconds";
	}else {
		
		switch(algorithm) {
		case "BFS":
			ans=p1.BFS();
			break;
		case "DFID":
			ans=p1.DFID();
			break;
		case "A*":
			ans=p1.A_Star();
			break;
		case "IDA*":
			ans=p1.IDA_Star();
			break;
		case "DFBnB":
			ans=p1.DFBnB();
			break;
		}
		
	
	}
		//write ans to output.txt
		//just for now 
//		System.out.println(ans);
		try {
			FileWriter myWriter = new FileWriter("output.txt");
			myWriter.write(ans);
			myWriter.close();
//			System.out.println("Successfully wrote to the file.");
		} catch (IOException e) {
//			System.out.println("An error occurred.");
			e.printStackTrace();
		}


	}







}









