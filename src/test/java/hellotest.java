import hello.helloworld;
import org.junit.*;

public class hellotest {
@Test
    public void testoutput()
{
    helloworld he=new helloworld();
    String result=he.output();
    System.out.println(result);
}

}
