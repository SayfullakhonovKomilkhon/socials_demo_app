@echo off
echo ========================================
echo    ☕ СБОРКА ELITE COFFEE APK
echo ========================================
echo.

REM Проверяем Java
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Java не найдена! Запустите setup-environment.bat
    pause
    exit /b 1
)

echo ✅ Java готова к работе
echo.

REM Очищаем предыдущие сборки
echo 🧹 Очищаем предыдущие сборки...
if exist app\build rmdir /s /q app\build
echo ✅ Очистка завершена
echo.

REM Собираем Debug APK
echo 🔨 Начинаем сборку Elite Coffee...
echo ⏳ Это может занять несколько минут...
echo.

call gradlew.bat assembleDebug --console=plain

if %errorlevel% equ 0 (
    echo.
    echo ========================================
    echo    🎉 СБОРКА УСПЕШНА!
    echo ========================================
    echo.
    echo 📱 APK файл готов:
    echo 📁 app\build\outputs\apk\debug\app-debug.apk
    echo.
    echo 📏 Размер файла:
    for %%A in (app\build\outputs\apk\debug\app-debug.apk) do echo    %%~zA байт
    echo.
    echo 🚀 Готово к установке на телефон!
    echo.
    
    REM Проверяем подключение устройства
    echo 📱 Проверяем подключенные устройства...
    adb devices
    
    echo.
    echo 💡 Для установки на телефон выполните:
    echo    install-on-phone.bat
    echo.
) else (
    echo.
    echo ❌ ОШИБКА СБОРКИ!
    echo 🔍 Проверьте сообщения выше для диагностики
    echo.
)

pause




