package business;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import services.TodoService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TodoBusinessImplMockitoInjectMocksTest {

    @Mock
    TodoService todoServiceMock;

    @InjectMocks
    TodoBusinessImpl todoBusiness;

    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;

    @Test
    public void testRetrieveTodoRelatedToSpring_usingAMock() {
        when(todoServiceMock.retrieveTodos("Dummy")).thenReturn(getTodos());

        List<String> filteredTodos = todoBusiness.retrieveTodosRelatedToSpring("Dummy");
        assertEquals(2, filteredTodos.size());
    }

    @Test
    public void testRetrieveTodoRelatedToSpring_withEmptyList() {
        when(todoServiceMock.retrieveTodos("Dummy")).thenReturn(new ArrayList<>());

        List<String> filteredTodos = todoBusiness.retrieveTodosRelatedToSpring("Dummy");
        assertEquals(0, filteredTodos.size());
    }

    @Test
    public void testRetrieveTodoRelatedToSpring_usingBDD() {
        //Given
        given(todoServiceMock.retrieveTodos("Dummy")).willReturn(getTodos());

        //When
        List<String> filteredTodos = todoBusiness.retrieveTodosRelatedToSpring("Dummy");

        //Then
        assertThat(filteredTodos.size(), is(2));
    }

    @Test
    public void testDeleteTodosNotRelatedToSpring_usingBDD() {
        //Given
        given(todoServiceMock.retrieveTodos("Dummy")).willReturn(getTodos());

        //When
        todoBusiness.deleteTodosNotRelatedToSpring("Dummy");

        //Then
        verify(todoServiceMock, times(1)).deleteTodo("Learn to dance");
        //then(todoServiceMock).should().deleteTodo("Learn to dance"); //its equivalent to the up line

        verify(todoServiceMock, never()).deleteTodo("Learn Spring MVC");
        //then(todoServiceMock).should(never()).deleteTodo("Learn to dance"); //its equivalent to the up line

        verify(todoServiceMock, never()).deleteTodo("Learn Spring");
    }

    @Test
    public void testDeleteTodosNotRelatedToSpring_usingBDD_argumentCapture() {
        //Given
        given(todoServiceMock.retrieveTodos("Dummy")).willReturn(getTodos());

        //When
        todoBusiness.deleteTodosNotRelatedToSpring("Dummy");

        //Then
        then(todoServiceMock).should().deleteTodo(stringArgumentCaptor.capture()); //Define Argument Captor on specific method call

        assertThat(stringArgumentCaptor.getValue(), is("Learn to dance")); //Capture the argument
    }

    @Test
    public void testDeleteTodosNotRelatedToSpring_usingBDD_argumentCaptureWithMultipleValues() {
        //Given
        List<String> todos = Arrays.asList("Learn Rock and Roll", "Learn Spring", "Learn to dance");

        given(todoServiceMock.retrieveTodos("Dummy")).willReturn(todos);

        //When
        todoBusiness.deleteTodosNotRelatedToSpring("Dummy");

        //Then
        then(todoServiceMock).should(times(2)).deleteTodo(stringArgumentCaptor.capture());

        assertThat(stringArgumentCaptor.getAllValues().size(), is(2));
    }

    private List<String> getTodos() {
       return Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn to dance");
    }
}
