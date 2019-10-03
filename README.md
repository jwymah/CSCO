## Usage
#### Method 1: command line

navigate to project directory and run using sbt
```
sbt "run [absolute path to json file] [Optional: true/false to show parsing errors]"
```

eg.
```
sbt "run /tmp/log.json true"
```
##### Running Tests with sbt
```sbt test```
#### Method 2: Load project into intellij as sbt project and run Main class with arguments or test classes

### Assumptions
* Invalid JSON / Invalid UUIDs / Unparsable fields into expected types - are caught but do not stop processing of other lines
* multiple extension files are not check for - in this case only the final extension is kept
		- can be added without too much effort
* file contents are expected as 1 complete JSON per line
* disposition values are not validated beyond data type.Unclear what to do if it were outside of defined values and the existing 3 values should be sufficient for.

### Misc 
Written/run on mac OS Mojave 10.14.6 using Intellij
Scala version: 2.13.1
JDK version: 12.0.2
