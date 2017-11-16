
import java.io.*;
import java.util.Calendar;
import java.text.SimpleDateFormat;

public class on_pinger 
{
    public static String now(String dateFormat) 
    {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(cal.getTime());
    }
    public static void cmdexec(int count, String ipaddr, PrintStream p)
    {
        try 
        {
            Process p1 = Runtime.getRuntime().exec ("date");
            Process p2 = Runtime.getRuntime().exec ("ping -c 1 " + ipaddr);
            try 
            {
                Thread.sleep(500L);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
            BufferedReader in1 = new BufferedReader (new InputStreamReader(p1.getInputStream()));
            BufferedReader in2 = new BufferedReader (new InputStreamReader(p2.getInputStream()));
            String s1 = in1.readLine();
            String s2 = in2.readLine();
            s2 = in2.readLine();
            String s3 = count + " " + s1 + " PING TIMED OUT: " + ipaddr;
            String s4 = count + " " + s1 + " " + s2;
            if (s2.length() < 3)           
            {
                System.out.println(s3);
                p.println(s3);
            }
            else 
                System.out.println(s4);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public static void main (String args[])
    {
        FileOutputStream fout;
        PrintStream p;
        int looper = -1;
        int counter = 1;
        
        if (args.length < 1)
        {
            System.err.println("\nApplication Error:");
            System.err.println("Usage: java on_pinger ipadd1 ipadd2 .... \n\n");
            System.exit(-1);
        }
        try
        {   
            String opn = "ON-PINGER-" + now("yyyyMMdd") + "-" +now("HHmmss-aaa") + ".OUT";
            fout = new FileOutputStream (opn);
            p = new PrintStream (fout);
            System.out.println("Output Filename: " + opn);
            p.println("--------------------------------------------------------------------------");
            p.println("This log file was created on " + now("MMMMM dd, yyyy GGG hh:mm aaa"));
            p.println("--------------------------------------------------------------------------");
            while (looper < 0)
            {
                for(int i = 0; i < args.length; i++)
                {
                    cmdexec(counter, args[i], p);
                }
                counter++;
            }
            p.close();
            fout.close();
        }
        catch (IOException e)
        {
            System.err.println ("Unable to write file" );
            System.exit(-1);
        }
      }
}


