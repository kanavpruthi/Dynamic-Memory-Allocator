import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class Driver_alt {
    public static void main(String args[]) throws IOException{
        File myObj = new File("./A2_test2.in");
        Scanner sc = new Scanner(myObj);
        int numTestCases;
        numTestCases = sc.nextInt();
        while (numTestCases-- > 0) {
            int size;
            size = sc.nextInt();
            A2DynamicMem obj = new A2DynamicMem(size,3);
            int numCommands = sc.nextInt();
            while (numCommands-- > 0) {
                String command;
                command = sc.next();
                int argument;
                argument = sc.nextInt();
                int result = -5;
                switch (command) {
                    case "Allocate":
                        result = obj.Allocate(argument);
                        System.out.println(result);
                        break;
                    case "Free":
                        result = obj.Free(argument);
                        System.out.println(result);
                        break;
                    case "Defragment":
                        result =0;
                        obj.Defragment();
                        break;
                    default:
                        break;
                }
                
            }

        }
    }
}