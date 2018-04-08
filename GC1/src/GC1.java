import java.util.*;
class schd
{
	public String PID;
	public float AT,BT,WT,FT,TT;
	public int priot;
}

public class GC1 
{
	static schd[] table;
	static int no,arrlen;	//arrlen is used in Priority Scheduling
	
	public static void getData()
	{
		Scanner sc= new Scanner(System.in);
		System.out.println("\n Enter number of processes: ");
		no= sc.nextInt();
		table=new schd[no];
		for(int i=0;i<no;i++)			//Accepting Process Data
		{
			table[i]=new schd();
			System.out.println("-------------------------------------------------");
			System.out.println("Enter ProcessID of Process "+(i+1));
			table[i].PID=sc.next();
			System.out.println("Enter Arrival Time of Process "+(i+1));
			table[i].AT=sc.nextFloat();
			System.out.println("Enter Burst Time of Process "+(i+1));
			table[i].BT=sc.nextFloat();
			System.out.println("-------------------------------------------------");
		}
	}
	
	public static void sortData()			// sorting on AT
	{
		int n = table.length;			
	    for (int i1 = 0; i1 < n-1; i1++)
	    	for (int j1 = 0; j1 < n-i1-1; j1++)
	    		if (table[j1].AT > table[j1+1].AT)
	            {
	    			schd temp = table[j1];
	                table[j1] = table[j1+1];
	                table[j1+1] = temp;
	            }			
	}
	
	public static void printData(int flag)

	{
		int count=no,i=0;
		
		if(flag==0)					// flag=0 means print table W/O priority field
		{
			System.out.println("PID\tAT\tBT\tWT\tFT\tTT");
			while(count>0)
			{
				System.out.println(table[i].PID+"\t"+table[i].AT+"\t"+table[i].BT+"\t"+table[i].WT+"\t"+table[i].FT+"\t"+table[i].TT);
				i++;
				count--;
			}
			
		}
		else if(flag==1)			// flag=1 means print table WITH priority field
		{
			System.out.println("PID\tAT\tBT\tPRIOT\tWT\tFT\tTT");
			while(count>0)
			{
				System.out.println(table[i].PID+"\t"+table[i].AT+"\t"+table[i].BT+"\t"+table[i].priot+"\t"+table[i].WT+"\t"+table[i].FT+"\t"+table[i].TT);
				i++;
				count--;
			}
		}
		
	}

	public static void FCFS()
	{
		
		int i=0,count;
		float start_time,avg,sum=0,sum_tat=0,tat;	
		
		sortData();
		
	    count=no;
	    i=0;
	    start_time=table[i].AT;
	    while(count>0)
	    {
	    	table[i].WT=start_time-table[i].AT;		//WT=ST-AT
	    	table[i].FT=start_time+table[i].BT;		//FT=ST+BT
	    	table[i].TT=table[i].FT-table[i].AT;	//TT=FT-AT
	    	start_time=start_time+table[i].BT;		//Updating Start
	    	sum=sum+table[i].WT;					//calculating total Waiting Time
	    	sum_tat=sum_tat+table[i].TT;					//calculating total Waiting Time
	    	i++;
	    	count--;	    	
	    }
	    avg=sum/no;
	    tat=sum_tat/no;
	    System.out.println("----------------------------------------");
	    System.out.println("First Come First Serve (FCFS)");
	    System.out.println("----------------------------------------");
		printData(0);
		System.out.println("Average Waiting Time= "+avg);
		System.out.println("Average Turn Around Time= "+tat);
		System.out.println("----------------------------------------");
	
	}
		
