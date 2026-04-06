mkdir -Force lib
Invoke-WebRequest -Uri "https://repo1.maven.org/maven2/com/formdev/flatlaf/3.4/flatlaf-3.4.jar" -OutFile "lib/flatlaf-3.4.jar"
Invoke-WebRequest -Uri "https://repo1.maven.org/maven2/org/jfree/jfreechart/1.5.3/jfreechart-1.5.3.jar" -OutFile "lib/jfreechart-1.5.3.jar"
Invoke-WebRequest -Uri "https://repo1.maven.org/maven2/com/itextpdf/itextpdf/5.5.13.3/itextpdf-5.5.13.3.jar" -OutFile "lib/itextpdf-5.5.13.3.jar"
Invoke-WebRequest -Uri "https://repo1.maven.org/maven2/com/oracle/database/jdbc/ojdbc8/21.9.0.0/ojdbc8-21.9.0.0.jar" -OutFile "lib/ojdbc8-21.9.0.0.jar"
Write-Host "✅ JARs downloaded successfully!"
