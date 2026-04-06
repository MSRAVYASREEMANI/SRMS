$baseFolder = "c:\Users\SRAAVYA\Desktop\Result System\src\main\java"
$javaFiles = Get-ChildItem -Path $baseFolder -Recurse -Filter *.java

foreach ($file in $javaFiles) {
    if ($file.Directory.Name -eq "java") { continue }
    
    $folderName = $file.Directory.Name
    $content = Get-Content $file.FullName
    $fileChanged = $false
    
    if ($content[0] -notmatch "^package ") {
        $packageStmt = "package $folderName;"
        $imports = @(
            "import dao.*;",
            "import model.*;",
            "import gui.*;",
            "import util.*;",
            "import java.sql.*;",
            "import javax.swing.*;",
            "import javax.swing.table.*;",
            "import java.awt.*;",
            "import java.awt.event.*;"
        )
        
        $newContent = @($packageStmt, "") + $imports + @("") + $content
        Set-Content -Path $file.FullName -Value $newContent
        $fileChanged = $true
        Write-Host "Processed $($file.Name)"
    }
}