	public static void SJF()
	{
		sortData();
		
        float  total_wt = 0, total_tat = 0;
        
        //----------finding  Waiting time-----------------
        
        float rt[] = new float[no];				//rt = remaining time        
        
        for (int i = 0; i < no; i++)			// Copy the burst time into rt[]
            rt[i] = table[i].BT;
      
        int complete = 0, t = 0;
        float minm = Integer.MAX_VALUE;
        int shortest = 0;
        boolean check = false;
		
        while (complete != no)					 // Process until all processes gets completed 
        {      
            for (int j = 0; j < no; j++)             // Find process with minimum remaining time among the processes that arrives till the current time
            {
                if ((table[j].AT <= t) && (rt[j] < minm) && rt[j] > 0) 
                {
                    minm = rt[j];
                    shortest = j;
                    check = true;
                }
            }
      
            if (check == false) 
            {
                t++;
                continue;
            }
      
            rt[shortest]--;					// Reduce remaining time by one      
            System.out.print(table[shortest].PID+" | ");
            minm = rt[shortest];			// Update minimum
            if (minm == 0)
                minm = Integer.MAX_VALUE;
      
            if (rt[shortest] == 0)			 // If a process gets completely executed 
            {               
                complete++;					// Increment complete 
                table[shortest].FT = t + 1;		// Find finish time of current process
                table[shortest].WT = table[shortest].FT - table[shortest].BT - table[shortest].AT; // Calculate waiting time
      
                if (table[shortest].WT < 0)
                    table[shortest].WT = 0;
            }           
            t++;							 // Increment time
        }
      //---------------Waiting time calculation ends-------------------
        
       for (int i = 0; i < no; i++)			 // calculating turnaround time by adding bt[i] + wt[i]
       {
    	   table[i].TT = table[i].BT + table[i].WT;
    	   total_tat=total_tat+table[i].TT;
    	   total_wt=total_wt+table[i].WT;
       }
       
       System.out.println("\n----------------------------------------");
	   System.out.println("Shortest Job First-Preemtive(SJF-P)");
	   System.out.println("----------------------------------------");
       printData(0);
       System.out.println("Average waiting time = " + total_wt / no);
       System.out.println("Average turn around time = "+total_tat /no);
       System.out.println("----------------------------------------");
	}

	public static int select_start()		// function of PRIOT
	{
		int i=0,min,index=0;
		schd temp;
		temp=table[i];
		min=temp.priot;
		
		while(temp.AT==table[i].AT)
		{
			if(table[i].priot<min)
			{
				min=table[i].priot;
				index=i;
			}
			i++;			
		}
		return index;
	}
	
	public static schd[] getMinimum(int[] finished, float current_time) // function of PRIOT
	{
		schd [] minArray= new schd[no];
		int j=0;
		for(int i=0;i<no;i++)
		{
			if((table[i].AT<=current_time) && finished[i]==0)
				{
					minArray[j]=table[i];
					j++;
				}			
		}
		arrlen=j;				//arrlen will store the size of minArray, arrlen is to be used in selectProcess() 
		return minArray;
	}
	
	public static schd selectProcess(schd [] minArray)	// function of PRIOT
	{		
		schd min=minArray[0];
		for(int i=0;i<arrlen;i++)			
			if((minArray[i].priot<min.priot))
				min=minArray[i];
		return min;
	}
	
	public static int getIndex(schd temp)	// function of PRIOT and RR
	{
		for(int i=0;i<no;i++)
			if(table[i].PID.equals(temp.PID))
				return i;
		return 0;
	}
		
	public static void PRIOT()
	{
		Scanner sc= new Scanner(System.in);
		int i=0,index,finished[]=new int[no];
		float sum_wt=0,sum_tat=0,avg_wt,avg_tat,current_time;
		schd minArray[],temp;
		for(i=0;i<no;i++)									//Accepting Priorities
		{
			System.out.println("> Enter Priority of process "+table[i].PID+" : ");
			table[i].priot=sc.nextInt();
		}
		sortData();
		
		index=select_start();								//returns the starting process
		current_time=table[index].AT;		
		
		table[index].FT=current_time+table[index].BT;
		table[index].WT=current_time-table[index].AT;
		table[index].TT=table[index].FT-table[index].AT;
		sum_wt=sum_wt+table[index].WT;						//calculating total Waiting Time
    	sum_tat=sum_tat+table[index].TT;					//calculating total Waiting Time
		finished[index]=1;		
		current_time+=table[index].BT;	
		
		for(i=0;i<no-1;i++)
		{
			minArray=new schd[no];
			minArray=getMinimum(finished,current_time);		// returns array of allocatable processes
			temp=selectProcess(minArray);					// returns the process with highest priority
			index=getIndex(temp);							// gives the index of temp
			table[index].FT=current_time+table[index].BT;
			table[index].WT=current_time-table[index].AT;
			table[index].TT=table[index].FT-table[index].AT;
			sum_wt=sum_wt+table[index].WT;					//calculating total Waiting Time
	    	sum_tat=sum_tat+table[index].TT;				//calculating total Waiting Time
			finished[index]=1;		
			current_time+=table[index].BT;	
		}
		avg_wt=sum_wt/no;
		avg_tat=sum_tat/no;
	    System.out.println("----------------------------------------");
	    System.out.println("Priority Non Preemtive(P NP)");
	    System.out.println("----------------------------------------");
		printData(1);
		System.out.println("Average Waiting Time= "+avg_wt);
		System.out.println("Average Turn Around Time= "+avg_tat);
		System.out.println("----------------------------------------");
	}
 	
