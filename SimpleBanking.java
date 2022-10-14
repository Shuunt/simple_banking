import java.io.*;
import java.util.*;
import java.text.*;
import java.nio.charset.StandardCharsets;

public class SimpleBanking {
    
    public static void main(String[] args){
        File file = new File("bank.txt");
        FileWriter writer;
        System.out.println("List of commands :\n 1) Check account\n 2) Make a deposit\n 3) Make a withdrawal");
        Scanner in = new Scanner(System.in);
        String prompt = in.nextLine();
        String moneyPrompt = null;
        long money = 0;
        switch(prompt) {
            case "1":
              promptHistoricAccount();
              System.out.println("Total on your account : " + getTotalAccount() + " Euros.\n");
              break;
            case "2":
              moneyPrompt = getPromptNumber("How many Euros would you like to deposit ?", in);
              money = convertNumber(moneyPrompt);
              break;
            case "3":
              moneyPrompt = getPromptNumber("How many Euros would you like to withdraw ?", in);
              money = convertNumber(moneyPrompt);
              break;
            default:
              System.out.println("I'm sorry, use a number between 1 and 3 to interact with me.\n");
          }
        
        if (money > 0){
            try {
                writer = new FileWriter(file, true);
                PrintWriter printer = new PrintWriter(writer);
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                if (prompt.equals("3") && (getTotalAccount() - money < 0 || money == 0)){
                  System.out.println("I'm sorry we can't allow you to go THAT broke.\n");
                  System.exit(-1);
                }
                String output = '\n' + dateFormat.format(date) + (prompt.equals("2") ? " deposit of " : " withdrawal of ") + moneyPrompt + " Euros.\n"; 
                System.out.println(output);
                printer.append(output);
                if (prompt.equals("2")){
                  setNewTotalAccount(getTotalAccount() + money, printer);
                } else {
                  setNewTotalAccount(getTotalAccount() - money, printer);
                }
                printer.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static long getTotalAccount(){
      try {
        BufferedReader br = new BufferedReader(new FileReader("bank.txt"));
        String sCurrentLine = "";
        String lastLine = "";
        while ((sCurrentLine = br.readLine()) != null) 
        {
            lastLine = sCurrentLine;
        }
        br.close();
        return Long.valueOf(lastLine);
      } catch (Exception e) {
        return 0;
      }
    }

    private static void setNewTotalAccount(long money, PrintWriter printer){
      try {
        BufferedReader br = new BufferedReader(new FileReader("bank.txt"));
        String sCurrentLine = "";
        String lastLine = "";
        while ((sCurrentLine = br.readLine()) != null) 
        {
            lastLine = sCurrentLine;
        }
        br.close();
        printer.append(String.valueOf(money));
        System.out.println("Your new total Account is : " + money + " Euros.");
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    private static void promptHistoricAccount(){
      try {
        BufferedReader br = new BufferedReader(new FileReader("bank.txt"));
        String sCurrentLine = "";
        String lastLine = "";
        while ((sCurrentLine = br.readLine()) != null) 
        {
          if (sCurrentLine.contains("of")){
            System.out.println(sCurrentLine);
          }
        }
        br.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    private static String getPromptNumber(String output, Scanner in){
        System.out.println(output);
        return in.nextLine();
    }

    private static long convertNumber(String moneyPrompt){
        long value = 0;
        try {
          value = Long.valueOf(moneyPrompt);
        } catch (NumberFormatException e){
          System.out.println("I'm sorry, use a number to interact with me.\n");
        }
        return value;
    }
}