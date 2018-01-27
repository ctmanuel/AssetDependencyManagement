# AssetDependencyManagement

Built using Java 9 with JUnit 5 for unit testing.
Built to .jar

run commands:
From command prompt: java -jar AssetDependencyManagement.jar

executable commands:
[AddDep]: will enable you to add a dependency. After the dependency is successfully
added it will display the current dependencies that are being managed. This will check
for circular dependencies between assets and to see if the dependency already exists.
If the dependency cannot be added then it will display "Cannot add dependency: [from, to]".

[CanDelete]: will check to see if the Asset can be deleted. It checks to see that the Asset
does not have any other assets depending on it. Will display the result.

[exit]: exit the program

![alt text](https://github.com/ctmanuel/AssetDependencyManagement/blob/master/usage.PNG)
