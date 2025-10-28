---

# 🛡️ Cyber Threat Log Analyzer

[![License: MIT](https://img.shields.io/badge/License-MIT-red.svg)](LICENSE)
[![Java](https://img.shields.io/badge/Language-Java-blue.svg)]()
[![Platform](https://img.shields.io/badge/Platform-macOS%20%7C%20Linux-lightgrey.svg)]()

> A command-line cybersecurity tool that detects suspicious login attempts, repeated authentication failures, and potential brute-force attacks using Java and Data Structures.

---

## 📖 About the Project

**Cyber Threat Log Analyzer** is a lightweight **CLI-based cybersecurity project** that automatically analyzes system logs to detect and alert on abnormal login patterns.
It is designed as a **Data Structures & Algorithms (DSA)** project integrating **HashMaps, Queues, and Searching** concepts for efficient log processing and anomaly detection.

## [Its just a prototype right now .]

---

## 🎯 Features

✅ Real-time monitoring of system logs
✅ Detects brute-force and failed authentication attempts
✅ CLI Interface for easy control
✅ View log history by hour, day, or week
✅ Lightweight and open-source
✅ Fully implemented in Java

---

## 🧠 Concepts Used

* **HashMap** → To store and count login attempts per user/IP
* **Queue** → To manage recent log entries in memory
* **String Parsing & Regex** → For pattern matching in log data
* **File Handling** → Reading and filtering `/var/log/system.log`
* **Command-line Interface (CLI)** → User-friendly interaction

---

## ⚙️ Tech Stack

| Component           | Technology                         |
| ------------------- | ---------------------------------- |
| **Language**        | Java                               |
| **IDE**             | Visual Studio Code                 |
| **Platform**        | macOS (tested), works on Linux too |
| **Domain**          | Cybersecurity / Data Mining        |
| **Version Control** | Git & GitHub                       |

---

## 🧩 Project Structure

```
CyberThreatAnalyzer/
 ├── src/
 │   └── com/
 │       └── cber/
 │           └── analyzer/
 │                ├── Main.java
 │                ├── LogAnalyzer.java
 │                ├── LogEntry.java
 │                ├── Parser.java
 │                └── AlertSystem.java
 ├── out/
 │   └── (compiled .class files)
 ├── sample_logs/
 │   └── system.log
 ├── Cyber_Threat_Log_Analyzer_Presentation.pptx
 ├── LICENSE
 └── README.md
```

---

## 🚀 How to Run

### 1️⃣ Compile

```bash
javac -d out src/com/cber/analyzer/*.java
```

### 2️⃣ Run the Analyzer

```bash
java -cp out com.cber.analyzer.Main
```

### 3️⃣ CLI Options

```
1. Start Analyzer
2. Show Alert History
3. Show Logs (Time Filtered)
4. Exit
```

---

## 📸 Sample Output

```
Oct 27 09:35:53 Dishants-MacBook-Air sshd[110]: Failed password for root from 192.168.1.5
⚠️ Alert: Suspicious login attempt detected!
```

---

## 🔮 Future Enhancements

* 📧 Email/SMS alert integration
* 📊 GUI or web dashboard for visualization
* 🔗 Integration with **Splunk** or **ELK Stack**
* 🧠 Machine learning for advanced anomaly detection
* 🪟 Cross-platform support (Windows event logs)

---

## 👨‍💻 Developers

| Name                      | Role                                   |
| ------------------------- | -------------------------------------- |
| **Dishant Singh Rathore** | Lead Developer / Research              |
| **Priyanshu Joshi**       | Co-Developer / Testing & Documentation |

---

## 🪪 License

This project is licensed under the **[MIT License](LICENSE)** — you are free to use, modify, and distribute this project with proper credit.

```
MIT License © 2025 Dishant Singh Rathore & Priyanshu Joshi
```

---
