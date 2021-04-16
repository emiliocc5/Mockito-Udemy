# Mockito-Udemy

@Argument capture
- is Usefull when you want to check the values that are passed to a mock method


Spy

- A spy get all logic from the class
- You can stub specific methods of your choice 
- Is also called partial Mock
- A spy enables you to watch real action, as well as change behavior when needed
- The good practice recommend avoid Spys



# Why Mockito doesn't mock private methods?

Firstly, we are not dogmatic about mocking private methods. We just don't care about private methods because from the standpoint of testing, private methods don't exist. Here are a couple of reasons Mockito doesn't mock private methods:

1. It requires hacking of classloaders that is never bullet proof and it changes the API (you must use custom test runner, annotate the class, etc.).
2. It is very easy to work around - just change the visibility of method from private to package-protected (or protected).
3. It requires the team to spend time implementing & maintaining it. And it does not make sense given point (2) and a fact that it is already implemented in different tool (powermock).
4. Finally... Mocking private methods is a hint that there is something wrong with Object Oriented understanding. In OO you want objects (or roles) to collaborate, not methods. Forget about pascal & procedural code. Think in objects.