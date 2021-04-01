package business;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import services.TodoService;
import services.TodoServiceStub;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

public class TodoBusinessImplMockTest {

    @Test
    public void testRetrieveTodoRelatedToSpring_usingAMock() {
        TodoService todoServiceMock = mock(TodoService.class);
        TodoBusinessImpl todoBusiness = new TodoBusinessImpl(todoServiceMock);

        List<String> values = Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn to dance");

        when(todoServiceMock.retrieveTodos("Dummy")).thenReturn(values);

        List<String> filteredTodos = todoBusiness.retrieveTodosRelatedToSpring("Dummy");
        assertEquals(2, filteredTodos.size());
    }

    @Test
    public void testRetrieveTodoRelatedToSpring_withEmptyList() {
        TodoService todoServiceMock = mock(TodoService.class);
        TodoBusinessImpl todoBusiness = new TodoBusinessImpl(todoServiceMock);

        when(todoServiceMock.retrieveTodos("Dummy")).thenReturn(new ArrayList<>());

        List<String> filteredTodos = todoBusiness.retrieveTodosRelatedToSpring("Dummy");
        assertEquals(0, filteredTodos.size());
    }

    @Test
    public void testRetrieveTodoRelatedToSpring_usingBDD() {
        //Given
        TodoService todoServiceMock = mock(TodoService.class);
        TodoBusinessImpl todoBusiness = new TodoBusinessImpl(todoServiceMock);

        List<String> values = Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn to dance");

        given(todoServiceMock.retrieveTodos("Dummy")).willReturn(values);

        //When
        List<String> filteredTodos = todoBusiness.retrieveTodosRelatedToSpring("Dummy");

        //Then
        assertThat(filteredTodos.size(), is(2));
    }

    @Test
    public void testDeleteTodosNotRelatedToSpring_usingBDD() {
        //Given
        TodoService todoServiceMock = mock(TodoService.class);
        TodoBusinessImpl todoBusiness = new TodoBusinessImpl(todoServiceMock);

        List<String> todos = Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn to dance");

        given(todoServiceMock.retrieveTodos("Dummy")).willReturn(todos);

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
        //Declare Argument Captor
        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);

        //Given
        TodoService todoServiceMock = mock(TodoService.class);
        TodoBusinessImpl todoBusiness = new TodoBusinessImpl(todoServiceMock);

        List<String> todos = Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn to dance");

        given(todoServiceMock.retrieveTodos("Dummy")).willReturn(todos);

        //When
        todoBusiness.deleteTodosNotRelatedToSpring("Dummy");

        //Then
        then(todoServiceMock).should().deleteTodo(stringArgumentCaptor.capture()); //Define Argument Captor on specific method call

        assertThat(stringArgumentCaptor.getValue(), is("Learn to dance")); //Capture the argument
    }

    @Test
    public void testDeleteTodosNotRelatedToSpring_usingBDD_argumentCaptureWithMultipleValues() {
        //Declare Argument Captor
        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);

        //Given
        TodoService todoServiceMock = mock(TodoService.class);
        TodoBusinessImpl todoBusiness = new TodoBusinessImpl(todoServiceMock);

        List<String> todos = Arrays.asList("Learn Rock and Roll", "Learn Spring", "Learn to dance");

        given(todoServiceMock.retrieveTodos("Dummy")).willReturn(todos);

        //When
        todoBusiness.deleteTodosNotRelatedToSpring("Dummy");

        //Then
        then(todoServiceMock).should(times(2)).deleteTodo(stringArgumentCaptor.capture());

        assertThat(stringArgumentCaptor.getAllValues().size(), is(2));
    }
}
