@echo off
echo ========================================
echo    🚀 НАСТРОЙКА ОКРУЖЕНИЯ ELITE COFFEE
echo ========================================
echo.

REM Проверяем наличие Java
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Java не найдена!
    echo 📥 Скачиваем и устанавливаем JDK 17...
    echo.
    echo 🔗 Откройте ссылку и установите JDK 17:
    echo https://download.oracle.com/java/17/latest/jdk-17_windows-x64_bin.exe
    echo.
    echo ⚠️  После установки ПЕРЕЗАПУСТИТЕ командную строку!
    pause
    exit /b 1
)

echo ✅ Java найдена:
java -version

REM Проверяем Android SDK
if not exist "%ANDROID_HOME%\platform-tools\adb.exe" (
    echo.
    echo ❌ Android SDK не найден!
    echo 📥 Скачиваем Command Line Tools...
    
    mkdir android-sdk 2>nul
    cd android-sdk
    
    echo 🔗 Скачайте Android Command Line Tools:
    echo https://developer.android.com/studio#command-tools
    echo.
    echo 📁 Распакуйте в папку: %cd%\cmdline-tools\latest\
    echo.
    echo 📋 Затем выполните команды:
    echo set ANDROID_HOME=%cd%
    echo set PATH=%%PATH%%;%%ANDROID_HOME%%\cmdline-tools\latest\bin;%%ANDROID_HOME%%\platform-tools
    echo.
    echo ⚠️  ДОБАВЬТЕ эти переменные в системные настройки Windows!
    echo.
    pause
    
    cd ..
) else (
    echo ✅ Android SDK найден: %ANDROID_HOME%
)

echo.
echo 🎯 Проверяем Gradle Wrapper...
if exist gradlew.bat (
    echo ✅ Gradle Wrapper найден
) else (
    echo ❌ Gradle Wrapper не найден!
)

echo.
echo ========================================
echo    ✨ НАСТРОЙКА ЗАВЕРШЕНА!
echo ========================================
pause




