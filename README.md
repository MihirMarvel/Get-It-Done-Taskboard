# 🧩 GetItDone – A Taskboard App

**GetItDone** is a modular Android TaskBoard app built using **Kotlin**, **Jetpack Compose**, **Room**, and **Hilt**.  
It allows users to **create**, **edit**, **delete**, **mark tasks as done**, and **sync tasks** with a dummy network source — showcasing a clean, scalable Android architecture.

---

## 🚀 Features

- 📝 Add, edit, and delete tasks  
- ✅ Mark tasks as complete or incomplete  
- 🔄 Dummy network sync with simulated delay  
- 💾 Offline-first design using Room Database 
- 💉 Dependency Injection using Hilt  
- ⚙️ Reactive updates via Coroutines + Flow  

---

## 🧱 Architecture Overview

This project follows **MVVM + Clean Architecture** principles, divided into modular layers:

```
app/                  → Application entry point & Hilt setup
feature/taskboard/    → UI layer (Jetpack Compose), ViewModels, Repository
core/data/            → Local data layer (Room entities, DAO, database)
core/network/         → Dummy network service simulating API calls
core/common/          → Shared models, Result wrappers, constants
```

### 🧩 Key Architectural Concepts

| Layer | Responsibility |
|-------|----------------|
| **UI (Compose)** | Displays task list, form, and feedback. Observes state from ViewModels via `StateFlow`. |
| **ViewModel** | Handles user interactions, calls repository functions, exposes sealed `UiState`. |
| **Repository** | Mediates between local (Room) and remote (DummyNetwork) sources. |
| **Room Database** | Local cache and single source of truth. |
| **Dummy Network Layer** | Simulated API call with delay and seeded data for testing sync functionality. |
| **Hilt (DI)** | Provides dependencies like DAO, Repository, and Network Service. |

---

## 🧰 Tech Stack

- **Language:** Kotlin  
- **UI:** Jetpack Compose (Material 3, Navigation)  
- **Local Storage:** Room Database  
- **Dependency Injection:** Hilt  
- **Async & Streams:** Coroutines + StateFlow  
- **Navigation Animations:** Navigation Animation  
- **Architecture:** MVVM + Clean Architecture  

---

## 🧑‍💻 Setup Instructions

### Prerequisites
- Android Studio **Koala (2024.1)** or newer  
- JDK 17  
- Minimum SDK: 24  

### Steps to Run Locally

1. **Clone the repository:**
   ```bash
   git clone https://github.com/MihirMarvel/Get-It-Done-Taskboard.git
   cd GetItDone
   ```

2. **Open the project:**
   - Launch **Android Studio**
   - Choose **File → Open** and select the `GetItDone` project folder
   - Wait for Gradle sync to finish

3. **Run the app:**
   - Select an emulator or physical device  
   - Click ▶️ **Run 'app'**

4. **Usage:**
   - Tap **➕ (FAB)** to add a task  
   - Tap a task to edit it  
   - Mark tasks as complete using the checkbox  
   - Tap **Sync (🔄)** on the top bar to fetch dummy tasks and merge into the list
   - Every task is organised in newest first order. The newest one is at the top and the oldest one at bottom and this order follows by every task.

---

## 🧩 Module Responsibilities

| Module | Description |
|---------|-------------|
| **core/common** | Shared utilities, Result classes, constants |
| **core/data** | Entities, DAOs, and Room database setup |
| **core/network** | Simulated network calls (dummy data + delay) |
| **feature/taskboard** | Compose UI screens, ViewModels, repository logic |
| **app** | Application entry, navigation host, Hilt setup |

---

## 🧪 Future Enhancements

- 🧭 Add swipe-to-delete animation  
- 🕒 Add task due date & sorting  
- ☁️ Replace dummy API with a real REST API  
- 💬 Add undo option via Snackbar  
- 🔔 Add push notifications for upcoming tasks  

---

## 🏗️ Project Highlights

- Offline-first architecture  
- Fully reactive UI (Room → Flow → ViewModel → Compose)  
- Proper Hilt injection of Repositories, DAOs, and ViewModels  
- Separation of concerns & testable design  

---

## 🪄 Example Screens

| Task List | Add/Edit Task |
|------------|---------------|
| ✅ Scrollable list with checkboxes and delete icons | ✏️ Text fields with validation and save button |

---

## ⚙️ Build & Run (Terminal)

```bash
./gradlew clean assembleDebug
adb install app/build/outputs/apk/debug/app-debug.apk
```

---

## 📄 License

```
MIT License © 2025 MIHIR VERMA
```

---

**Developed with Kotlin and Jetpack Compose**
