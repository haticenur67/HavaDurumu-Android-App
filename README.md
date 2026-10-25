# 🌤️ Weather App | Hava Durumu Uygulaması

A modern Android weather application built with **Kotlin** and **Jetpack Compose** as part of the Bilişim Kampı graduation project.

The application provides weather information through the Open-Meteo API and features a custom temperature chart built from scratch using Jetpack Compose Canvas.

[English](#english) | [Türkçe](#türkçe)

---

## English

### About the Project

Weather App is an Android application designed to provide weather information through a clean, modern, and user-friendly interface. The project focuses on modern Android development practices, UI design, API integration, and separation of concerns.

### Features

- **Weather Data Integration:** Retrieves weather information from the Open-Meteo API.
- **Custom Temperature Chart:** A scrollable hourly temperature chart implemented using Jetpack Compose Canvas without relying on a ready-made charting library.
- **Modern UI:** A visually appealing interface built with Jetpack Compose.
- **City Search:** Search functionality for finding weather information by city.
- **UI State Management:** Handles application states such as loading, success, and error, where implemented.
- **Layered Code Structure:** Separates UI components, data handling, and application logic.

### Technologies

- **Language:** Kotlin
- **UI Framework:** Jetpack Compose
- **Architecture:** MVVM and Repository Pattern, if implemented
- **Asynchronous Programming:** Kotlin Coroutines and StateFlow, if implemented
- **Networking:** Retrofit and Gson Converter, if implemented
- **Weather Data:** Open-Meteo API

### Project Structure

The project is organized into packages based on their responsibilities.

- `model/` — Data classes representing API responses.
- `data/` — API service and repository components.
- `ui/` — Screens, reusable UI components, and the hourly temperature chart.
- `ui.theme/` — Application colors, typography, and theme configuration.

*Adjust these package names to match your actual project structure.*

### Getting Started

1. Clone or download this repository.
2. Open the project in Android Studio.
3. Allow Gradle to sync and download the required dependencies.
4. Run the application on an Android emulator or a compatible Android device.

### Learning Objectives

Through this project, I have been developing my skills in:

- Android application development with Kotlin.
- Building declarative user interfaces with Jetpack Compose.
- Integrating external APIs.
- Visualizing data with a custom Canvas implementation.
- Organizing code into maintainable components.

### Developer

**Hatice Nur Saraç**  
Computer Engineering Student  
Karabük University

---

## Türkçe

### Proje Hakkında

Hava Durumu Uygulaması, Bilişim Kampı bitirme projesi kapsamında geliştirilen bir Android uygulamasıdır. Kotlin ve Jetpack Compose kullanılarak oluşturulan uygulama, Open-Meteo API üzerinden hava durumu verilerini sunmayı amaçlar.

Proje kapsamında modern arayüz tasarımı, API entegrasyonu, kod organizasyonu ve Canvas kullanılarak özel bir sıcaklık grafiği oluşturulması üzerine çalışılmıştır.

### Özellikler

- **Hava Durumu Verileri:** Open-Meteo API üzerinden hava durumu bilgilerini alma.
- **Özel Sıcaklık Grafiği:** Jetpack Compose Canvas ile hazır grafik kütüphanesi kullanılmadan geliştirilen kaydırılabilir saatlik sıcaklık grafiği.
- **Modern Arayüz:** Jetpack Compose ile oluşturulmuş kullanıcı arayüzü.
- **Şehir Arama:** Şehre göre hava durumu bilgilerini arama.
- **Arayüz Durum Yönetimi:** Uygulamada gerçekleştirilen yükleniyor, başarılı sonuç ve hata durumlarının yönetimi.
- **Düzenli Kod Yapısı:** Arayüz, veri işlemleri ve uygulama mantığının sorumluluklarına göre ayrılması.

### Kullanılan Teknolojiler

- **Programlama Dili:** Kotlin
- **Arayüz:** Jetpack Compose
- **Mimari:** Uygulandıysa MVVM ve Repository Pattern
- **Asenkron İşlemler:** Uygulandıysa Kotlin Coroutines ve StateFlow
- **Ağ İstekleri:** Uygulandıysa Retrofit ve Gson Converter
- **Veri Kaynağı:** Open-Meteo API

### Geliştirici

**Hatice Nur Saraç**  
Karabük Üniversitesi — Bilgisayar Mühendisliği Öğrencisi
