# BMI Calculator – Customization Guide

Thank you for purchasing BMI Calculator.

This document explains how to customize the app easily.

---

## 1. Change App Name
Edit:
res/values/strings.xml

Change:
<string name="app_name">BMI Calculator</string>

---

## 2. Change App Colors
Edit:
res/values/colors.xml

You can modify:
- colorPrimary
- colorAccent
- bmi_underweight
- bmi_normal
- bmi_overweight
- bmi_obese

---

## 3. Dark Mode
The app supports system Dark Mode automatically.
No code changes required.

---

## 4. Add / Remove Features
You can enable or disable features inside:
ui/main/MainActivity.kt
ui/result/ResultActivity.kt

---

## 5. Localization
Add new languages by creating:
res/values-xx/strings.xml

Example:
values-es → Spanish
values-ar → Arabic

---

## 6. Build APK / AAB
Use Android Studio:
Build → Generate Signed Bundle / APK