	public static boolean isFinish(float[] rt) // function of RR
	{
		int flag=0;
		for(int i=0;i<no;i++)
			if(rt[i]==0)
				flag++;
		
		if(flag==no)
			return false;
		else
			return true;
	}
	
	public static void RR()
	{
		Scanner sc = new Scanner(System.in);
		float quantam ,rt[]=new float[no], t;				//rt = remaining time    ;
		float sum_wt=0,sum_tat=0,avg_wt,avg_tat;
		schd temp=null;
		int i=0,process_no;
		Queue<schd> wq = new LinkedList<>();				// wait queue
		Queue<schd> rq = new LinkedList<>();				// ready queue
		
		System.out.println("> Enter Time Quantum/Time Slice: ");
		quantam=sc.nextFloat();		    
             
        sortData();
        for(i=0;i<no;i++)
        	{
        		rt[i]=table[i].BT;
        		wq.add(table[i]);			//adding processes to wait queue
        	}
    
        t=wq.peek().AT;
        System.out.println("\nGantt Chart: \n");
        while(isFinish(rt))
        {        	
			while((wq.isEmpty()!=true)&&(wq.peek().AT<=t))			//peek()-To view the head of queue without removing it. Returns null if queue is empty.
			{			
				rq.add(wq.remove());
			}
			
			if((temp!=null)&&(rt[getIndex(temp)])!=0)
				rq.add(temp);
			
			temp=rq.remove();
			process_no=getIndex(temp);
			if(rt[process_no]>quantam)
			{
				t+=quantam;
				rt[process_no]-=quantam;
			}
			else
			{	
				t+=rt[process_no];
				table[process_no].FT=t;
				table[process_no].WT=t-table[process_no].BT-table[process_no].AT;
				table[process_no].TT=table[process_no].WT+table[process_no].BT;
				rt[process_no]-=quantam;
				sum_wt+=table[process_no].WT;
				sum_tat+=table[process_no].TT;
			}
			System.out.print(temp.PID+" | ");
        }
        avg_wt=sum_wt/no;
		avg_tat=sum_tat/no;
	    System.out.println("\n----------------------------------------");
	    System.out.println("Round Robin (RR)");
	    System.out.println("----------------------------------------");
		printData(0);
		System.out.println("Average Waiting Time= "+avg_wt);
		System.out.println("Average Turn Around Time= "+avg_tat);
		System.out.println("----------------------------------------");
		
	}
	
	public static void main(String[] args) 
	{
		
		int choice;
		boolean cont= true;
		Scanner sc = new Scanner(System.in);
		GC1 obj=new GC1();
		
		System.out.println("----------------------------------------");
		System.out.println("\t\tEnter Data");	
		System.out.println("----------------------------------------");
		obj.getData();
		
		while(cont)
		{
		System.out.println("\n Enter the choice of Scheduling Algorithm: ");
		System.out.println("\n1. First Come First Serve (FCFS)\n2. Shortest Job First Preemtive (SJF-P)\n3. Round Robin (RR)\n4. Priority Based\n5. Exit\n-->");
		choice=sc.nextInt();		
			
		switch (choice) 
		{
			case 1:	
				FCFS();
				break;
			case 2:
				SJF();
				break;
			case 3:
				RR();
				break;
			case 4:
				PRIOT();
				break;
			case 5:
			System.out.println("----------------------------------------");
			System.out.print("\t\tExiting...");
			System.out.println("\n----------------------------------------");
			cont=false;
			break;
			default:
				System.out.println("\n Invalid option! ");
				break;
		}	// end of switch
		}// end of while	
	}// end of main

}// end of class
