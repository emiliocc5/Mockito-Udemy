package mockito;

import org.junit.Test;

import java.util.ArrayList;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class SpyTest {

    @Test
    public void test() {
        ArrayList arrayListSpy = spy(ArrayList.class);
        arrayListSpy.add("Dummy");
        verify(arrayListSpy).add("Dummy");
    }
}
