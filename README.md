A maven plugin for compressing css and javascript files based on YUICompressor

## Build it

```
mvn clean install
```

##How to user it : 

+ build it 


```
<plugin>
				<groupId>org.omidbiz.fanpardaz.maven.plugin</groupId>
				<artifactId>compressor</artifactId>
				<version>0.2</version>
				<configuration>
					<excludes>
						<exclude>seam-min.css</exclude>
						<exclude>seam-min.js</exclude>						
					</excludes>
					<warCssOutputDirectory>css</warCssOutputDirectory>
				</configuration>
				<executions>
					<execution>
						<id>cssCompression</id>
						<phase>prepare-package</phase>
						<goals>
							<goal>cssCompressor</goal>
						</goals>
					</execution>
					<execution>
						<id>jsCompression</id>
						<phase>prepare-package</phase>
						<goals>
							<goal>jsCompressor</goal>
						</goals>
					</execution>
				</executions>
			</plugin>
```
