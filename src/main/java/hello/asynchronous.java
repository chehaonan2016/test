package hello;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class asynchronous {
public static void main(String args[])
{
    ExecutorService executor= Executors.newFixedThreadPool(2);
    CompletableFuture<String> future2=CompletableFuture.supplyAsync(()->{System.out.println("red_come");
    try{
        System.out.println("red");
        Thread.sleep(50000);
        return "red_back";
    }
    catch(InterruptedException e){
        System.err.println("red_wrong");
        return "red_bye";
        }
    },executor);

    CompletableFuture<String> future1=CompletableFuture.supplyAsync(()->{System.out.println("ming_come");
        try{
            System.out.println("ming");
            Thread.sleep(30000);
            return "ming_back";
        }
        catch(InterruptedException e){
            System.err.println("ming_wrong");
            return "ming_bye";
        }
    },executor);

}
}
