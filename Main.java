import java.util.*;

public class Main
{
    static Scanner sc = new Scanner(System.in);
    public static void operate(A2DynamicMem obj)
    {
        String command;
        command = sc.next();
        int argument;
        argument = sc.nextInt();
        int result = -5;
        boolean toPrint = true;
        switch (command) {
            case "Allocate":
                result = obj.Allocate(argument);
                break;
            case "Free":
                result = obj.Free(argument);
                break;
            case "Defragment":
                obj.Defragment();
                toPrint = false;
                break;
            default:
                break;
        }
        
        if(toPrint)
            System.out.println(result);
    }

    public static void main(String[] args)
    {
        A2DynamicMem obj = new A2DynamicMem(10000, 3);
        for (int i = 0; i < 4; i++)
        {
            operate(obj);
        }
        // obj.allocBlk.sanity();
        obj.freeBlk.sanity();
        
    }
}