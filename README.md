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
* Any errors in the file result in that line being ignored for processing. Errors may arise from:
  * Invalid JSON / Invalid UUIDs / Unparsable fields into expected types
  * Sha values that contain non-hex characters
  * Disposition values outside expected range of [1,3]
* Logs with disposition values in range of [1,3] are all included for processing
* multiple extension files are not check for - in this case only the final extension is kept
		- can be added without too much effort, but expected behaviour should be defined
* file contents are expected as 1 complete JSON per line

### Misc 
Written/run on mac OS Mojave 10.14.6 using Intellij
Scala version: 2.13.1
JDK version: 12.0.2
