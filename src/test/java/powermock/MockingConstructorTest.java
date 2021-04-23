package powermock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SystemUnderTest.class)
public class MockingConstructorTest {

    @Mock
    ArrayList mockList;

    @InjectMocks
    SystemUnderTest systemUnderTest;

    @Test
    public void testBadNames() throws Exception {
        PowerMockito.whenNew(ArrayList.class).withAnyArguments().thenReturn(mockList);

        when(mockList.size()).thenReturn(10);

        int size = systemUnderTest.methodUsingAnArrayListConstructor();

        assertEquals(10, size);

    }

    private List<Integer> getStats() {
       return Arrays.asList(1, 2, 3);
    }
}
